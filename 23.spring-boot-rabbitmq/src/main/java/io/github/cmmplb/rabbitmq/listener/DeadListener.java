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
 * @date 2021-06-19 15:48:51
 * @since jdk 1.8
 * 监听死信队列
 */

@Slf4j
@Component
public class DeadListener {

    private static int count = 0;

    /**
     * 死信队列
     * @param body
     * @param message
     * @param channel
     * @throws IOException
     */
    @RabbitListener(queues = RabbitMqConstants.DEAD_QUEUE)
    public void deadListener(String body, Message message, Channel channel) throws IOException {
        long msgTag = message.getMessageProperties().getDeliveryTag();
        count++;
        log.info("count:{}", count);
        log.info("监听到死信队列消息,body:{},msgTag:{},receivedRoutingKey:{}", body, msgTag, message.getMessageProperties().getReceivedRoutingKey());
        if (count == 1) {
            log.info("收到消息需要重新发送");
            // 第一个参数：该消息的index
            // 第二个参数：是否批量.true:将一次性拒绝所有小于deliveryTag的消息. 
            // 第三个参数：为true重新排队, 为false表示不会重回队列, 死信. 
            channel.basicNack(msgTag, false, true);
        } else if (count == 2) {
            log.info("告诉broker, 消息拒绝确认, 拒绝消费消息（丢失消息） 给死信队列,第三个参数 false 表示不会重回队列");
            channel.basicNack(msgTag, false, false);
        } else {
            log.info("告诉broker, 消息已经被确认");
            channel.basicAck(msgTag, false);
        }
    }

    /**
     * 普通队列
     * @param body
     * @param message
     * @param channel
     * @throws IOException
     */
    /*@RabbitListener(queues = GlobalConstant.COMMON_QUEUE) // 不设置消费者, 让其超过过期时间进入死信队列
    public void commonListener(String body, Message message, Channel channel) throws IOException {
        long msgTag = message.getMessageProperties().getDeliveryTag();
        count++;
        log.info("count:{}", count);
        log.info("监听到普通队列消息,body:{},msgTag:{},receivedRoutingKey:{}", body, msgTag, message.getMessageProperties().getReceivedRoutingKey());
        if (count == 1) {
            log.info("收到消息需要重新发送");
            channel.basicNack(msgTag, false, true);
        } else if (count == 2) {
            log.info("告诉broker, 消息拒绝确认, 拒绝消费消息（丢失消息） 给死信队列,第三个参数 false 表示不会重回队列");
            channel.basicNack(msgTag, false, false);
        } else {
            log.info("告诉broker, 消息已经被确认");
            channel.basicAck(msgTag, false);
        }
    }*/

}
