package io.github.cmmplb.activemq;

import io.github.cmmplb.core.utils.SpringApplicationUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author penglibo
 */

@SpringBootApplication
public class ActivemqApplication {

    public static void main(String[] args) {
        SpringApplicationUtil.run(ActivemqApplication.class, args);
    }
}