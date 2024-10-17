package io.github.cmmplb.rabbitmq.listener;

import io.github.cmmplb.rabbitmq.constants.RabbitMqConstant;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author penglibo
 * @date 2021-05-26 16:38:43
 * @since jdk 1.8
 * 监听订阅模型Fanout(广播模式)队列
 */

@Component
public class FanoutListener {

    /**
     * 订阅模型监听
     */
    @RabbitListener(queues = RabbitMqConstant.ONE_FANOUT_QUEUE)
    public void listener(String body, Message message, Channel channel) throws IOException {
        System.out.println("one - 监听到订阅模型Fanout(广播模式)消息:" + body);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    /**
     * 创建的第二个监听, 同样能接收到消息
     */
    @RabbitListener(queues = RabbitMqConstant.TWO_FANOUT_QUEUE)
    public void listenTwo(String body, Message message, Channel channel) throws IOException {
        System.out.println("two - 监听到订阅模型Fanout(广播模式)消息:" + body);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
