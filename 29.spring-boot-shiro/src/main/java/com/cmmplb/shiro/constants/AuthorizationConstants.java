package com.cmmplb.shiro.constants;

/**
 * @author penglibo
 * @date 2021-09-27 11:22:25
 * @since jdk 1.8
 */
public interface AuthorizationConstants {

    /**
     * Authorization
     */
    String AUTHORIZATION = "Authorization";

    /**
     * header
     */
    String HEADER = "header";

    /**
     * 单线登录
     */
    String SINGLE_LINE = "singleLine";

    /**
     * 认证缓存前缀:key-前缀+令牌,值-用户ID
     */
    String AUTHORIZATION_UID_CACHE_PREFIX = "authorization:auth:uid:";

    /**
     * 认证缓存过期时间(单位:秒)-30分钟
     */
    int AUTHORIZATION_UID_CACHE_EXPIRE_SECONDS = 60 * 30;

    /**
     * 用户信息缓存前缀:key-前缀+用户id,值-用户信息
     */
    String AUTHORIZATION_PREFIX = "authorization:uid:uinfo:";

    /**
     * 用户令牌列表(多端登录使用):key-前缀+用户id,值-令牌列表
     */
    String UID_TOKENS_CACHE_PREFIX = "authorization:uid:authorizations:";
}
