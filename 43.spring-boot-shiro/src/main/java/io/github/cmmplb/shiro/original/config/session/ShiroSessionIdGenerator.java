package io.github.cmmplb.shiro.original.config.session;

import io.github.cmmplb.core.utils.UUIDUtil;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;

import java.io.Serializable;

/**
 * @author penglibo
 * @date 2021-09-27 11:31:16
 * @since jdk 1.8
 * 自定义SessionId生成器
 */

public class ShiroSessionIdGenerator implements SessionIdGenerator {

    @Override
    public Serializable generateId(Session session) {
        return UUIDUtil.uuidTrim();
    }
}
