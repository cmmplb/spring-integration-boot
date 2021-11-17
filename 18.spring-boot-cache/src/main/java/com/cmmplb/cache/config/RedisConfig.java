package com.cmmplb.cache.config;

import com.cmmplb.cache.service.IRedisMessage;
import com.cmmplb.cache.service.impl.RedisMessageServiceImpl;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;
import java.time.Duration;

/**
 * @author penglibo
 * @date 2021-09-13 14:42:14
 * @since jdk 1.8
 */

@Configuration
public class RedisConfig extends CachingConfigurerSupport {

    public static final String REDIS_LISTENER_CHANNEL_MESSAGE = "redis.listener.message";

    public static final String REDIS_ADAPTER_CHANNEL_MESSAGE = "redis.adapter.message";

    @Resource
    private RedisConnectionFactory redisConnectionFactory;

    @Autowired
    private RedisMessageServiceImpl redisMessageService;

    /**
     * Redis消息监听器容器
     * @return
     */
    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory redisConnectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        // 配置不同的listener,实现MessageListener重写onMessage方法
        container.addMessageListener(redisMessageService, new PatternTopic(REDIS_LISTENER_CHANNEL_MESSAGE));
        // 也可以使用处理器,通过反射获取调用处理器的方法
        //container.addMessageListener(listenerAdapter(new RedisChannel()), new PatternTopic(REDIS_ADAPTER_CHANNEL_MESSAGE));
        return container;
    }

    /**
     * 配置消息接收处理类
     * @param redisMsg 自定义消息接收类
     * @return
     */
    /*@Scope("prototype")
    public MessageListenerAdapter listenerAdapter(IRedisMessage redisMsg) {
        return new MessageListenerAdapter(redisMsg, IRedisMessage.METHOD_NAME);
    }*/

    // 自定义缓存key生成策略
    @Bean
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            sb.append(method.getName());
            for (Object obj : params) {
                sb.append(obj.toString());
            }
            return sb.toString();
        };
    }

    /**
     * 缓存配置管理器
     */
    @Bean
    @Override
    public CacheManager cacheManager() {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .disableCachingNullValues() // 是否允许缓存空值
                // key序列化
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                // value序列化
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new FastJson2JsonRedisSerializer<>(Object.class)))
                // 缓存前缀
                .computePrefixWith(CacheKeyPrefix.simple())
                // 数据过期时间
                .entryTtl(Duration.ofHours(1)); // 一小时
        // 以锁写入的方式创建RedisCacheWriter对象
        return new RedisCacheManager(RedisCacheWriter.lockingRedisCacheWriter(redisConnectionFactory), redisCacheConfiguration);
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
        StringRedisTemplate template = new StringRedisTemplate(factory);
        setSerializer(template);// 设置序列化工具
        template.afterPropertiesSet();
        return template;
    }

    private void setSerializer(StringRedisTemplate template) {
        @SuppressWarnings({"rawtypes", "unchecked"})
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        template.setValueSerializer(jackson2JsonRedisSerializer);
    }
}
