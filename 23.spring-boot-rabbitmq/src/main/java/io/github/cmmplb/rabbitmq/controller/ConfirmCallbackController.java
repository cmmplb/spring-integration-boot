package io.github.cmmplb.rabbitmq.controller;

import io.github.cmmplb.rabbitmq.constants.RabbitMqConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/confirm")
    public void send2SimpleQueue() {
        // 发送简单消息
        rabbitTemplate.convertAndSend(RabbitMqConstant.SIMPLE_QUEUE, "简单消息");
        log.info("生产消息：确认");
        // 开启发送确认
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                log.info("消息发送到交换机成功，消息 ID: " + (correlationData != null ? correlationData.getId() : null));
            } else {
                log.info("消息发送到交换机失败，原因: " + cause);
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
