package io.github.cmmplb.rabbitmq.listener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.rabbitmq.client.Channel;
import io.github.cmmplb.rabbitmq.constants.RabbitMqConstant;
import io.github.cmmplb.rabbitmq.entity.DeadMessage;
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

    // 模拟数据库操作字段
    private static int count = 0;

    /**
     * 死信队列
     * 消息有哪几种情况成为死信
     * 消费者拒收消息 （basic.reject/ basic.nack） ，并且没有重新入队 requeue=false
     * 消息在队列中未被消费，且超过队列或者消息本身的过期时间TTL(time-to-live)
     * 队列的消息长度达到极限
     * 结果：消息成为死信后，如果该队列绑定了死信交换机，则消息会被死信交换机重新路由到死信队列
     * 死信队列经常用来做延迟队列消费。
     * @param body
     * @param message
     * @param channel
     * @throws IOException
     */
    @RabbitListener(queues = RabbitMqConstant.DEAD_QUEUE)
    public void deadListener(String body, Message message, Channel channel) throws Exception {
        long msgTag = message.getMessageProperties().getDeliveryTag();
        count++;
        log.info("count:{}", count);
        DeadMessage deadMessage = JSON.parseObject(body, new TypeReference<DeadMessage>() {
        });
        if (deadMessage.getType() == 1) {
            log.info("延时消息类型:1-有消费者，拒绝消费");
        } else {
            log.info("延时消息类型:2-无消费者，消息过期");
        }
        log.info("监听到死信队列消息,body:{},msgTag:{},receivedRoutingKey:{}", body, msgTag, message.getMessageProperties().getReceivedRoutingKey());
        if (count == 1) {
            log.info("收到消息需要重新发送");
            // 第一个参数：该消息的index
            // 第二个参数：是否批量.true:将一次性拒绝所有小于deliveryTag的消息.
            // 第三个参数：为true重新排队, 为false表示不会重回队列, 死信.
            Thread.sleep(3000);
            channel.basicNack(msgTag, false, true);
        } else if (count == 2) {
            log.info("告诉broker, 消息拒绝确认, 拒绝消费消息（丢失消息） 给死信队列,第三个参数 false 表示不会重回队列");
            Thread.sleep(3000);
            channel.basicNack(msgTag, false, false);
        } else {
            log.info("告诉broker, 消息已经被确认");
            count = 0;
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
    /*@RabbitListener(queues = GlobalConstants.COMMON_QUEUE) // 不设置消费者, 让其超过过期时间进入死信队列
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
