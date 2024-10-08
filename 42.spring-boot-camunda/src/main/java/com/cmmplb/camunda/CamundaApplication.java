package com.cmmplb.camunda;

import io.github.cmmplb.core.utils.SpringApplicationUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author penglibo
 * @createdate 2021-03-20
 */

@SpringBootApplication
public class CamundaApplication {

    public static void main(String[] args) {
        SpringApplicationUtil.run(CamundaApplication.class, args);
    }
}