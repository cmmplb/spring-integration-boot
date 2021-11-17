package com.cmmplb.rabbitmq.listener;

import com.cmmplb.rabbitmq.constants.RabbitMqConstants;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author penglibo
 * @date 2021-05-26 17:32:05
 * @since jdk 1.8
 * 监听订阅模型Direct(路由模式)队列
 */

@Component
public class DirectListener {

    @RabbitListener(queues = RabbitMqConstants.ONE_DIRECT_QUEUE)
    public void listener(String message) {
        System.out.println("one - 监听到订阅模型Direct(路由模式)消息:" + message);
    }

    @RabbitListener(queues = RabbitMqConstants.TWO_DIRECT_QUEUE)
    public void listenerTwo(String message) {
        System.out.println("two - 监听到订阅模型Direct(路由模式)消息:" + message);
    }
}
