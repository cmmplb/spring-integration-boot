package com.cmmplb.sync.controller;

import com.cmmplb.core.constants.StringConstants;
import com.cmmplb.core.result.Result;
import com.cmmplb.core.result.ResultUtil;
import com.cmmplb.sync.service.SyncService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author penglibo
 * @date 2021-09-13 15:07:06
 * @since jdk 1.8
 */

@Api(tags = "异步线程演示")
@Slf4j
@ApiSupport(order = 1, author = StringConstants.AUTHOR)
@RestController
@RequestMapping("/sync")
public class SyncController {

    @Autowired
    private SyncService syncService;

    @ApiOperation("同步方法")
    @ApiOperationSupport(order = 1)
    @GetMapping("/sync")
    public Result<String> sync() {
        long start = System.currentTimeMillis();
        syncService.sync();
        long end = System.currentTimeMillis();
        return ResultUtil.success("总耗时：" + (end - start) + " ms");
    }

    @ApiOperation("异步方法")
    @ApiOperationSupport(order = 2)
    @GetMapping("/async")
    public Result<String> async() {
        long start = System.currentTimeMillis();
        syncService.async();
        long end = System.currentTimeMillis();
        return ResultUtil.success("总耗时：" + (end - start) + " ms");
    }

    @ApiOperation("异步方法-带返回值")
    @ApiOperationSupport(order = 3)
    @GetMapping("/async/return")
    public Result<String> asyncReturn() throws ExecutionException, InterruptedException, TimeoutException {
        // 当调用get时方法为阻塞状态
        // return ResultUtil.success(syncService.asyncReturn().get());
        // 重载方法，当时间超过设置时间将会抛出TimeoutException
        return ResultUtil.success(syncService.asyncReturn().get(1, TimeUnit.SECONDS));
    }
}
