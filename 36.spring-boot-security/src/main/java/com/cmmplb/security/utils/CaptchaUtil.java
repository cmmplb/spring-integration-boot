package com.cmmplb.security.utils;

import com.cmmplb.core.utils.ServletUtil;
import com.cmmplb.core.utils.SpringUtil;
import com.cmmplb.redis.service.RedisService;
import com.cmmplb.security.constants.RedisConstant;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @author penglibo
 * @date 2021-06-04 11:27:09
 * @since jdk 1.8
 * 图形验证码工具类
 */

@Slf4j
public class CaptchaUtil {

    /**
     * 创建验证码
     * @param uuid 唯一标识
     * @throws IOException i
     */
    public static void create(String uuid) throws IOException {
        // web写入
        HttpServletResponse response = ServletUtil.getResponse();
        response.setContentType("image/gif");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        //生成验证码
        SpecCaptcha captcha = new SpecCaptcha(75, 40);
        // 验证码个数
        captcha.setLen(1);
        captcha.setCharType(Captcha.TYPE_DEFAULT);
        captcha.out(response.getOutputStream());


        String text = captcha.text();
        log.info("图形验证码：" + text);
        String base64 = captcha.toBase64();

        //保存到缓存
        setCache(uuid, text);
        // 返回base64
        // return base64;
    }

    /**
     * 校验验证码
     * @param uuid uuid
     * @param graphCode 验证码
     * @return boolean
     */
    public static boolean validate(String uuid, String graphCode) {
        return graphCode.equalsIgnoreCase(getCache(uuid));
    }

    /**
     * 保存验证码到缓存
     * @param uuid  uuid
     * @param value value
     */
    private static void setCache(String uuid, String value) {
        // 5分钟
        getRedisService().set(getCaptchaKey(uuid), value, RedisConstant.CAPTCHA_EXPIRE_SECONDS);
    }

    /**
     * 获取验证码的缓存Key
     */
    public static String getCaptchaKey(String uuid) {
        return RedisConstant.CAPTCHA_CACHE_PREFIX + uuid;
    }

    /**
     * 获取验证码
     * @param uuid uuid
     * @return code
     */
    private static String getCache(String uuid) {
        return (String) getRedisService().get(getCaptchaKey(uuid));
    }

    /**
     * 删除验证码
     * @param uuid uuid
     */
    public static void delete(String uuid) {
        getRedisService().del(getCaptchaKey(uuid));
    }

    /**
     * 从容器获取redisService
     * @return RedisService
     */
    public static RedisService getRedisService() {
        return SpringUtil.getBean(RedisService.class);
    }

}
