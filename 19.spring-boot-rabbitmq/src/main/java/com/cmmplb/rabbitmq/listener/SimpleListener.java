package com.cmmplb.rabbitmq.listener;

import com.cmmplb.rabbitmq.constants.RabbitMqConstants;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author penglibo
 * @date 2021-05-19 16:06:59
 * @since jdk 1.8
 * 监听简单队列
 */

@Component
public class SimpleListener {

    @RabbitListener(queues = RabbitMqConstants.SIMPLE_QUEUE)
    public void listener(String body){
        System.out.println("监听到简单队列消息:" + body);
    }
}
