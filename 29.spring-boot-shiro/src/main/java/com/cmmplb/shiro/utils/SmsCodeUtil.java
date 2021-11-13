package com.cmmplb.shiro.utils;

import com.cmmplb.common.redis.service.RedisService;
import com.cmmplb.core.constants.RedisConstants;
import com.cmmplb.core.utils.RandomUtil;
import com.cmmplb.core.utils.SpringUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author penglibo
 * @date 2021-09-26 14:42:23
 * @since jdk 1.8
 * 短信验证码工具类
 */

@Slf4j
public class SmsCodeUtil {

    /**
     * 创建手机验证码
     * @param phone 手机号
     */
    public static String create(String phone) {
        //生成验证码
        String code = RandomUtil.getRandomNumString(4);
        // 这里写死一个1234难得每次改勒。
        code = "1234";

        log.info("短信验证码：" + code);

        //保存到缓存
        setCache(phone, code);
        return code;
    }

    /**
     * 校验验证码
     * @param phone 手机号
     * @param code 验证码
     * @return boolean
     */
    public static boolean validate(String phone, String code) {
        return code.equalsIgnoreCase(getCache(phone));
    }

    private static void setCache(String phone, String value) {
        getRedisService().set(getCaptchaKey(phone), value, RedisConstants.CAPTCHA_EXPIRE_SECONDS); // 5分钟
    }

    /**
     * 获取验证码的缓存Key
     */
    public static String getCaptchaKey(String phone) {
        return RedisConstants.SMS_CACHE_PREFIX + phone;
    }

    /**
     * 获取验证码
     * @param phone phone
     * @return code
     */
    private static String getCache(String phone) {
        return (String) getRedisService().get(getCaptchaKey(phone));
    }

    /**
     * 删除验证码
     * @param phone phone
     */
    public static void delete(String phone) {
        getRedisService().del(getCaptchaKey(phone));
    }

    /**
     * 从容器获取redisService
     * @return RedisService
     */
    public static RedisService getRedisService() {
        return SpringUtil.getBean(RedisService.class);
    }
}
