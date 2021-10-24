package com.cmmplb.shiro.controller;

import com.cmmplb.core.constants.StringConstants;
import com.cmmplb.core.exception.CustomException;
import com.cmmplb.core.result.Result;
import com.cmmplb.core.result.ResultUtil;
import com.cmmplb.shiro.config.properties.ShiroProperties;
import com.cmmplb.shiro.constants.HttpConstants;
import com.cmmplb.shiro.dto.LoginDTO;
import com.cmmplb.shiro.service.LoginService;
import com.cmmplb.shiro.utils.CaptchaUtil;
import com.cmmplb.shiro.vo.LoginVO;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author penglibo
 * @date 2021-09-20 00:08:57
 * @since jdk 1.8
 */

@Api(tags = "登录管理")
@ApiSupport(order = 3, author = StringConstants.AUTHOR)
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private ShiroProperties shiroProperties;

    @ApiOperation("账号密码登录")
    @ApiOperationSupport(order = 1)
    @PostMapping("/do")
    public Result<LoginVO> doLogin(@Validated LoginDTO dto) {
        if (!shiroProperties.getSplit() && !CaptchaUtil.validate(dto.getUuid(), dto.getGraphCode())) {
            throw new CustomException(HttpConstants.VERIFICATION_CODE_ERROR);
        }
        return ResultUtil.success(loginService.doLogin(dto));
    }

    @ApiOperation("手机号登录")
    @ApiOperationSupport(order = 2)
    @PostMapping("/mobile")
    public Result<LoginVO> doMobileLogin(@Validated LoginDTO dto) {
        return ResultUtil.success(loginService.doLogin(dto));
    }

    @ApiOperation("退出登录")
    @ApiOperationSupport(order = 3)
    @PostMapping("/logout")
    public Result<Boolean> doLogout() {
        return ResultUtil.success(loginService.doLogout());
    }
}
