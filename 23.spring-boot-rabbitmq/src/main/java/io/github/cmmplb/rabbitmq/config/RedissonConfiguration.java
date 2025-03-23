package io.github.cmmplb.rabbitmq.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author penglibo
 * @date 2025-03-23 14:27:15
 * @since jdk 1.8
 */

@Configuration
public class RedissonConfiguration {

    @Bean
    public RedissonClient redissonClient() {
        // 配置单节点 Redis
        Config config = new Config();
        SingleServerConfig serverConfig = config.useSingleServer();
        serverConfig.setAddress("redis://127.0.0.1:6379");
        serverConfig.setPassword("cmmplb");
        return Redisson.create(config);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // 设置默认的序列化方式为StringRedisSerializer
        redisTemplate.setDefaultSerializer(stringRedisSerializer);
        // 使用StringRedisSerializer来序列化和反序列化redis的key值
        redisTemplate.setKeySerializer(stringRedisSerializer);
        // Hash的key也采用StringRedisSerializer的序列化方式
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        // 设置消息确认回调
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                System.out.println("消息成功到达交换机，correlationData: " + correlationData);
            } else {
                throw new RuntimeException("消息未能到达交换机，原因: " + cause + ", correlationData: " + correlationData);
            }
        });

        // 设置消息返回回调
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            throw new RuntimeException("消息未能路由到队列，消息: " + new String(message.getBody()) +
                    ", 回复码: " + replyCode +
                    ", 回复文本: " + replyText +
                    ", 交换机: " + exchange +
                    ", 路由键: " + routingKey);
        });

        // 开启强制模式，确保 ReturnCallback 生效
        rabbitTemplate.setMandatory(true);

        return rabbitTemplate;
    }
}
