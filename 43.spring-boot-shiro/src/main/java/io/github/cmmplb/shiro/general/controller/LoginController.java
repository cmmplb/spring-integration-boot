package io.github.cmmplb.shiro.general.controller;

import io.github.cmmplb.core.exception.CustomException;
import io.github.cmmplb.core.result.Result;
import io.github.cmmplb.core.result.ResultUtil;
import io.github.cmmplb.shiro.general.constants.HttpConstants;
import io.github.cmmplb.shiro.general.dto.LoginDTO;
import io.github.cmmplb.shiro.general.properties.ShiroProperties;
import io.github.cmmplb.shiro.general.service.LoginService;
import io.github.cmmplb.shiro.general.utils.CaptchaUtil;
import io.github.cmmplb.shiro.general.vo.LoginVO;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author penglibo
 * @date 2021-09-20 00:08:57
 * @since jdk 1.8
 */

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private ShiroProperties shiroProperties;

    @PostMapping("/do")
    public Result<LoginVO> doLogin(@Validated LoginDTO dto) {
        if (!shiroProperties.getSplit() && !CaptchaUtil.validate(dto.getUuid(), dto.getGraphCode())) {
            throw new CustomException(HttpConstants.VERIFICATION_CODE_ERROR);
        }
        return ResultUtil.success(loginService.doLogin(dto));
    }

    @PostMapping("/mobile")
    public Result<LoginVO> doMobileLogin(@Validated LoginDTO dto) {
        return ResultUtil.success(loginService.doLogin(dto));
    }

    @PostMapping("/logout")
    @RequiresPermissions(value = {"get_info", "delete_info"}, logical = Logical.AND)
    public Result<Boolean> doLogout() {
        return ResultUtil.success(loginService.doLogout());
    }
}
