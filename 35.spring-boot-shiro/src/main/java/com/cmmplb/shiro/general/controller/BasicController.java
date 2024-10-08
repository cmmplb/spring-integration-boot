package com.cmmplb.shiro.general.controller;

import io.github.cmmplb.core.result.HttpCodeEnum;
import io.github.cmmplb.core.result.Result;
import io.github.cmmplb.core.result.ResultUtil;
import com.cmmplb.shiro.general.constants.HttpConstants;
import com.cmmplb.shiro.general.utils.CaptchaUtil;
import com.cmmplb.shiro.general.utils.SmsCodeUtil;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author penglibo
 * @date 2021-09-20 00:08:57
 * @since jdk 1.8
 */

@RestController
@RequestMapping("/basic")
public class BasicController {

    @SneakyThrows
    @GetMapping("/graph/code")
    public void getValidateCode(String uuid) {
        CaptchaUtil.create(uuid);
    }

    @GetMapping("/sms/code")
    public Result<String> getSmsCode(String phone) {
        // 发送短信验证码...
        return ResultUtil.success(SmsCodeUtil.create(phone)); // 这里难得看控制台了，直接返回用来测试看验证码了
    }

    @PostMapping("/failure")
    public Result<String> loginFailure() {
        return ResultUtil.custom(HttpConstants.LOGIN_FAIL);
    }

    @GetMapping("/session/invalid")
    public Result<String> sessionInvalid() {
        return ResultUtil.custom(HttpConstants.SESSION_INVALID);
    }

    @GetMapping("/un/login")
    public Result<String> unLogin() {
        return ResultUtil.custom(HttpCodeEnum.UNAUTHORIZED);
    }

    @GetMapping("/unauthorized")
    public Result<String> unauthorized() {
        return ResultUtil.custom(HttpCodeEnum.FORBIDDEN);
    }

}
