package com.cmmplb.log.controller;

import com.cmmplb.core.constants.StringConstant;
import com.cmmplb.core.result.Result;
import com.cmmplb.core.result.ResultUtil;
import com.cmmplb.log.annotations.Log;
import com.cmmplb.log.constants.LogConstant;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author penglibo
 * @date 2021-04-14 15:01:09
 */

// @ApiSupport > @ApiSort > @Api  -  排序的规则是倒序
@ApiSort(1)
// 作者,方法名上ApiOperationSupport.author没有则取类名声明的作者
@ApiSupport(order = 1, author = StringConstant.AUTHOR)
@RestController
@RequestMapping("/test")
public class TestController {

    @Operation(summary = "one", description = "one")
    @ApiOperationSupport(order = 1)
    @GetMapping("/one")
    @Log(type = LogConstant.LogOperationTypeEnum.ONE, content = "one", businessType = LogConstant.LogBusinessTypeEnum.SAVE)
    public Result<Boolean> methodOne(String name) {
        return ResultUtil.success(true);
    }

    @Operation(summary = "two")
    @ApiOperationSupport(order = 2)
    @GetMapping("/two")
    @Log(type = LogConstant.LogOperationTypeEnum.TWO, content = "two", businessType = LogConstant.LogBusinessTypeEnum.SAVE)
    public Result<Boolean> methodTwo() throws InterruptedException {
        Thread.sleep(2000);
        return ResultUtil.success(true);
    }

    @Operation(summary = "three")
    @ApiOperationSupport(order = 2)
    @GetMapping("/three")
    @Log(type = LogConstant.LogOperationTypeEnum.THREE, content = "three", businessType = LogConstant.LogBusinessTypeEnum.SAVE)
    public Result<Boolean> methodThree(String name, String age) {
        return ResultUtil.success(true);
    }

    @Operation(summary = "ex")
    @ApiOperationSupport(order = 3)
    @GetMapping("/ex")
    @Log(type = LogConstant.LogOperationTypeEnum.EX, content = "测试异常", businessType = LogConstant.LogBusinessTypeEnum.SAVE)
    public Result<Boolean> methodEx(String name, String age) {
        System.out.println("异常");
        System.out.println(1 / 0);
        return ResultUtil.success(true);
    }
}
