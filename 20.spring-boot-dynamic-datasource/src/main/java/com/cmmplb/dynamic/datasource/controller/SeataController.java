package com.cmmplb.dynamic.datasource.controller;

import io.github.cmmplb.core.result.Result;
import io.github.cmmplb.core.result.ResultUtil;
import com.cmmplb.dynamic.datasource.service.DemoService;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author penglibo
 * @date 2021-08-06 10:27:54
 * @since jdk 1.8
 */

@Api(tags = "seate事务演示")
@ApiSort(1)
@ApiSupport(order = 1, author = "张三")
@RestController
@RequestMapping("/seata")
public class SeataController {

    @Autowired
    private DemoService demoService;


    @PostMapping("/seate/buy")
    @ApiOperation("seate事务测试")
    public Result<Boolean> demo() {
        return ResultUtil.success(demoService.demo());
    }


}
