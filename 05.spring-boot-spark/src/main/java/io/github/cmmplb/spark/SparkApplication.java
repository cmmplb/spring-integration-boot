package io.github.cmmplb.spark;

import io.github.cmmplb.core.utils.SpringApplicationUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author penglibo
 * @date 2021-03-20
 */

@EnableAsync
@SpringBootApplication
public class SparkApplication {

    public static void main(String[] args) {
        SpringApplicationUtil.run(SparkApplication.class, args);
    }
}
