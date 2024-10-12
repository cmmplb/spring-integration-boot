package io.github.cmmplb.security.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.github.cmmplb.core.constants.StringConstant;
import io.github.cmmplb.core.result.Result;
import io.github.cmmplb.core.result.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author penglibo
 * @date 2021-09-19 22:39:32
 * @since jdk 1.8
 */

@Api(tags = "资源管理")
@ApiSupport(order = 3, author = StringConstant.AUTHOR)
@RestController
@RequestMapping("/resource")
public class ResourceController {

    @ApiOperation("查询")
    @ApiOperationSupport(order = 1)
    @GetMapping("/info")
    public Result<String> getInfo() {
        return ResultUtil.success("账户");
    }
}
