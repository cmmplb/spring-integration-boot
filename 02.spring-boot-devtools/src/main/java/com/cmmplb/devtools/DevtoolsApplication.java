package com.cmmplb.devtools;

import io.github.cmmplb.core.utils.SpringApplicationUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author penglibo
 * @date 2021-03-29 14:26:40
 */

@SpringBootApplication
public class DevtoolsApplication {

    public static void main(String[] args) {
        System.setProperty("spring.devtools.restart.enabled","false");
        SpringApplicationUtil.run(DevtoolsApplication.class, args);
    }
}