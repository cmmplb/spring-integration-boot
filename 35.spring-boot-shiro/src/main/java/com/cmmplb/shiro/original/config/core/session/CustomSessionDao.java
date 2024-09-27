package com.cmmplb.shiro.original.config.core.session;

import com.alibaba.fastjson.JSON;
import com.cmmplb.redis.service.RedisService;
import com.cmmplb.shiro.general.constants.ShiroConstants;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.springframework.util.Assert;

import java.io.Serializable;

/**
 * @author penglibo
 * @date 2021-09-30 17:34:00
 * @since jdk 1.8
 * SessionDAO定义了从持久层操作session的标准；
 * AbstractSessionDAO提供了SessionDAO的基础实现, 如生成会话ID等；
 * CachingSessionDAO提供了对开发者透明的session缓存的功能, 只需要设置相应的 CacheManager 即可；
 * MemorySessionDAO直接在内存中进行session维护；
 * EnterpriseCacheSessionDAO提供了缓存功能的session维护, 默认情况下使用 MapCache 实现, 内部使用ConcurrentHashMap保存缓存的会话. 
 * 默认提供了MemorySessionDAO持久化到内存
 */

@Data
@Slf4j
@EqualsAndHashCode(callSuper = true)
public class CustomSessionDao extends CachingSessionDAO {

    private RedisService redisService;

    private static final long DEFAULT_SESSION_IN_CACHE_TIMEOUT = 60 * 1000L;

    private String getKey(Serializable sessionId) {
        return ShiroConstants.SESSION_PREFIX + sessionId;
    }

    @Override
    protected void doUpdate(Session session) {
        log.info("doUpdate session");
    }

    @Override
    protected void doDelete(Session session) {
        log.info("doDelete session");
        Assert.notNull(session, "session not be null");
        Assert.notNull(session.getId(), "session id not be null");
        redisService.del(getKey(session.getId()));
    }

    /**
     * 创建完session后会调用该方法
     */
    @Override
    protected Serializable doCreate(Session session) {
        log.info("doCreate session");
        Serializable sessionId = this.generateSessionId(session);
        assignSessionId(session, sessionId);
        redisService.set(getKey(sessionId), JSON.toJSONString(session), DEFAULT_SESSION_IN_CACHE_TIMEOUT);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        log.info("doRead session");
        SimpleSession session = JSON.parseObject(redisService.get(getKey(sessionId)).toString(), SimpleSession.class);
        if (null != session) {
            session.setId(sessionId);
            System.out.println("session:" + session);
            return session;
        }
        return null;
    }
}
