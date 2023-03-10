package com.cmmplb.rabbitmq.config;

import com.cmmplb.core.constants.StringConstants;
import com.cmmplb.rabbit.mq.configuration.properties.RabbitMqProperties;
import com.cmmplb.rabbitmq.constants.RabbitMqConstants;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author penglibo
 * @date 2021-05-26 16:47:44
 * @since jdk 1.8
 * 订阅模型-Fanout(广播模式)
 * 1.可以有多个消费者
 * 2.每个消费者有自己的队列
 * 3.每个队列都要绑定到Exchange（交换机）
 * 4.生产者发送的消息，只能发送到交换机，交换机来决定要发给哪个队列，生产者无法决定。
 * 5.交换机把消息发送给绑定过的所有队列
 * 6.队列的消费者都能拿到消息。实现一条消息被多个消费者消费
 * -- 两个队列--两个队列绑定交换机--一个交换机--
 */

@Configuration
// 关闭配置文件配置bean时才开启类的注入
@ConditionalOnProperty(prefix = RabbitMqProperties.PREFIX, name = RabbitMqProperties.ENABLED, havingValue = StringConstants.FALSE)
public class FanoutConfig {

    /**
     * 声明订阅模型-Fanout队列-one
     * @return
     */
    @Bean
    public Queue oneFanoutQueue() {
        return new Queue(RabbitMqConstants.ONE_FANOUT_QUEUE);
    }

    /**
     * 声明订阅模型-Fanout队列-two
     * @return
     */
    @Bean
    public Queue twoFanoutQueue() {
        return new Queue(RabbitMqConstants.TWO_FANOUT_QUEUE);
    }

    /**
     * 声明订阅模型-Fanout交换机
     * @return
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(RabbitMqConstants.FANOUT_EXCHANGE);
    }

    /**
     * 订阅模型-Fanout队列-one绑定-Fanout交换机
     * @return
     */
    @Bean
    public Binding bindingFanoutOne() {
        return BindingBuilder.bind(oneFanoutQueue()).to(fanoutExchange());
    }

    /**
     * 订阅模型-Fanout队列-two绑定-Fanout交换机
     * @return
     */
    @Bean
    public Binding bindingFanoutTwo() {
        return BindingBuilder.bind(twoFanoutQueue()).to(fanoutExchange());
    }

}
