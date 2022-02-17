package com.cmmplb.shiro.general.constants;

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
     * 认证缓存前缀: 【key:前缀+令牌token,值:用户ID】
     */
    String AUTH_UID_CACHE_PREFIX = "auth:uid:token:";

    /**
     * 认证缓存过期时间(单位:秒)-30分钟
     */
    int AUTH_CACHE_EXPIRE_SECONDS = 60 * 30;

    /**
     * 用户信息缓存前缀: 【key:前缀+用户id,值:用户信息】
     */
    String AUTH_UID_INFO_PREFIX = "auth:uid:info:";

    /**
     * 用户令牌列表(多端登录使用): 【key:前缀+用户id,值:令牌列表】
     */
    String UID_TOKENS_CACHE_PREFIX = "auth:uid:tokens:";

    /**
     * 用户权限缓存前缀: 【key:前缀+用户id,值:权限缓存】
     */
    String UID_PERMISSIONS_CACHE_PREFIX = "auth:uid:permissions:";

    /**
     * 权限缓存过期时间(单位:秒)-30分钟
     */
    int AUTH_PERMISSIONS_CACHE_EXPIRE_SECONDS = 60 * 5;
}
