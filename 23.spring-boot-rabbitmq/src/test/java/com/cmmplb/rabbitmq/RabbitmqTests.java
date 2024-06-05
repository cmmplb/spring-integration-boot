package com.cmmplb.rabbitmq;

import com.cmmplb.rabbitmq.service.ProducerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class RabbitmqTests {

    @Autowired
    private ProducerService producerService;

    /**
     * 简单队列模式-一个生产者对应一个消费者
     */
    @Test
    public void simpleQueue() {
        String message = "simple queue message";
        producerService.send2SimpleQueue(message);
    }

}