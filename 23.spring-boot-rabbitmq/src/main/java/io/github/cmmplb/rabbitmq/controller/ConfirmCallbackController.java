package io.github.cmmplb.rabbitmq.controller;

import com.alibaba.fastjson.JSON;
import io.github.cmmplb.core.utils.UUIDUtil;
import io.github.cmmplb.rabbitmq.constants.RabbitMqConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @author penglibo
 * @date 2025-03-22 15:39:05
 * @since jdk 1.8
 */

@Slf4j
@RestController
@RequestMapping("/confirm/callback")
public class ConfirmCallbackController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private MessageConverter messageConverter;

    @Bean
    public MessageConverter messageConverter() {
        return new SimpleMessageConverter();
    }

    @GetMapping("/confirm")
    public void send2SimpleQueue() {
        MessageProperties properties = new MessageProperties();
        // 设置消息投递模式为持久化
        properties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
        HashMap<Object, Object> map = new HashMap<>();
        map.put("text", "简单消息");
        map.put("id", UUIDUtil.uuidTrim());

        Message amqpMessage = messageConverter.toMessage(JSON.toJSONString(map), properties);
        rabbitTemplate.send(RabbitMqConstants.SIMPLE_QUEUE, amqpMessage);

        // 发送简单消息
        // rabbitTemplate.convertAndSend(RabbitMqConstants.SIMPLE_QUEUE, "简单消息");
        log.info("生产消息：确认");
        // 开启发送确认
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                log.info("消息发送到交换机成功，消息 ID: " + (correlationData != null ? correlationData.getId() : null));
            } else {
                log.info("消息发送到交换机失败，原因: " + cause);
                // 重试补偿，重试三次之后还是失败，则记录到数据库表，进行定时任务补偿
            }
        });
        // 开启返回回调
        rabbitTemplate.setReturnsCallback(returned -> {
            log.info("消息从交换机路由到队列失败，" +
                    "交换机: " + returned.getExchange() +
                    "，队列: " + returned.getRoutingKey() +
                    "，回复码: " + returned.getReplyCode() +
                    "，回复文本: " + returned.getReplyText());
        });
    }

}
