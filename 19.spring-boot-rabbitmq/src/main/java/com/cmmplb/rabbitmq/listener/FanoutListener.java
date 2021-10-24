package com.cmmplb.rabbitmq.listener;

import com.cmmplb.rabbitmq.constants.RabbitMqConstants;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

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
     * @param message
     */
    @RabbitListener(queues = RabbitMqConstants.ONE_FANOUT_QUEUE)
    public void listener(String message) {
        System.out.println("one - 监听到订阅模型Fanout(广播模式)消息:" + message);
    }

    /**
     * 创建的第二个监听，同样能接收到消息
     * @param message
     */
    @RabbitListener(queues = RabbitMqConstants.TWO_FANOUT_QUEUE)
    public void listenTwo(String message) {
        System.out.println("two - 监听到订阅模型Fanout(广播模式)消息:" + message);
    }
}
