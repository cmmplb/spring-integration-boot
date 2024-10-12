package io.github.cmmplb.velocity;

import io.github.cmmplb.core.utils.SpringApplicationUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author penglibo
 * @date 2021-08-27 17:44:36
 * @since jdk 1.8
 */

@SpringBootApplication
public class VelocityApplication {

    public static void main(String[] args) {
        SpringApplicationUtil.run(VelocityApplication.class, args);
    }
}
