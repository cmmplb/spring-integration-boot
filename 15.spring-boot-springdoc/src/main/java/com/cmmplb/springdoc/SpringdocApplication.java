package com.cmmplb.springdoc;

import io.github.cmmplb.core.utils.SpringApplicationUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author penglibo
 * @date 2021-08-03 09:22:34
 * @since jdk 1.8
 */

@SpringBootApplication
public class SpringdocApplication {

    public static void main(String[] args) {
        SpringApplicationUtil.run(SpringdocApplication.class, args);
    }
}