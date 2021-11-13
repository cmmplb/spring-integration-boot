package com.cmmplb.shiro.config.shiro.session;

import com.cmmplb.common.redis.service.RedisService;
import com.cmmplb.core.threads.ThreadContext;
import com.cmmplb.core.utils.SpringUtil;
import com.cmmplb.shiro.constants.AuthorizationConstants;
import com.cmmplb.core.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * @author penglibo
 * @date 2021-09-27 11:25:39
 * @since jdk 1.8
 * 支持web应用的{@link org.apache.shiro.session.mgt.SessionManager}实现
 */

@Slf4j
public class ShiroSessionManager extends DefaultWebSessionManager {

    //重写构造器
    public ShiroSessionManager() {
        super();
    }

    /**
     * 重写获取sessionId规则，从请求头获取
     */
    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        log.info("getSessionId");
        HttpServletRequest req = (HttpServletRequest) request;
        // always set rewrite flag - SHIRO-361
        request.setAttribute(ShiroHttpServletRequest.SESSION_ID_URL_REWRITING_ENABLED, isSessionIdUrlRewritingEnabled());

        //获取请求头中的数据
        String token = req.getHeader(AuthorizationConstants.AUTHORIZATION);
        if (StringUtil.isEmpty(token)) {
            //如果没有携带，生成新的sessionId
            // return super.getSessionId(request, response); // 父类是通过org.apache.shiro.web.servlet.SimpleCookie#readValue读取默认cookie的JSESSIONID
            //禁用cookie、sessionk
            return null;
        }
        // if (check(token)) return null;

        // 设置SESSION_ID来源为header
        request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, AuthorizationConstants.HEADER);
        request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, token);
        request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
        return token;
    }

    private boolean check(String token) {
        // 校验令牌是否有效
        RedisService redisService = SpringUtil.getBean(RedisService.class);
        String tokenUidCacheKey = AuthorizationConstants.AUTHORIZATION_UID_CACHE_PREFIX + token;
        Object uid = redisService.get(tokenUidCacheKey);
        if (null == uid) {
            return true;
        }
        // 刷新认证时间
        redisService.expire(tokenUidCacheKey, AuthorizationConstants.AUTHORIZATION_UID_CACHE_EXPIRE_SECONDS);
        // 异步读取用户多端token
        ThreadContext.executeTask(() -> {
            // 取出用户最早存入的token
            String tokenOld = (String) redisService.rpop(AuthorizationConstants.UID_TOKENS_CACHE_PREFIX + uid);
            if (redisService.hasKey(AuthorizationConstants.AUTHORIZATION_PREFIX + tokenOld)) {
                redisService.lpush(AuthorizationConstants.UID_TOKENS_CACHE_PREFIX + uid, tokenOld);
            }
        });
        return false;
    }
}
