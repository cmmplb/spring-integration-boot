package com.cmmplb.rabbitmq.listener;

import com.cmmplb.rabbitmq.constants.RabbitMqConstants;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author penglibo
 * @date 2021-05-19 16:06:59
 * @since jdk 1.8
 * 监听简单队列
 */

@Slf4j
@Component
public class SimpleListener {

    @RabbitListener(queues = RabbitMqConstants.SIMPLE_QUEUE)
    public void listener(String body, Message message, Channel channel) throws IOException {
        System.out.println("监听到简单队列消息:" + body);
        log.info("告诉broker, 消息已经被确认");
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
