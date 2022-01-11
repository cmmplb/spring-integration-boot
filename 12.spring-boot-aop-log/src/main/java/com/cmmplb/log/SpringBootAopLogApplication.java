package com.cmmplb.log;

import com.cmmplb.core.utils.SpringApplicationUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author penglibo
 * @date 2021-04-14 14:24:38
 */

@SpringBootApplication
public class SpringBootAopLogApplication {

    public static void main(String[] args) {
        SpringApplicationUtil.run(SpringBootAopLogApplication.class, args);
    }
}
