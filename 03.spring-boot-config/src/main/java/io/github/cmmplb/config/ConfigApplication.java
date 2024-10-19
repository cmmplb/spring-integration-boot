package io.github.cmmplb.config;

import io.github.cmmplb.core.utils.SpringApplicationUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author penglibo
 * @date 2021-03-29 14:26:40
 */

@SpringBootApplication
public class ConfigApplication {

    public static void main(String[] args) {
        SpringApplicationUtil.run(ConfigApplication.class, args);
    }

}