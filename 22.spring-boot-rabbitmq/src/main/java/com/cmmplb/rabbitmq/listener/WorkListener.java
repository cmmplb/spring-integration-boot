package com.cmmplb.rabbitmq.listener;

import com.cmmplb.rabbitmq.constants.RabbitMqConstants;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author penglibo
 * @date 2021-05-26 16:38:43
 * @since jdk 1.8
 * 监听Work队列
 */

@Component
public class WorkListener {

    @RabbitListener(queues = RabbitMqConstants.WORK_QUEUE)
    public void listener(String body, Message message, Channel channel) throws IOException {
        System.out.println("one - 监听到Work模式消息:" + body);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    // 创建两个队列共同消费
    @RabbitListener(queues = RabbitMqConstants.WORK_QUEUE)
    public void listenTwo(String body, Message message, Channel channel) throws IOException {
        System.out.println("two - 监听到Work模式消息:" + body);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
