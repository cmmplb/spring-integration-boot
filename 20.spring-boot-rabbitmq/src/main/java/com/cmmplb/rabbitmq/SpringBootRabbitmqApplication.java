package com.cmmplb.rabbitmq;

import com.cmmplb.core.utils.SpringApplicationUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootRabbitmqApplication {

    public static void main(String[] args) {
        SpringApplicationUtil.run(SpringBootRabbitmqApplication.class, args);
    }


}