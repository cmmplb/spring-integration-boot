package com.cmmplb.rabbitmq.config;

import com.cmmplb.rabbitmq.constants.RabbitMqConstants;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author penglibo
 * @date 2021-05-26 15:43:21
 * @since jdk 1.8
 * 简单队列模式配置-一个生产者对应一个消费者
 */

@Configuration
public class SimpleQueueConfig {

    /**
     * 声明简单队列
     * @return
     */
    @Bean
    public Queue simpleQueue() {
        return new Queue(RabbitMqConstants.SIMPLE_QUEUE);
    }
}
