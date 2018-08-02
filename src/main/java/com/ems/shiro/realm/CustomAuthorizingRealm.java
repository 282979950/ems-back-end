package com.ems.shiro.realm;

import com.ems.common.Global;
import com.ems.entity.Employee;
import com.ems.entity.SysPermission;
import com.ems.entity.SysRole;
import com.ems.service.IEmployeeService;
import com.ems.service.SystemService;
import com.ems.shiro.Principal;
import com.ems.shiro.utils.ShiroUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.permission.WildcardPermission;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;
import java.util.Deque;
import java.util.List;
import java.util.Set;

/**
 * 自定义认证授权Realm
 *
 * @author hml
 * @version 2016-7-5
 */
@Service
@Slf4j
public class CustomAuthorizingRealm extends AuthorizingRealm {

    /**
     * 踢出之前登录的/之后登录的用户 默认踢出之前登录的用户
     */
    private static boolean kickoutAfter = false;

    /**
     * 同一个帐号最大会话数 默认1
     */
    private static int maxSession = 1;

    private SessionManager sessionManager;

    private Cache<Integer, Deque<Serializable>> cache;

    private Cache<Principal, SimpleAuthorizationInfo> infoCache;

    public void setCacheManager(CacheManager cacheManager) {
        this.cache = cacheManager.getCache("shiro-activeSessionCache");
        this.infoCache = cacheManager.getCache("authorizationInfo-cache");
    }

    @Autowired
    private SystemService systemService;

    @Autowired
    private IEmployeeService employeeService;

    /**
     * 认证,登录时调用
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        String userName = token.getUsername();
//        int activeSessionSize = systemService.getSessionDao().getActiveSessions(false).size();
//        if (log.isDebugEnabled()) {
//            log.debug("login submit, active session size: {}, username: {}", activeSessionSize, token.getUsername());
//        }
        // 校验用户名密码
        Employee emp = employeeService.getEmpByLoginName(userName);
        if (emp == null) {
            return null;
        }
        if (emp.getEmpLoginFlag().equals(Global.FALSE)) {
            throw new AuthenticationException("该帐号已禁止登录,请联系管理员");
        }
        Principal principal = new Principal(emp);
        infoCache.remove(principal);
        return new SimpleAuthenticationInfo(principal, emp.getEmpPassword(), ByteSource.Util.bytes(Global.DEFAULT_MD5_SALT), getName());
    }

    /**
     * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        log.info("==enter doGetAuthorizationInfo====");

        Principal principal = (Principal) getAvailablePrincipal(principals);

        // 获取当前已登录的用户
//        if (!Boolean.valueOf(Global.getConfig("user.multiLoginAllowed"))) {
//            Subject subject = ShiroUtils.getSubject();
//            Session session = subject.getSession();
//            Serializable sessionId = session.getId();
//            Principal p = (Principal) subject.getPrincipal();
//            if (principal.equals(p)) {
//                Integer userId = p.getId();
//                Deque<Serializable> deque = cache.get(userId);
//                if (deque == null) {
//                    deque = new LinkedList<>();
//                    cache.put(userId, deque);
//                }
//                Collection<Session> _sessions = systemService.getSessionDao().getActiveSessions(true, principal, session);
//                int count = 0;
//                for (Session s : _sessions) {
//                    String sid = (String) s.getId();
//                    if (!deque.contains(sid)) {
//                        count++;
//                    }
//                }
//                if (count == _sessions.size()) {
//                    deque.clear();
//                }
//                //如果队列里没有此sessionId，且用户没有被踢出；放入队列
//                if (!deque.contains(sessionId) && session.getAttribute("kickout") == null) {
//                    deque.push(sessionId);
//                }
//                //如果队列里的sessionId数超出最大会话数，开始踢人
//                while (deque.size() > maxSession) {
//                    Serializable kickoutSessionId;
//                    if (Boolean.valueOf(Global.getConfig("user.kickoutAfter"))) {
//                        kickoutSessionId = deque.removeFirst();
//                    } else {
//                        kickoutSessionId = deque.removeLast();
//                    }
//                    try {
//                        Collection<Session> sessions = systemService.getSessionDao().getActiveSessions(true, principal, null);
//                        for (Session s : sessions) {
//                            String sid = (String) s.getId();
//                            if (sid.equals(kickoutSessionId)) {
//                                s.setAttribute("kickout", true);
//                            }
//                        }
//                    } catch (Exception e) {
//                        log.error("==Session set attribute==" + e.getMessage());
//                    }
//                }
//            }
//        }
        Employee emp = employeeService.getEmpByLoginName(principal.getLoginName());
        if (emp == null) {
            return null;
        }
        Session session = ShiroUtils.getSession();
        session.setAttribute(Global.SESSION_USER_ID, emp.getEmpId());
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Set<SysRole> roles = emp.getRoles();
        for (SysRole role : roles) {
            info.addRole(role.getRoleName());
            Set<SysPermission> permissions = role.getPermissions();
            for (SysPermission permission : permissions) {
                info.addStringPermission(permission.getPermName());
            }
        }
        info.addRole("user");
        infoCache.put(principal, info);
        // TODO: 2018/8/1 更新登录IP和时间
        return info;
    }

    @Override
    protected void checkPermission(Permission permission, AuthorizationInfo info) {
        log.info("==enter SystemAuthorizingRealm login checkPermission=ssss=");
        authorizationValidate(permission);
        super.checkPermission(permission, info);
    }

    @Override
    protected boolean[] isPermitted(List<Permission> permissions, AuthorizationInfo info) {
        log.info("==enter SystemAuthorizingRealm login isPermitted=tttt=");
        if (permissions != null && !permissions.isEmpty()) {
            for (Permission permission : permissions) {
                authorizationValidate(permission);
            }
        }
        return super.isPermitted(permissions, info);
    }

    @Override
    public boolean isPermitted(PrincipalCollection principals, Permission permission) {
        log.info("==enter SystemAuthorizingRealm isPermitted 666=begin=");
        authorizationValidate(permission);
        Principal principal = (Principal) getAvailablePrincipal(principals);
        SimpleAuthorizationInfo info = infoCache.get(principal);
        if (info != null) {
            Set<String> permissions = info.getStringPermissions();
            if (permissions == null) {
                return false;
            }
            for (String p : permissions) {
                Permission pp = new WildcardPermission(p);
                if (permission.equals(pp)) {
                    return true;
                }
            }
            return false;
        } else {
            return super.isPermitted(principals, permission);
        }
    }


    @Override
    protected boolean isPermittedAll(Collection<Permission> permissions, AuthorizationInfo info) {
        log.info("==enter SystemAuthorizingRealm login 777=ssss=");
        if (permissions != null && !permissions.isEmpty()) {
            for (Permission permission : permissions) {
                authorizationValidate(permission);
            }
        }
        return super.isPermittedAll(permissions, info);
    }

    /**
     * 授权验证方法
     *
     * @param permission
     */
    private void authorizationValidate(Permission permission) {
        // 模块授权预留接口
        log.info("==enter SystemAuthorizingRealm authorizationValidate=permission==" + permission.toString());
    }
}
