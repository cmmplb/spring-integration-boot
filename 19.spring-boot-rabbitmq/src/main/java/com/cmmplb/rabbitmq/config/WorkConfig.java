package com.cmmplb.rabbitmq.config;

import com.cmmplb.rabbitmq.constants.RabbitMqConstants;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author penglibo
 * @date 2021-05-26 16:36:35
 * @since jdk 1.8
 * Work模式-一个生产者对应多个消费者
 */

@Configuration
public class WorkConfig {

    /**
     * 声明work队列
     * @return
     */
    @Bean
    public Queue workQueue() {
        return new Queue(RabbitMqConstants.WORK_QUEUE);
    }
}
