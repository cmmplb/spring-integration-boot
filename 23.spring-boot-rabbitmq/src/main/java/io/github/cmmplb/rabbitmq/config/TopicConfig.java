package io.github.cmmplb.rabbitmq.config;

import io.github.cmmplb.rabbitmq.constants.RabbitMqConstant;
import io.github.cmmplb.core.constants.StringConstant;
import io.github.cmmplb.rabbit.configuration.properties.RabbitMqProperties;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author penglibo
 * @date 2021-05-26 17:47:07
 * @since jdk 1.8
 * 订阅模型-Topic (主题模式)
 * topic类型的Exchange与Direct相比, 都是可以根据RoutingKey把消息路由到不同的队列. 只不过Topic类型Exchange可以让队列在绑定Routing key 的时候使用通配符！
 * RoutingKey 一般都是有一个或多个单词组成, 多个单词之间以”.”分割, 例如： user.insert
 * 通配符规则	例:
 * #：匹配一个或多个词	person.#：能够匹配person.insert.save 或者 person.insert
 * *：匹配不多不少恰好1个词	person.*：只能匹配person.insert
 * -- 两个队列--三个队列绑定交换机--一个交换机--
 */

@Configuration
// 关闭配置文件配置bean时才开启类的注入
@ConditionalOnProperty(prefix = RabbitMqProperties.PREFIX, name = RabbitMqProperties.ENABLED, havingValue = StringConstant.FALSE)
public class TopicConfig {

    /**
     * 声明订阅模型-Topic队列-one
     * @return
     */
    @Bean
    public Queue oneTopicQueue() {
        return new Queue(RabbitMqConstant.ONE_TOPIC_QUEUE);
    }

    /**
     * 声明订阅模型-Topic队列-two
     * @return
     */
    @Bean
    public Queue TwoTopicQueue() {
        return new Queue(RabbitMqConstant.TWO_TOPIC_QUEUE);
    }

    /**
     * 声明订阅模型-Topic交换机
     * @return
     */
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(RabbitMqConstant.TOPIC_EXCHANGE);
    }

    /**
     * 订阅模型-Topic队列-one绑定-Topic交换机-指定通配符路由routingKey
     * @return
     */
    @Bean
    public Binding bindingTopicOne() {
        return BindingBuilder.bind(oneTopicQueue()).to(topicExchange()).with(RabbitMqConstant.ONE_TOPIC_ROUTING_KEY);
    }

    /**
     * 订阅模型-Topic队列-two绑定-Topic交换机-指定通配符路由routingKey
     * @return
     */
    @Bean
    public Binding bindingTopicTwo() {
        return BindingBuilder.bind(TwoTopicQueue()).to(topicExchange()).with(RabbitMqConstant.TWO_TOPIC_ROUTING_KEY);
    }

    /**
     * 订阅模型-Topic队列-three绑定-Topic交换机-指定通配符路由routingKey
     * @return
     */
    @Bean
    public Binding bindingTopicThree() {
        return BindingBuilder.bind(TwoTopicQueue()).to(topicExchange()).with(RabbitMqConstant.THREE_TOPIC_ROUTING_KEY);//#表示0个或若干个关键字, *表示一个关键字
    }
}
