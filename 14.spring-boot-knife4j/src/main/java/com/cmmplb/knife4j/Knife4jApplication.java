package com.cmmplb.knife4j;

import com.cmmplb.core.utils.SpringApplicationUtil;
import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author penglibo
 * @date 2021-08-03 09:22:34
 * @since jdk 1.8
 */

@EnableKnife4j
@SpringBootApplication
public class Knife4jApplication {

    public static void main(String[] args) {
        SpringApplicationUtil.run(Knife4jApplication.class, args);
    }
}
