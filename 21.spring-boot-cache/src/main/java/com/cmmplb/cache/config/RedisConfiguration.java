package com.cmmplb.cache.config;

import com.cmmplb.cache.config.properties.RedisConfigProperties;
import com.cmmplb.cache.config.serializer.FastJson2JsonRedisSerializer;
import com.cmmplb.cache.service.impl.RedisMessageListenerImpl;
import io.github.cmmplb.core.exception.CustomException;
import io.github.cmmplb.core.result.HttpCodeEnum;
import io.github.cmmplb.core.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author penglibo
 * @date 2021-08-05 10:13:12
 * @since jdk 1.8
 */

@Slf4j
@Configuration
@EnableConfigurationProperties(RedisConfigProperties.class)
public class RedisConfiguration {

    @Autowired
    private RedisConfigProperties redisProperties;

    @Autowired
    private RedisMessageListenerImpl redisMessageService;

    /**
     * Redis消息监听容器
     */
    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory connectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        // 指定多个通配符
        container.addMessageListener(redisMessageService, Arrays.asList(
                // 订阅名称叫cache的通道, 类似 Redis 中的subscribe命令
                new ChannelTopic(RedisMessageListenerImpl.REDIS_LISTENER_CHANNEL_MESSAGE),
                // 订阅名称以 'user-' 开头的全部通道, 类似 Redis 的 pSubscribe 命令
                new PatternTopic(RedisMessageListenerImpl.REDIS_CHANNEL_MESSAGE))
        );
        // 添加其他配置，如线程池大小等
        return container;
    }

    @Bean
    @ConditionalOnBean(RedisConfigProperties.class)
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // 设置默认的序列化方式为StringRedisSerializer
        redisTemplate.setDefaultSerializer(stringRedisSerializer);
        // 使用StringRedisSerializer来序列化和反序列化redis的key值
        redisTemplate.setKeySerializer(stringRedisSerializer);
        // Hash的key也采用StringRedisSerializer的序列化方式
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        FastJson2JsonRedisSerializer<Object> fastJson2JsonRedisSerializer = new FastJson2JsonRedisSerializer<>(Object.class);
        redisTemplate.setValueSerializer(fastJson2JsonRedisSerializer);
        redisTemplate.setHashValueSerializer(fastJson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    /**
     * Redis连接工厂配置
     */
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisConnectionFactory redisConnectionFactory;
        log.info("初始化redis连接工厂,客户端类型： " + getProperties().getClusterType());
        log.info("初始化redis连接池,池类型： " + getProperties().getPoolType());
        if (RedisConfigProperties.PoolType.JEDIS.equals(getProperties().getPoolType())) {
            redisConnectionFactory = getConnectionFactory4Jedis();
        } else if (RedisConfigProperties.PoolType.LETTUCE.equals(getProperties().getPoolType())) {
            redisConnectionFactory = getConnectionFactory4Lettuce();
        } else {
            throw new CustomException(HttpCodeEnum.INVALID_ERROR);
        }
        return redisConnectionFactory;
    }

    /**
     * 获取单机配置
     * @return RedisStandaloneConfiguration
     */
    private RedisStandaloneConfiguration getStandaloneConf() {
        RedisStandaloneConfiguration standaloneConf = new RedisStandaloneConfiguration();
        standaloneConf.setHostName(getProperties().getStandalone().getHost());
        standaloneConf.setPort(getProperties().getStandalone().getPort());
        standaloneConf.setPassword(RedisPassword.of(getProperties().getStandalone().getPassword()));
        standaloneConf.setDatabase(getProperties().getStandalone().getDatabase());
        return standaloneConf;
    }

    /**
     * 获取Cluster配置
     * @return RedisClusterConfiguration
     */
    private RedisClusterConfiguration getClusterConf() {
        RedisClusterConfiguration clusterConfig = new RedisClusterConfiguration(getProperties().getCluster().getNodes());
        clusterConfig.setPassword(RedisPassword.of(getProperties().getCluster().getPassword()));
        clusterConfig.setMaxRedirects(getProperties().getCluster().getMaxRedirects());
        return clusterConfig;
    }

    /**
     * 获取哨兵配置
     * @return RedisSentinelConfiguration
     */
    private RedisSentinelConfiguration getSentinelConf() {
        Set<RedisNode> redisNodeSet = new HashSet<>();
        List<String> nodes = getProperties().getSentinel().getNodes();
        for (String node : nodes) {
            String[] sentinelHostAndPort = node.split(":");
            RedisNode redisNode = new RedisNode(sentinelHostAndPort[0], Integer.parseInt(sentinelHostAndPort[1]));
            redisNodeSet.add(redisNode);
        }
        RedisSentinelConfiguration sentinelConf = new RedisSentinelConfiguration();
        sentinelConf.setMaster(getProperties().getSentinel().getMaster());
        sentinelConf.setSentinels(redisNodeSet);
        if (StringUtil.isNotEmpty(getProperties().getSentinel().getPassword())) {
            sentinelConf.setPassword(RedisPassword.of(getProperties().getSentinel().getPassword()));
        }
        sentinelConf.setDatabase(getProperties().getSentinel().getDatabase());
        return sentinelConf;
    }

    /**
     * 获取jedis连接工厂
     * @return RedisConnectionFactory
     */
    private RedisConnectionFactory getConnectionFactory4Jedis() {
        RedisConnectionFactory factory;
        JedisClientConfiguration clientConfiguration = getPoolConfig4Jedis();
        if (RedisConfigProperties.ClientType.STANDALONE.equals(getProperties().getClusterType())) {
            factory = new JedisConnectionFactory(getStandaloneConf(), clientConfiguration);
        } else if (RedisConfigProperties.ClientType.SENTINEL.equals(getProperties().getClusterType())) {
            factory = new JedisConnectionFactory(getSentinelConf(), clientConfiguration);
        } else if (RedisConfigProperties.ClientType.CLUSTER.equals(getProperties().getClusterType())) {
            factory = new JedisConnectionFactory(getClusterConf(), clientConfiguration);
        } else {
            throw new CustomException(HttpCodeEnum.INVALID_ERROR);
        }
        return factory;
    }

    /**
     * 获取lettuce连接工厂
     * @return RedisConnectionFactory
     */
    private RedisConnectionFactory getConnectionFactory4Lettuce() {
        LettuceConnectionFactory factory;
        LettuceClientConfiguration clientConfiguration = getPoolConfig4Lettuce();
        if (RedisConfigProperties.ClientType.STANDALONE.equals(getProperties().getClusterType())) {
            factory = new LettuceConnectionFactory(getStandaloneConf(), clientConfiguration);
        } else if (RedisConfigProperties.ClientType.SENTINEL.equals(getProperties().getClusterType())) {
            factory = new LettuceConnectionFactory(getSentinelConf(), clientConfiguration);
        } else if (RedisConfigProperties.ClientType.CLUSTER.equals(getProperties().getClusterType())) {
            factory = new LettuceConnectionFactory(getClusterConf(), clientConfiguration);
        } else {
            throw new CustomException(HttpCodeEnum.INVALID_ERROR);
        }
        return factory;
    }

    /**
     * Jedis客户端配置
     * @return JedisClientConfiguration
     */
    private JedisClientConfiguration getPoolConfig4Jedis() {
        JedisClientConfiguration.JedisClientConfigurationBuilder builder = JedisClientConfiguration.builder();
        if (getProperties().isSsl()) {
            builder.useSsl();
        }
        if (StringUtils.hasText(getProperties().getClientName())) {
            builder.clientName(getProperties().getClientName());
        }
        if (getProperties().getTimeout() != 0) {
            builder.readTimeout(Duration.ofMillis(getProperties().getTimeout()));
        }
        if (getProperties().getConnectTimeout() != 0) {
            builder.connectTimeout(Duration.ofMillis(getProperties().getConnectTimeout()));
        }
        return builder.usePooling().poolConfig(procPoolConfig()).build();
    }

    /**
     * Lettuce客户端配置
     * @return LettuceClientConfiguration
     */
    private LettuceClientConfiguration getPoolConfig4Lettuce() {
        LettucePoolingClientConfiguration.LettucePoolingClientConfigurationBuilder builder = LettucePoolingClientConfiguration.builder();
        if (getProperties().isSsl()) {
            builder.useSsl();
        }
        if (StringUtils.hasText(getProperties().getClientName())) {
            builder.clientName(getProperties().getClientName());
        }
        if (getProperties().getTimeout() != 0) {
            builder.commandTimeout(Duration.ofMillis(getProperties().getTimeout()));
        }
        return builder.poolConfig(procPoolConfig()).build();
    }

    /**
     * 获取进程池配置
     * @return GenericObjectPoolConfig
     */
    private GenericObjectPoolConfig<?> procPoolConfig() {
        GenericObjectPoolConfig<?> poolConfig = new GenericObjectPoolConfig<>();
        poolConfig.setMaxTotal(getProperties().getPool().getMaxActive());
        poolConfig.setMaxIdle(getProperties().getPool().getMaxIdle());
        poolConfig.setMaxWait((Duration.ofMillis(getProperties().getPool().getMaxWait())));
        poolConfig.setMinIdle(getProperties().getPool().getMinIdle());
        poolConfig.setTestOnBorrow(getProperties().getPool().isTestOnBorrow());
        poolConfig.setTestOnReturn(getProperties().getPool().isTestOnReturn());
        poolConfig.setTestWhileIdle(getProperties().getPool().isTestWhileIdle());
        return poolConfig;
    }

    protected final RedisConfigProperties getProperties() {
        return this.redisProperties;
    }

}

