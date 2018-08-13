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

    private CacheManager cacheManager;

    /**
     * 活动会话缓存
     * key为用户ID，value为会话队列
     */
    private Cache<Integer, Deque<Serializable>> activeSessionCache;

    /**
     * 授权缓存
     */
    private Cache<Principal, SimpleAuthorizationInfo> authorizationInfoCache;

    @Override
    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
        this.activeSessionCache = cacheManager.getCache("shiro-activeSessionCache");
        this.authorizationInfoCache = cacheManager.getCache("authorizationInfo-cache");
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
        authorizationInfoCache.remove(principal);
        return new SimpleAuthenticationInfo(principal, emp.getEmpPassword(), ByteSource.Util.bytes(Global.DEFAULT_MD5_SALT), getName());
    }

    /**
     * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        log.info("==开始获取授权信息====");

        Principal principal = (Principal) getAvailablePrincipal(principals);

        Employee emp = employeeService.getEmpByLoginName(principal.getLoginName());
        if (emp == null) {
            return null;
        }
        Session session = ShiroUtils.getSession();
        session.setAttribute(Global.SESSION_USER_ID, emp.getEmpId());

        SimpleAuthorizationInfo info = authorizationInfoCache.get(principal);
        if (info == null) {
            info = new SimpleAuthorizationInfo();
        } else {
            return info;
        }
        Set<SysRole> roles = emp.getRoles();
        for (SysRole role : roles) {
            info.addRole(role.getRoleName());
            Set<SysPermission> permissions = role.getPermissions();
            for (SysPermission permission : permissions) {
                info.addStringPermission(permission.getPermName());
            }
        }
        info.addRole("user");
        authorizationInfoCache.put(principal, info);
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
        SimpleAuthorizationInfo info = authorizationInfoCache.get(principal);
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
