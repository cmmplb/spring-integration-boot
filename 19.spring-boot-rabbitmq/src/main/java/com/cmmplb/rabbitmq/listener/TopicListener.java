package com.cmmplb.rabbitmq.listener;

import com.cmmplb.rabbitmq.constants.RabbitMqConstants;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;

/**
 * @author penglibo
 * @date 2021-05-26 18:02:52
 * @since jdk 1.8
 * 监听订阅模型-Topic (主题模式)队列
 */

@Configuration
public class TopicListener {

    /**
     * 订阅模型监听
     * @param message
     */
    @RabbitListener(queues = RabbitMqConstants.ONE_TOPIC_QUEUE)
    public void listener(String message) {
        System.out.println("one - 监听到订阅模型-Topic (主题模式)消息:" + message);
    }

    /**
     * 订阅模型监听
     * @param message
     */
    @RabbitListener(queues = RabbitMqConstants.TWO_TOPIC_QUEUE)
    public void listenerTwo(String message) {
        System.out.println("two - 监听到订阅模型-Topic (主题模式)消息:" + message);
    }
}
