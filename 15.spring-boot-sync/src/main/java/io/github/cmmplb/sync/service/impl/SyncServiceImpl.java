package io.github.cmmplb.sync.service.impl;

import io.github.cmmplb.sync.service.SyncService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author penglibo
 * @date 2021-09-13 15:12:38
 * @since jdk 1.8
 */

@Service
public class SyncServiceImpl implements SyncService {

    @Override
    public void sync() {
        sleep();
    }

    @Async("one")
    @Override
    public void async() {
        long start = System.currentTimeMillis();
        sleep();
        long end = System.currentTimeMillis();
        System.out.println("sleep," + (end - start) + " ms, " + Thread.currentThread().getName());
    }

    @Async("one") // 配置使用自定义线程池
    @Override
    public Future<String> asyncReturn() {
        long start = System.currentTimeMillis();
        sleep();
        long end = System.currentTimeMillis();
        return new AsyncResult<>("总耗时：" + (end - start) + " ms");
    }

    private void sleep() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
