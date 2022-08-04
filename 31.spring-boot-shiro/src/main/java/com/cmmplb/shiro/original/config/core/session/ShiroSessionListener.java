package com.cmmplb.shiro.original.config.core.session;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

/**
 * @author penglibo
 * @date 2021-09-27 11:47:57
 * @since jdk 1.8
 */

@Slf4j
public class ShiroSessionListener implements SessionListener {

    /**
     * 会话创建时触发
     */
    @Override
    public void onStart(Session session) {
        log.info("会话创建时触发:sessionId:" + session.getId());
    }

    /**
     * 会话过期时触发
     */
    @Override
    public void onStop(Session session) {
        log.info("会话过期时触发");
    }

    /**
     * 退出会话过期时触发
     */
    @Override
    public void onExpiration(Session session) {
        log.info("退出会话过期时触发");
    }

}
