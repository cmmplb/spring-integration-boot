package com.cmmplb.rabbitmq.service.impl;

import com.cmmplb.rabbitmq.constants.RabbitMqConstants;
import com.cmmplb.rabbitmq.service.ProducerService;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author penglibo
 * @date 2021-05-26 15:41:33
 * @since jdk 1.8
 */

@Service
public class ProducerServiceImpl implements ProducerService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void send2SimpleQueue(String message) {
        rabbitTemplate.convertAndSend(RabbitMqConstants.SIMPLE_QUEUE, message);
    }

    @Override
    public void send2WorkQueue(String message) {
        rabbitTemplate.convertAndSend(RabbitMqConstants.WORK_QUEUE, message);
    }

    @Override
    public void send2FanoutQueue(String message) {
        // 指定交换机-将消息发送到交换机，由交换机来发消息给消费者
        rabbitTemplate.convertAndSend(RabbitMqConstants.FANOUT_EXCHANGE, "", message); // 需要设置第二个参数,否则第一个交换机的参数就变成routingKey了。
    }

    @Override
    public void send2DirectQueue(String routingKey, String message) {
        rabbitTemplate.convertAndSend(RabbitMqConstants.DIRECT_EXCHANGE, routingKey, message);
    }

    @Override
    public void send2TopicQueue(String routingKey, String message) {
        rabbitTemplate.convertAndSend(RabbitMqConstants.TOPIC_EXCHANGE, routingKey, message);
    }

    @Override
    public void send2DeadQueue(String routingKey, String message) {
        // 两种方式：
        // 1、可以发送到有消费者的队列，拒绝消费
        // rabbitTemplate.convertAndSend(GlobalConstant.COMMON_EXCHANGE, routingKey, message);
        // 2、发送到没有消费者的队列，设置队列过期时间-需要注释掉消费者-com.cmmplb.rabbitmq.listener.DeadListener.commonListener
        rabbitTemplate.convertAndSend(RabbitMqConstants.COMMON_QUEUE, message, m -> {
            MessageProperties messageProperties = m.getMessageProperties();
            //为每条消息设定过期时间
            messageProperties.setExpiration(RabbitMqConstants.MESSAGE_TTL_TIME + "");
            return m;
        });
    }
}
