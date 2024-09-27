package com.cmmplb.security.controller;

import com.cmmplb.core.constants.StringConstant;
import com.cmmplb.core.result.HttpCodeEnum;
import com.cmmplb.core.result.Result;
import com.cmmplb.core.result.ResultUtil;
import com.cmmplb.security.utils.CaptchaUtil;
import com.cmmplb.security.utils.SmsCodeUtil;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "基础管理")
@ApiSupport(order = 2, author = StringConstant.AUTHOR)
@RestController
@RequestMapping("/basic")
public class BasicController {

    @SneakyThrows
    @Operation(summary = "获取图形验证码")
    @ApiOperationSupport(order = 1)
    @GetMapping("/graph/code")
    public void getValidateCode(String uuid) {
        CaptchaUtil.create(uuid);
    }

    @Operation(summary = "获取短信验证码")
    @ApiOperationSupport(order = 2)
    @GetMapping("/sms/code")
    public Result<String> getSmsCode(String mobile) {
        // 发送短信验证码...
        // 这里难得看控制台了, 直接返回用来测试看验证码了
        return ResultUtil.success(SmsCodeUtil.create(mobile));
    }

    @Operation(summary = "登录失败处理")
    @ApiOperationSupport(order = 3)
    @PostMapping("/failure")
    public Result<String> loginFailure() {
        return ResultUtil.custom("登录失败");
    }

    @Operation(summary = "session失效处理")
    @ApiOperationSupport(order = 4)
    @GetMapping("/session/invalid")
    public Result<String> sessionInvalid() {
        return ResultUtil.custom("session已失效, 请重新认证");
    }

    @Operation(summary = "未授权")
    @ApiOperationSupport(order = 5)
    @GetMapping("/unauthorized")
    public Result<String> unauthorized() {
        return ResultUtil.custom(HttpCodeEnum.FORBIDDEN);
    }
}
