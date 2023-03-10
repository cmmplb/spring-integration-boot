package com.cmmplb.rabbitmq.config;

import com.cmmplb.core.constants.StringConstants;
import com.cmmplb.rabbit.mq.configuration.properties.RabbitMqProperties;
import com.cmmplb.rabbitmq.constants.RabbitMqConstants;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author penglibo
 * @date 2021-05-26 17:14:31
 * @since jdk 1.8
 * 订阅模型-Direct(路由模式)
 * 在Fanout模式中，一条消息，会被所有订阅的队列都消费。但是，在某些场景下，我们希望不同的消息被不同的队列消费。这时就要用到Direct类型的Exchange。给特定的消费者消费
 * 在Direct模型下：
 * 1.队列与交换机的绑定，不能是任意绑定了，而是要指定一个RoutingKey（路由key）
 * 2.消息的发送方在 向 Exchange发送消息时，也必须指定消息的 RoutingKey。
 * 3.Exchange不再把消息交给每一个绑定的队列，而是根据消息的Routing Key进行判断，只有队列的 RoutingKey与消息的 Routing key完全一致，才会接收到消息
 * --两个队列--三个队列绑定交换机--一个交换机--
 */

@Configuration
// 关闭配置文件配置bean时才开启类的注入
@ConditionalOnProperty(prefix = RabbitMqProperties.PREFIX, name = RabbitMqProperties.ENABLED, havingValue = StringConstants.FALSE)
public class DirectConfig {

    /**
     * 声明订阅模型-Direct队列-one
     * @return
     */
    @Bean
    public Queue oneDirectQueue() {
        return new Queue(RabbitMqConstants.ONE_DIRECT_QUEUE);
    }

    /**
     * 声明订阅模型-Direct队列-two
     * @return
     */
    @Bean
    public Queue twoDirectQueue() {
        return new Queue(RabbitMqConstants.TWO_DIRECT_QUEUE);
    }

    /**
     * 声明订阅模型-Direct交换机
     * @return
     */
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(RabbitMqConstants.DIRECT_EXCHANGE);
    }

    /**
     * 订阅模型-Direct队列-one绑定-Direct交换机-声明routingKey
     * @return
     */
    @Bean
    public Binding bindingDirectOne(@Qualifier("topicExchange") Exchange topicExchange) {
        return BindingBuilder.bind(oneDirectQueue()).to(directExchange()).with(RabbitMqConstants.ONE_DIRECT_ROUTING_KEY);
    }

    /**
     * 订阅模型-Direct队列-two绑定-Direct交换机-声明routingKey
     * @return
     */
    @Bean
    public Binding bindingDirectTwo() {
        return BindingBuilder.bind(twoDirectQueue()).to(directExchange()).with(RabbitMqConstants.TWO_DIRECT_ROUTING_KEY);
    }

    /**
     * 订阅模型-Direct队列-two绑定-Direct交换机-声明routingKey
     * @return
     */
    @Bean
    public Binding bindingDirectThree() {
        return BindingBuilder.bind(twoDirectQueue()).to(directExchange()).with(RabbitMqConstants.THREE_DIRECT_ROUTING_KEY);
    }
}
