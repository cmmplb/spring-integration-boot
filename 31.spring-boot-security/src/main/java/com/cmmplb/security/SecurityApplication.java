package com.cmmplb.security;

import com.cmmplb.core.utils.SpringApplicationUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author penglibo
 * @date 2021-04-01 21:07:28
 */

@SpringBootApplication
public class SecurityApplication {

    public static void main(String[] args) {
        SpringApplicationUtil.run(SecurityApplication.class, args);
    }
}
