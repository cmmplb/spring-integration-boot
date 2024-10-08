package com.cmmplb.rabbitmq.config;

import com.cmmplb.rabbitmq.constants.RabbitMqConstants;
import io.github.cmmplb.core.constants.StringConstant;
import io.github.cmmplb.rabbit.configuration.properties.RabbitMqProperties;
import org.springframework.amqp.core.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author penglibo
 * @date 2021-06-19 15:24:19
 * @since jdk 1.8
 * 死信队列模式配置
 * 将消息拒绝消费消息(丢失消息)给死信队列或者发送消息到一个没有消费者的普通队列中，过期时间达到后死信队列会接收到消息
 */

@Configuration
// 关闭配置文件配置bean时才开启类的注入
@ConditionalOnProperty(prefix = RabbitMqProperties.PREFIX, name = RabbitMqProperties.ENABLED, havingValue = StringConstant.FALSE)
public class DeadConfig {

    /**
     * 创建普通队列
     * @return
     */
    @Bean
    public Queue commonQueue() {
        Map<String, Object> args = new HashMap<>(3);
        //消息过期后，进入到死信交换机
        args.put("x-dead-letter-exchange", RabbitMqConstants.DEAD_EXCHANGE);
        //消息过期后，进入到死信交换机的路由key
        args.put("x-dead-letter-routing-key", RabbitMqConstants.DEAD_ROUTING_KEY);
        //过期时间，单位毫秒
        args.put("x-message-ttl", RabbitMqConstants.MESSAGE_TTL_TIME);
        return new Queue(RabbitMqConstants.COMMON_QUEUE, true, false, false, args);
        //return QueueBuilder.durable(GlobalConstant.COMMON_QUEUE).withArguments(args).build();
    }

    /**
     * 声明死信队列
     * @return
     */
    @Bean
    public Queue deadQueue() {
        return QueueBuilder.durable(RabbitMqConstants.DEAD_QUEUE).build();
    }

    /**
     * 声明普通交换机
     * @return
     */
    @Bean
    public Exchange commonExchange() {
        return new TopicExchange(RabbitMqConstants.COMMON_EXCHANGE, true, false);
    }

    /**
     * 声明死信交换机
     * @return
     */
    @Bean
    public Exchange deadExchange() {
        return new TopicExchange(RabbitMqConstants.DEAD_EXCHANGE, true, false);
    }

    /**
     * 绑定普通交换机和普通队列
     * @return
     */
    @Bean
    public Binding bindingCommon() {
        return new Binding(RabbitMqConstants.COMMON_QUEUE, Binding.DestinationType.QUEUE, RabbitMqConstants.COMMON_EXCHANGE, RabbitMqConstants.COMMON_ROUTING_KEY, Collections.<String, Object>emptyMap());
        //return BindingBuilder.bind(commonQueue()).to(commonExchange()).with(GlobalConstant.COMMON_ROUTING_KEY).noargs();
    }

    /**
     * 绑定死信交换机和死信队列
     * @return
     */
    @Bean
    public Binding bindingDead() {
        return new Binding(RabbitMqConstants.DEAD_QUEUE, Binding.DestinationType.QUEUE, RabbitMqConstants.DEAD_EXCHANGE, RabbitMqConstants.DEAD_ROUTING_KEY, null);
    }
}
