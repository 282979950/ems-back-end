package com.tdmh.util;

import com.tdmh.entity.Principal;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;



/**
 * shiro工具类
 *
 * @author litairan on 2018/8/1.
 */
public class ShiroUtils {

    /**
     * 获取授权主要对象
     */
    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    /**
     * 获取当前登录者对象
     */
    public static Principal getPrincipal() {
        Subject subject = SecurityUtils.getSubject();
        return (Principal) subject.getPrincipal();
    }

    /**
     * 获取会话对象
     */
    public static Session getSession() {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession(false);
        if (session == null) {
            return subject.getSession();
        } else {
            return session;
        }
    }
}
