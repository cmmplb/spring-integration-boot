package io.github.cmmplb.rabbitmq;

import io.github.cmmplb.core.utils.SpringApplicationUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class RabbitmqApplication {

    public static void main(String[] args) {
        SpringApplicationUtil.run(RabbitmqApplication.class, args);
    }


}