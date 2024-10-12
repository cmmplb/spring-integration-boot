package com.cmmplb.rabbitmq.listener;

import com.cmmplb.rabbitmq.constants.RabbitMqConstants;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @author penglibo
 * @date 2021-05-26 18:02:52
 * @since jdk 1.8
 * 监听订阅模型-Topic (主题模式)队列
 */

@Slf4j
@Configuration
public class TopicListener {

    /**
     * 订阅模型监听
     */
    @RabbitListener(queues = RabbitMqConstants.ONE_TOPIC_QUEUE)
    public void listener(String body, Message message, Channel channel) throws IOException {
        System.out.println("one - 监听到订阅模型-Topic (主题模式)消息:" + message);
        log.info("告诉broker, 消息已经被确认");
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    /**
     * 订阅模型监听
     */
    @RabbitListener(queues = RabbitMqConstants.TWO_TOPIC_QUEUE)
    public void listenerTwo(String body, Message message, Channel channel) throws IOException {
        System.out.println("two - 监听到订阅模型-Topic (主题模式)消息:" + body);
        log.info("告诉broker, 消息已经被确认");
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
