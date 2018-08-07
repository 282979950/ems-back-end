package com.ems.service;

import com.ems.shiro.realm.CustomAuthorizingRealm;
import com.ems.shiro.session.SessionDAO;
import com.ems.utils.DigestUtils;
import com.ems.utils.Encodes;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * 系统管理，安全相关实体的管理类,包括用户、角色、菜单.
 *
 * @author litairan
 */
@Service
public class SystemService implements InitializingBean {

    public static final String HASH_ALGORITHM = "SHA-1";

    public static final int HASH_INTERATIONS = 1024;

    public static final int SALT_SIZE = 8;

    @Autowired
    private SessionDAO sessionDao;

    @Autowired
    private CustomAuthorizingRealm systemRealm;

    public SessionDAO getSessionDao() {
        return sessionDao;
    }

    /**
     * 生成安全的密码，生成随机的16位salt并经过1024次 sha-1 hash
     */
    public static String entryptPassword(String plainPassword) {
        String plain = Encodes.unescapeHtml(plainPassword);
        byte[] salt = DigestUtils.generateSalt(SALT_SIZE);
        byte[] hashPassword = DigestUtils.sha1(plain.getBytes(), salt, HASH_INTERATIONS);
        return Encodes.encodeHex(salt) + Encodes.encodeHex(hashPassword);
    }

    /**
     * 验证密码
     *
     * @param plainPassword 明文密码
     * @param password      密文密码
     * @return 验证成功返回true
     */
    public static boolean validatePassword(String plainPassword, String password) {
        String plain = Encodes.unescapeHtml(plainPassword);
        byte[] salt = Encodes.decodeHex(password.substring(0, 16));
        byte[] hashPassword = DigestUtils.sha1(plain.getBytes(), salt, HASH_INTERATIONS);
        return password.equals(Encodes.encodeHex(salt) + Encodes.encodeHex(hashPassword));
    }

    /**
     * 获得活动会话
     *
     * @return
     */
    public Collection<Session> getActiveSessions() {
        return sessionDao.getActiveSessions(false);
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}

