package io.github.cmmplb.spark.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

/**
 * @author penglibo
 * @date 2025-06-03 09:35:55
 * @since jdk 1.8
 */

@Service
public class AsyncSparkService {

    private final SparkService sparkService;

    public AsyncSparkService(SparkService sparkService) {
        this.sparkService = sparkService;
    }

    @Async("sparkExecutor")
    public Future<Long> countWordsAsync(String text) {
        long result = sparkService.countWords(text);
        return new AsyncResult<>(result);
    }
}
