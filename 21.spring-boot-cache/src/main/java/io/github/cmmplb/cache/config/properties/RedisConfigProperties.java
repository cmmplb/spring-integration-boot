package com.cmmplb.cache.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author penglibo
 * @date 2021-09-13 09:36:49
 * @since jdk 1.8
 * {@link org.springframework.boot.autoconfigure.data.redis.RedisProperties}
 */

@Data
@ConfigurationProperties(prefix = "redis")
public class RedisConfigProperties {

    /**
     * 客户端类型-standalone, sentinel, cluster
     */
    private ClientType clusterType;

    /**
     * 连接池类型-jedis, lettuce
     */
    private PoolType poolType = PoolType.LETTUCE;

    /**
     * 使用SSL
     */
    private boolean ssl;

    /**
     * 读取超时(毫秒) timeout.
     */
    private long timeout;

    /**
     * 连接超时（毫秒）
     */
    private long connectTimeout;

    /**
     * 客户端名称
     */
    private String clientName;

    /**
     * 连接池类型
     */
    public enum PoolType {

        /**
         * lettuce
         */
        LETTUCE,

        /**
         * jedis
         */
        JEDIS
    }

    /**
     * 客户端类型
     */
    public enum ClientType {

        /**
         * 单机
         */
        STANDALONE,

        /**
         * 哨兵
         */
        SENTINEL,

        /**
         * 集群
         */
        CLUSTER,
    }

    /**
     * 连接池
     */
    private Pool pool;

    /**
     * 单机
     */
    private Standalone standalone;

    /**
     * sentinel
     */
    private Sentinel sentinel;

    /**
     * cluster
     */
    private Cluster cluster;

    @Data
    public static class Pool {

        /**
         * pool中空闲连接的最大数量 (使用负值为无限制)
         */
        private int maxIdle = 8;
        /**
         * pool中空闲连接的最小数量 (使用负值为无效配置)
         */
        private int minIdle = 0;
        /**
         * pool可以分配的最大连接数 (使用负值为无限制)
         */
        private int maxActive = 8;
        /**
         * 连接池最大阻塞等待时间 (单位: 毫秒, 使用负值为无限制)
         */
        private long maxWait = -1L;
        /**
         * 从pool中获取连接时, 是否校验连接有效性(影响性能, 生产环境不建议开启)
         */
        private boolean testOnBorrow = false;
        /**
         * 返还到pool中连接时, 是否校验连接有效性(影响性能, 生产环境不建议开启)
         */
        private boolean testOnReturn = false;
        /**
         * pool中定时进行连接有效性扫描, 无效连接将被释放
         */
        private boolean testWhileIdle = false;
        /**
         * pool中定时进行连接有效性扫描时间间隔(单位: 毫秒, 使用负值为不启用扫描)
         */
        private long timeBetweenEvictionRunsMillis = -1L;
    }

    /**
     * 单机
     */
    @Data
    public static class Standalone {

        /**
         * 连接工厂使用的数据库索引
         */
        private int database = 0;

        /**
         * Redis服务器主机
         */
        private String host = "localhost";

        /**
         * redis服务器端口
         */
        private int port = 6379;

        /**
         * redis6.0提供用户名
         */
        private String username;

        /**
         * 登录redis服务器的密码
         */
        private String password;

    }

    @Data
    public static class Sentinel {

        /**
         * 哨兵监听的RedisServer的名称
         */
        private String master;

        /**
         * 哨兵的配置列表
         */
        private List<String> nodes;

        /**
         * 连接工厂使用的数据库索引
         */
        private int database;

        /**
         * 登录redis服务器的密码
         */
        private String password;
    }

    @Data
    public static class Cluster {

        /**
         * 主机列表
         */
        private List<String> nodes;

        /**
         * 主机密码
         */
        private String password;

        /**
         * 跨集群执行命令时要遵循的最大重定向数量
         */
        private int maxRedirects;
    }

}
