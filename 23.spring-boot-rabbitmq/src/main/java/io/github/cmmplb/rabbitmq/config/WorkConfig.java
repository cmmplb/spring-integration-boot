package io.github.cmmplb.rabbitmq.config;

import io.github.cmmplb.rabbitmq.constants.RabbitMqConstants;
import io.github.cmmplb.core.constants.StringConstant;
import io.github.cmmplb.rabbit.configuration.properties.RabbitMqProperties;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author penglibo
 * @date 2021-05-26 16:36:35
 * @since jdk 1.8
 * Work模式-一个生产者对应多个消费者
 */

@Configuration
// 关闭配置文件配置bean时才开启类的注入
@ConditionalOnProperty(prefix = RabbitMqProperties.PREFIX, name = RabbitMqProperties.ENABLED, havingValue = StringConstant.FALSE)
public class WorkConfig {

    /**
     * 声明work队列
     * @return
     */
    @Bean
    public Queue workQueue() {
        return new Queue(RabbitMqConstants.WORK_QUEUE);
    }
}
