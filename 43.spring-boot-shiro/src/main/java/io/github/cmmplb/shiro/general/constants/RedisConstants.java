package io.github.cmmplb.shiro.general.constants;

/**
 * @author penglibo
 * @date 2021-12-08 16:05:15
 * @since jdk 1.8
 */
public interface RedisConstants {

    int CAPTCHA_EXPIRE_SECONDS = 60 * 5;

    int CAPTCHA_CACHE_PREFIX = 60 * 5;

    String SMS_CACHE_PREFIX = "sms:cache:";

}
