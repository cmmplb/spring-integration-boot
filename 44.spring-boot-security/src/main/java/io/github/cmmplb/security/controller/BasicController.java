package io.github.cmmplb.security.controller;

import io.github.cmmplb.core.constants.StringConstant;
import io.github.cmmplb.core.result.HttpCodeEnum;
import io.github.cmmplb.core.result.Result;
import io.github.cmmplb.core.result.ResultUtil;
import io.github.cmmplb.security.utils.CaptchaUtil;
import io.github.cmmplb.security.utils.SmsCodeUtil;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

@Api(tags = "基础管理")
@ApiSupport(order = 1, author = StringConstant.AUTHOR)
@RestController
@RequestMapping("/basic")
public class BasicController {

    @SneakyThrows
    @ApiOperation("获取图形验证码")
    @ApiOperationSupport(order = 1)
    @GetMapping("/graph/code")
    public void getValidateCode(String uuid) {
        CaptchaUtil.create(uuid);
    }

    @ApiOperation("获取短信验证码")
    @ApiOperationSupport(order = 2)
    @GetMapping("/sms/code")
    public Result<String> getSmsCode(String mobile) {
        // 发送短信验证码...
        return ResultUtil.success(SmsCodeUtil.create(mobile)); // 这里难得看控制台了, 直接返回用来测试看验证码了
    }

    @ApiOperation("登录失败处理")
    @ApiOperationSupport(order = 3)
    @PostMapping("/failure")
    public Result<String> loginFailure() {
        return ResultUtil.custom("登录失败");
    }

    @ApiOperation("session失效处理")
    @ApiOperationSupport(order = 4)
    @GetMapping("/session/invalid")
    public Result<String> sessionInvalid() {
        return ResultUtil.custom("session已失效, 请重新认证");
    }

    @ApiOperation("未授权")
    @ApiOperationSupport(order = 5)
    @GetMapping("/unauthorized")
    public Result<String> unauthorized() {
        return ResultUtil.custom(HttpCodeEnum.FORBIDDEN);
    }
}
