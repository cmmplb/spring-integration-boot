package com.cmmplb.security.controller;

import io.github.cmmplb.core.constants.StringConstant;
import io.github.cmmplb.core.result.Result;
import io.github.cmmplb.core.result.ResultUtil;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author penglibo
 * @date 2021-09-19 22:43:56
 * @since jdk 1.8
 */

@Api(tags = "用户管理")
@ApiSupport(order = 3, author = StringConstant.AUTHOR)
@RestController
@RequestMapping("/user")
public class UserController {

    @ApiOperation("查询")
    @ApiOperationSupport(order = 1)
    @GetMapping("/info")
    public Result<String> getInfo() {
        return ResultUtil.success("张三");
    }
}
