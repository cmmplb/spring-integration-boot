package com.cmmplb.cache.config;

import com.cmmplb.cache.config.properties.CacheProperties;
import com.cmmplb.cache.domain.entity.User;
import com.cmmplb.core.constants.StringConstant;
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
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.EntryUnit;
import org.ehcache.config.units.MemoryUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKey;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.cache.jcache.JCacheCacheManager;
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

import static org.ehcache.config.builders.CacheManagerBuilder.newCacheManagerBuilder;

/**
 * @author penglibo
 * @date 2021-09-13 14:06:21
 * @since jdk 1.8
 */

@Configuration
@EnableConfigurationProperties(CacheProperties.class)
public class CacheConfig {

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

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
        // EhCache3和Springboot3移除了EhCacheCacheManager
        if (cacheProperties.getCacheType().equals(CacheProperties.CacheType.EHCACHE)) {
            // cacheManager = new EhCacheCacheManager();
            // 缓存类型不再是EhCache了，EhCache3是实现JCache的一种缓存。
            cacheManager = new JCacheCacheManager();
        }
        // GuavaCacheManager被移除, 不再自动配置Guava缓存, 采用CaffeineCacheManager替换, 使用Caffiene替换Guava缓存. 
        if (cacheProperties.getCacheType().equals(CacheProperties.CacheType.CAFFEINE)) {
            cacheManager = new CaffeineCacheManager();
        }
        if (cacheProperties.getCacheType().equals(CacheProperties.CacheType.REDIS)) {
            // 使用redis分布式缓存作为管理器
            cacheManager = new RedisCacheManager(
                    RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory),
                    // 默认策略, 未配置的key会使用这个
                    this.getRedisCacheConfigurationWithTtl(60),
                    // 指定 key 策略
                    this.getRedisCacheConfigurationMap()
            );
        }
        return cacheManager;
    }

    @Bean
    public org.ehcache.CacheManager ehCacheManager() {
        // 基于代码配置ehcache
        //userTokenCache配置
        CacheConfiguration<String, User> userTokenCacheCacheConfiguration = CacheConfigurationBuilder
                .newCacheConfigurationBuilder(String.class, User.class, ResourcePoolsBuilder.newResourcePoolsBuilder()
                        // 堆缓存大小为 100 个条目
                        .heap(100, EntryUnit.ENTRIES)
                        // 堆外缓存大小为 10 MB
                        .offheap(10, MemoryUnit.MB)
                )
                // 设置缓存的生存时间为 10 秒
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(10)))
                // 设置缓存的空闲时间为 5 秒
                .withExpiry(ExpiryPolicyBuilder.timeToIdleExpiration(Duration.ofSeconds(5)))
                .build();

        //otherCache配置
        CacheConfiguration<String,String> otherCacheCacheConfiguration = CacheConfigurationBuilder
                .newCacheConfigurationBuilder(String.class, String.class, ResourcePoolsBuilder.newResourcePoolsBuilder()
                        .heap(100, EntryUnit.ENTRIES)
                        .offheap(10, MemoryUnit.MB)
                        // 磁盘储存,永久储存
                        .disk(100, MemoryUnit.MB, true)
                )
                // 设置缓存的生存时间为 10 秒
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(10)))
                // 设置缓存的空闲时间为 5 秒
                .withExpiry(ExpiryPolicyBuilder.timeToIdleExpiration(Duration.ofSeconds(5)))
                .build();
        return newCacheManagerBuilder()
                // 磁盘储存路径
                .with(CacheManagerBuilder.persistence("/Users/penglibo/Downloads"))
                .withCache("userTokenCache", userTokenCacheCacheConfiguration)
                .withCache("otherCache", otherCacheCacheConfiguration)
                .build(true);
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
