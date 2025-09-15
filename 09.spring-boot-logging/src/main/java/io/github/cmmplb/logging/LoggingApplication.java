package io.github.cmmplb.logging;

import io.github.cmmplb.core.utils.SpringApplicationUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author penglibo
 * @date 2021-08-27 17:44:36
 * @since jdk 1.8
 */

@EnableScheduling
@SpringBootApplication
public class LoggingApplication {

    public static void main(String[] args) {
        SpringApplicationUtil.run(LoggingApplication.class, args);
    }
}
