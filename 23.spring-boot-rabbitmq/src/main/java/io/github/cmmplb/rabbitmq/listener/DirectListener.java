package io.github.cmmplb.rabbitmq.listener;

import io.github.cmmplb.rabbitmq.constants.RabbitMqConstant;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author penglibo
 * @date 2021-05-26 17:32:05
 * @since jdk 1.8
 * 监听订阅模型Direct(路由模式)队列
 */

@Component
public class DirectListener {

    @RabbitListener(queues = RabbitMqConstant.ONE_DIRECT_QUEUE)
    public void listener(String body, Message message, Channel channel) throws IOException {
        System.out.println("one - 监听到订阅模型Direct(路由模式)消息:" + body);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    @RabbitListener(queues = RabbitMqConstant.TWO_DIRECT_QUEUE)
    public void listenerTwo(String body, Message message, Channel channel) throws IOException {
        System.out.println("two - 监听到订阅模型Direct(路由模式)消息:" + body);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
