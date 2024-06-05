package com.cmmplb.activemq;

import com.cmmplb.core.utils.SpringApplicationUtil;
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