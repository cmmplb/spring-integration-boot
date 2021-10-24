package com.cmmplb.shiro.utils;


import com.cmmplb.core.utils.RandomUtil;
import org.apache.shiro.crypto.hash.SimpleHash;

;

/**
 * @author : plb
 * @createdate: 2019/12/25
 * MD5加密工具
 */

public class MD5Util extends com.cmmplb.core.utils.MD5Util {

    /**
     * 加密循环次数
     **/
    public final static int HASH_ITERATIONS = 1;

    /**
     * 循环盐值加密
     * @param str            待加密字符串
     * @param salt           盐值
     * @param hashIterations 循环次数
     */
    public static String cycleEncode(String str, String salt, int hashIterations) {
        return new SimpleHash(MD5, str, salt, hashIterations).toString();
    }

    /**
     * 循环加密
     * @param str            待加密字符串
     * @param hashIterations 循环次数
     */
    public static String cycleEncode(String str, int hashIterations) {
        return new SimpleHash(MD5, str, null, hashIterations).toString();
    }

    /**
     * 盐值加密
     * @param str  待加密字符串
     * @param salt 盐值
     */
    public static String saltEncode(String str, String salt) {
        return new SimpleHash(MD5, str, salt, HASH_ITERATIONS).toString();
    }

    /**
     * 普通加密
     * @param str 待加密字符串
     */
    public static String encode(String str) {
        return com.cmmplb.core.utils.MD5Util.encode(str);
    }

    /**
     * 获取随机盐值
     * @param length 盐长度
     */
    public static String getRandomSalt(int length) {
        return RandomUtil.getRandomString(length);
    }
}
