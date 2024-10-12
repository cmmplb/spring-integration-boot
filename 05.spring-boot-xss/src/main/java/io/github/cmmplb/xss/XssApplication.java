package io.github.cmmplb.xss;

import io.github.cmmplb.core.utils.SpringApplicationUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author penglibo
 * @date 2021-08-27 17:44:36
 * @since jdk 1.8
 */

@SpringBootApplication
public class XssApplication {

    public static void main(String[] args) {
        SpringApplicationUtil.run(XssApplication.class, args);
    }
}
