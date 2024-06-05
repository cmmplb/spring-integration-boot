package com.cmmplb.security.controller;

import com.cmmplb.core.constants.StringConstants;
import com.cmmplb.core.result.Result;
import com.cmmplb.core.result.ResultUtil;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

/**
 * @author penglibo
 * @date 2021-09-20 20:10:46
 * @since jdk 1.8
 */

@Api(tags = "注解权限管理")
@ApiSupport(order = 1, author = StringConstants.AUTHOR)
@RestController
@RequestMapping("/annotation")
public class AnnotationController {

    @ApiOperation("查询注解控制")
    @ApiOperationSupport(order = 1)
    @GetMapping("/info")
    @Secured("user")
    @RolesAllowed("ROLE_user")
    @PreAuthorize("hasAnyAuthority('user')")
    public Result<String> getInfo() {
        return ResultUtil.success("注解控制");
    }
}
