package com.ems.shiro.session;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

/**
 * 会话监听器
 *
 * @author litairan on 2018/8/3.
 */
@Slf4j
public class CustomSessionListener implements SessionListener {

    @Override
    public void onStart(Session session) {
        log.info("会话开始，session:{}", session.toString());
    }

    @Override
    public void onStop(Session session) {
        log.info("会话结束:{}", session.getId());
    }

    @Override
    public void onExpiration(Session session) {
        log.info("会话过期:{}", session.getId());
    }
}
