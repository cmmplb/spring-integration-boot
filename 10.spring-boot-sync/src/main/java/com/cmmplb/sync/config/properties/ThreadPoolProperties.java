package com.cmmplb.sync.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author penglibo
 * @date 2021-09-13 15:35:52
 * @since jdk 1.8
 */

@Data
@ConfigurationProperties(prefix = "thread")
public class ThreadPoolProperties {

    private List<ThreadPoolVo> pool;

    @Data
    public static class ThreadPoolVo {

        /**
         * 核心线程数
         */
        private Integer corePoolSize;

        /**
         * 最大线程数
         */
        private Integer maxPoolSize;

        /**
         * 线程池中的线程的名称前缀
         */
        private String threadNamePrefix;

        /**
         * 线程名称
         */
        private String threadName;
    }
}
