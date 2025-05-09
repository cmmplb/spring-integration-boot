package io.github.cmmplb.cache.config;

import io.github.cmmplb.cache.config.properties.CacheProperties;
import io.github.cmmplb.core.constants.StringConstant;
import io.github.cmmplb.core.utils.SpringUtil;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import org.cache2k.Cache2kBuilder;
import org.cache2k.extra.spring.SpringCache2kCacheManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKey;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.lang.NonNull;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author penglibo
 * @date 2021-09-13 14:06:21
 * @since jdk 1.8
 */

@Configuration
@EnableConfigurationProperties(CacheProperties.class)
public class CacheConfiguration {

    /**
     * 缓存管理器
     */
    @Bean
    @ConditionalOnProperty(prefix = CacheProperties.PREFIX, name = CacheProperties.ENABLED, havingValue = StringConstant.TRUE)
    public CacheManager cacheManager(CacheProperties cacheProperties) {
        CacheManager cacheManager = null;
        if (cacheProperties.getCacheType().equals(CacheProperties.CacheType.CACHE2K)) {
            cacheManager = new SpringCache2kCacheManager();
        }
        if (cacheProperties.getCacheType().equals(CacheProperties.CacheType.EHCACHE)) {
            cacheManager = new EhCacheCacheManager();
        }
        // GuavaCacheManager被移除, 不再自动配置Guava缓存, 采用CaffeineCacheManager替换, 使用Caffiene替换Guava缓存. 
        if (cacheProperties.getCacheType().equals(CacheProperties.CacheType.CAFFEINE)) {
            cacheManager = new CaffeineCacheManager();
        }
        if (cacheProperties.getCacheType().equals(CacheProperties.CacheType.REDIS)) {
            // 使用redis分布式缓存作为管理器
            cacheManager = new RedisCacheManager(
                    RedisCacheWriter.nonLockingRedisCacheWriter(SpringUtil.getBean(RedisConnectionFactory.class)),
                    // 默认策略, 未配置的key会使用这个
                    this.getRedisCacheConfigurationWithTtl(60),
                    // 指定 key 策略
                    this.getRedisCacheConfigurationMap()
            );
        }
        return cacheManager;
    }

    /**
     * Cache2k实例
     */
    @Bean
    public org.cache2k.Cache<String, String> cache2k() {
        return Cache2kBuilder.of(String.class, String.class)
                // 过期时间
                .expireAfterWrite(1, TimeUnit.MINUTES)
                .build();
    }

    /**
     * Guava实例
     */
    @Bean
    public com.google.common.cache.Cache<String, String> guavaCache() {
        return CacheBuilder.newBuilder()
                // 过期时间
                .expireAfterWrite(10, TimeUnit.MINUTES)
                // 设置缓存最大容量
                .maximumSize(100)
                .build(new CacheLoader<String, String>() {
                    // 自定义缓存加载方式, 也可以为空
                    @Override
                    public String load(@NonNull String key) {
                        // 这里可以实现自定义的数据加载逻辑
                        return "value_for_" + key;
                    }
                });
    }

    /**
     * Caffeine实例
     */
    @Bean
    public com.github.benmanes.caffeine.cache.Cache<String, String> caffeineCache() {
        return Caffeine.newBuilder()
                // 设置最后一次写入或访问后经过固定时间过期
                .expireAfterWrite(60, TimeUnit.SECONDS)
                // 初始的缓存空间大小
                .initialCapacity(100)
                // 缓存的最大条数
                .maximumSize(1000)
                .build();
    }

    /**
     * 自定义KeyGenerator
     * {@link SimpleKeyGenerator}
     */
    @Bean
    public KeyGenerator customKeyGenerator() {
        return (target, method, params) -> {
            if (params.length == 0) {
                return SimpleKey.EMPTY;
            } else {
                if (params.length == 1) {
                    Object param = params[0];
                    if (param != null && !param.getClass().isArray()) {
                        return param;
                    }
                }
                return new SimpleKey(params);
            }
        };
    }

    private Map<String, RedisCacheConfiguration> getRedisCacheConfigurationMap() {
        Map<String, RedisCacheConfiguration> redisCacheConfigurationMap = new HashMap<>();
        redisCacheConfigurationMap.put("getById", this.getRedisCacheConfigurationWithTtl(30));
        redisCacheConfigurationMap.put("getList", this.getRedisCacheConfigurationWithTtl(60));
        return redisCacheConfigurationMap;
    }

    private RedisCacheConfiguration getRedisCacheConfigurationWithTtl(Integer seconds) {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL,
                JsonTypeInfo.As.PROPERTY);
        jackson2JsonRedisSerializer.setObjectMapper(om);

        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        redisCacheConfiguration = redisCacheConfiguration.serializeValuesWith(RedisSerializationContext
                        .SerializationPair.fromSerializer(jackson2JsonRedisSerializer))
                .entryTtl(Duration.ofSeconds(seconds));
        return redisCacheConfiguration;
    }
}
