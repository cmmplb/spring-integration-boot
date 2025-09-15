package io.github.cmmplb.rocketmq;

import io.github.cmmplb.core.utils.SpringApplicationUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RocketApplication {

    public static void main(String[] args) {
        SpringApplicationUtil.run(RocketApplication.class, args);
    }

}