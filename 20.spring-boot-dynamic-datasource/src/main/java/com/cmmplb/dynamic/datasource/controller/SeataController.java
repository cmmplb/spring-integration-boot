package com.cmmplb.dynamic.datasource.controller;

import com.cmmplb.core.constants.StringConstant;
import com.cmmplb.core.result.Result;
import com.cmmplb.core.result.ResultUtil;
import com.cmmplb.dynamic.datasource.service.DemoService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author penglibo
 * @date 2021-08-06 10:27:54
 * @since jdk 1.8
 */

@Tag(name = "seate事务演示")
// @ApiSupport > @ApiSort > @Api  -  排序的规则是倒序
@ApiSort(1)
// 作者,方法名上ApiOperationSupport.author没有则取类名声明的作者
@ApiSupport(order = 1, author = StringConstant.AUTHOR)
@RestController
@RequestMapping("/seata")
public class SeataController {

    @Autowired
    private DemoService demoService;


    @PostMapping("/seate/buy")
    @Operation(summary = "seate事务测试", description = "seate事务测试")
    @ApiOperationSupport(order = 1)
    public Result<Boolean> demo() {
        return ResultUtil.success(demoService.demo());
    }


}
