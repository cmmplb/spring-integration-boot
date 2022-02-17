package com.cmmplb.kafka;

import com.cmmplb.core.utils.SpringApplicationUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KafkaApplication {

    public static void main(String[] args) {
        SpringApplicationUtil.run(KafkaApplication.class, args);
    }

}