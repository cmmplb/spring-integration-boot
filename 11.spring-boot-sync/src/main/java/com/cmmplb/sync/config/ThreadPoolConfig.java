package com.cmmplb.sync.config;

import com.cmmplb.sync.config.properties.ThreadPoolProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author penglibo
 * @date 2021-09-13 15:06:31
 * @since jdk 1.8
 * 线程池配置
 */

@Slf4j
@EnableAsync
@Configuration
@EnableConfigurationProperties(ThreadPoolProperties.class)
public class ThreadPoolConfig {

    @Autowired
    private ThreadPoolProperties threadPoolProperties;

    /**
     * 自定义多个线程池
     */
    @Bean("one")
    public Executor one(){
        return getExecutor(Objects.requireNonNull(getThreadPoolMap("one")));
    }

    @Bean("two")
    public Executor two(){
        return getExecutor(Objects.requireNonNull(getThreadPoolMap("two")));
    }

    private Executor getExecutor(ThreadPoolProperties.ThreadPoolVo threadPoolVo) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutorExt();
        // 核心线程数
        executor.setCorePoolSize(threadPoolVo.getCorePoolSize());
        // 最大线程数
        executor.setMaxPoolSize(threadPoolVo.getMaxPoolSize());
        // 线程池中的线程的名称前缀
        executor.setThreadNamePrefix(threadPoolVo.getThreadNamePrefix());
        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        //执行初始化
        executor.initialize();

        return executor;
    }

    /**
     * 从配置文件中读取线程池配置
     * @return 线程池对象
     */
    private ThreadPoolProperties.ThreadPoolVo getThreadPoolMap(String threadPoolName) {
        List<ThreadPoolProperties.ThreadPoolVo> threadPoolVoList = threadPoolProperties.getPool();
        for (ThreadPoolProperties.ThreadPoolVo threadPoolVo : threadPoolVoList) {
            if (threadPoolName.equals(threadPoolVo.getThreadName())) {
                return threadPoolVo;
            }
        }
        return null;
    }

    /**
     * 自定义线程池
     */
    @Bean
    public ThreadPoolTaskExecutor asyncThreadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(20); // 线程池核心线程的数量，默认值为1（这就是默认情况下的异步线程池配置使得线程不能被重用的原因）。
        executor.setMaxPoolSize(200); // 线程池维护的线程的最大数量，只有当核心线程都被用完并且缓冲队列满后，才会开始申超过请核心线程数的线程，默认值为Integer.MAX_VALUE。
        executor.setQueueCapacity(25); // 缓冲队列。
        executor.setKeepAliveSeconds(200); // 超出核心线程数外的线程在空闲时候的最大存活时间，默认为60秒。
        executor.setThreadNamePrefix("asyncThread"); // 线程名前缀。
        executor.setWaitForTasksToCompleteOnShutdown(true); // 是否等待所有线程执行完毕才关闭线程池，默认值为false。
        executor.setAwaitTerminationSeconds(60); // aitForTasksToCompleteOnShutdown的等待的时长，默认值为0，即不等待。

        // 当没有线程可以被使用时的处理策略（拒绝任务），默认策略为abortPolicy，包含下面四种策略：
        // - CallerRunsPolicy:用于被拒绝任务的处理程序，它直接在 execute 方法的调用线程中运行被拒绝的任务；如果执行程序已关闭，则会丢弃该任务。
        // - AbortPolicy:直接抛出java.util.concurrent.RejectedExecutionException异常。
        // - DiscardOldestPolicy:当线程池中的数量等于最大线程数时、抛弃线程池中最后一个要执行的任务，并执行新传入的任务。
        // - DiscardPolicy:当线程池中的数量等于最大线程数时，不做任何动作。
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }
}


