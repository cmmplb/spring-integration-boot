package com.cmmplb.security.constants;

/**
 * @author penglibo
 * @date 2021-12-08 16:05:15
 * @since jdk 1.8
 */
public interface RedisConstants {

    long GLOBAL_SESSION_TIMEOUT = 60 * 30;

    int SESSION_EXPIRE = 60 * 30;

    int CAPTCHA_EXPIRE_SECONDS = 60 * 5;

    int CAPTCHA_CACHE_PREFIX = 60 * 5;

    String SESSION_KEY = "session:key";

    String SMS_CACHE_PREFIX = "sms:cache:prefix";


}
