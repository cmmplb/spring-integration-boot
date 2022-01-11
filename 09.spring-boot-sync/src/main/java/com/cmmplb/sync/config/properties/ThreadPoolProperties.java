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
        private Integer corePoolSize;
        private Integer maxPoolSize;
        private String threadNamePrefix;
        private String threadName;
    }
}
