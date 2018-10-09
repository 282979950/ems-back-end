package com.tdmh.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;

/**
 * @author Administrator on 2018/9/19.
 */
@Slf4j
public class SessionListener implements org.apache.shiro.session.SessionListener {
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
