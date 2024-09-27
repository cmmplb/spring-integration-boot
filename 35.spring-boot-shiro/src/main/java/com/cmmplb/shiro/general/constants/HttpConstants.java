package com.cmmplb.shiro.general.constants;

/**
 * @author penglibo
 * @date 2021-09-28 15:38:28
 * @since jdk 1.8
 */
public interface HttpConstants {

    String VERIFICATION_CODE_ERROR = "验证码错误";
    String INCORRECT_CREDENTIALS = "用户名不存在或密码错误";
    String ACCOUNT_LOCKED = "账号被锁定";
    String AUTHENTICATION = "用户信息不存在";
    String LOGIN_FAIL = "登录失败";
    String SESSION_INVALID = "session已失效, 请重新认证";
}
