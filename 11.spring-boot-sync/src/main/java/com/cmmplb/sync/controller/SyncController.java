package com.cmmplb.sync.controller;

import com.cmmplb.core.constants.StringConstant;
import com.cmmplb.core.result.Result;
import com.cmmplb.core.result.ResultUtil;
import com.cmmplb.sync.service.SyncService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author penglibo
 * @date 2021-09-13 15:07:06
 * @since jdk 1.8
 */

@Tag(name = "异步线程演示")
@Slf4j
// @ApiSupport > @ApiSort > @Api  -  排序的规则是倒序
@ApiSort(1)
// 作者,方法名上ApiOperationSupport.author没有则取类名声明的作者
@ApiSupport(order = 1, author = StringConstant.AUTHOR)
@RestController
@RequestMapping("/sync")
public class SyncController {

    @Autowired
    private SyncService syncService;

    @Operation(summary = "同步方法", description = "同步方法")
    @ApiOperationSupport(order = 1)
    @GetMapping("/sync")
    public Result<String> sync() {
        long start = System.currentTimeMillis();
        syncService.sync();
        long end = System.currentTimeMillis();
        return ResultUtil.success("总耗时：" + (end - start) + " ms");
    }

    @Operation(summary = "异步方法")
    @ApiOperationSupport(order = 2)
    @GetMapping("/async")
    public Result<String> async() {
        long start = System.currentTimeMillis();
        syncService.async();
        long end = System.currentTimeMillis();
        return ResultUtil.success("总耗时：" + (end - start) + " ms");
    }

    @Operation(summary = "异步方法-带返回值")
    @ApiOperationSupport(order = 3)
    @GetMapping("/async/return")
    public Result<String> asyncReturn() throws ExecutionException, InterruptedException, TimeoutException {
        // 当调用get时方法为阻塞状态
        return ResultUtil.success(syncService.asyncReturn().get());
        // 重载方法, 当时间超过设置时间将会抛出TimeoutException
        // return ResultUtil.success(syncService.asyncReturn().get(1, TimeUnit.SECONDS));
    }

    @Operation(summary = "异步执行多个方法,没有返回值")
    @ApiOperationSupport(order = 4)
    @GetMapping("/async/all")
    public Result<String> asyncAll() throws ExecutionException, InterruptedException {

        long start = System.currentTimeMillis();

        CompletableFuture<Void> runAsync1 = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("runAsync1");
        });

        CompletableFuture<Void> runAsync2 = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("runAsync2");
        });

        // 等待两个CompletableFuture都完成的时候, future才会完成
        CompletableFuture<Void> future = CompletableFuture.allOf(runAsync1, runAsync2);

        // 阻塞到CompletableFuture1完成
        future.get();
        // 耗时大于3000毫秒
        log.info("ms:{}", System.currentTimeMillis() - start);
        return ResultUtil.success();
    }

    @Autowired
    private Executor one;

    @Operation(summary = "异步执行多个方法, 返回所有方法的返回值")
    @ApiOperationSupport(order = 5)
    @GetMapping("/async/all/return")
    public Result<Map<String, Object>> asyncAllReturn() throws ExecutionException, InterruptedException {

        long start = System.currentTimeMillis();

        CompletableFuture<Integer> runAsync1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("runAsync1, thread-name:" + Thread.currentThread().getName());
            return 10;
        });

        // 这里supplyAsync中添加一个THREAD_POOL_EXECUTOR线程池配置
        CompletableFuture<Integer> runAsync2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("runAsync2, thread-name:" + Thread.currentThread().getName());
            return 20;
        }, THREAD_POOL_EXECUTOR);

        // 这里supplyAsync中添加一个one线程池配置的bean
        CompletableFuture<Integer> runAsync3 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("runAsync3, thread-name:" + Thread.currentThread().getName());
            return 30;
        }, one);

        // 等待两个CompletableFuture都完成的时候, future才会完成
        // CompletableFuture<Void> future = CompletableFuture.allOf(runAsync1, runAsync2, runAsync3);
        // 阻塞到CompletableFuture1完成
        // future.get();

        // 使用join也可以阻塞
        CompletableFuture.allOf(runAsync1, runAsync2, runAsync3).join();

        Integer runAsync1Num = runAsync1.get();
        Integer runAsync2Num = runAsync2.get();
        Integer runAsync3Num = runAsync3.get();
        Map<String, Object> map = new HashMap<>();
        map.put("runAsync1Num", runAsync1Num);
        map.put("runAsync2Num", runAsync2Num);
        map.put("runAsync3Num", runAsync3Num);
        map.put("millis", System.currentTimeMillis() - start);
        // 耗时大于3000毫秒
        log.info("ms:{}", System.currentTimeMillis() - start);
        return ResultUtil.success(map);
    }

    /**
     * CPU核数
     */
    private static final int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();

    private static final ThreadPoolExecutor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(
            // 核心线程数
            AVAILABLE_PROCESSORS,
            // 最大线程数
            3 * AVAILABLE_PROCESSORS,
            // keepAliveTime
            3,
            TimeUnit.SECONDS,
            // 阻塞队列
            new LinkedBlockingDeque<>(20),
            r -> new Thread(r, "sync-thread-" + r.hashCode()));

    /**
     * 使用CompletionService批量异步处理
     * <p>
     * CompletionService 能够让异步任务的执行结果有序化, 先执行完的先进入阻塞队列,
     * 利用这个特性, 你可以轻松实现后续处理的有序性, 避免无谓的等待
     */
    @Operation(summary = "使用CompletionService批量异步处理")
    @ApiOperationSupport(order = 6)
    @GetMapping("/async/completion/service")
    public static void completionService() {
        Map<String, Object> dataMap = new ConcurrentHashMap<>();

        CompletionService<Map<String, Object>> cs = new ExecutorCompletionService<>(THREAD_POOL_EXECUTOR);
        cs.submit(() -> {
            Thread.sleep(3000);
            dataMap.put("A", "");
            log.info("A, thread-name:" + Thread.currentThread().getName());
            return dataMap;
        });
        cs.submit(() -> {
            Thread.sleep(2000);
            dataMap.put("B", "");
            log.info("B, thread-name:" + Thread.currentThread().getName());
            return dataMap;
        });
        cs.submit(() -> {
            Thread.sleep(1000);
            dataMap.put("C", "");
            log.info("C, thread-name:" + Thread.currentThread().getName());
            return dataMap;
        });

        for (int i = 0; i < 3; i++) {
            Map<String, Object> resultMap = null;
            try {
                //依次从队列中取任务执行结果
                resultMap = cs.take().get();
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("resultMap:{}", resultMap);
        }
    }
}
