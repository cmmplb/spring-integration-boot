package io.github.cmmplb.start;

import io.github.cmmplb.core.utils.SpringApplicationUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author penglibo
 * @date 2021-03-20
 */

@SpringBootApplication
public class StartApplication {

    public static void main(String[] args) {
        SpringApplicationUtil.run(StartApplication.class, args);
    }
}
