package com.cmmplb.shiro.controller;

import com.cmmplb.core.constants.StringConstants;
import com.cmmplb.core.result.Result;
import com.cmmplb.core.result.ResultUtil;
import com.cmmplb.shiro.utils.ShiroUtil;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * @author penglibo
 * @date 2021-09-20 00:16:57
 * @since jdk 1.8
 */

@Api(tags = "个人中心管理")
@ApiSupport(order = 4, author = StringConstants.AUTHOR)
@RestController
@RequestMapping("/personal/center")
public class PersonalCenterController {

    @ApiOperation("查询principal")
    @ApiOperationSupport(order = 1)
    @GetMapping("/principal")
    public Result<Principal> getPrincipal(Principal principal) {
        return ResultUtil.success(principal);
    }

    @ApiOperation("查询request里的principal")
    @ApiOperationSupport(order = 2)
    @GetMapping("/request/principal")
    public Result<Principal> getUserPrincipal(HttpServletRequest request) {
        return ResultUtil.success(request.getUserPrincipal());
    }

    @ApiOperation("查询shiro中的principal")
    @ApiOperationSupport(order = 3)
    @GetMapping("/shiro/principal")
    public Result<Object> getPrincipal() {
        return ResultUtil.success(ShiroUtil.getSubject().getPrincipal());
    }
}
