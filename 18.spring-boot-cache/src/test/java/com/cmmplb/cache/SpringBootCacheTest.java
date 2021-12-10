package com.cmmplb.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.*;

import java.util.HashSet;
import java.util.Set;

/**
 * @author penglibo
 * @date 2021-03-29 14:10:17
 */

@Slf4j
@SpringBootTest
public class SpringBootCacheTest {


    private static String host = "192.168.68.129";
    private static String password = "cmmplb";

    private static String commonKey = "model";

    public static void main(String[] args) {
        standalone();
        cluster();
        sentinel();
    }

    /**
     * 单机
     */
    public static void standalone() {
        Jedis jedis = new Jedis(host, 6379);
        jedis.auth(password);
        jedis.set(commonKey, "standalone");
        // 持久化
        // jedis.save();
        System.out.println(jedis.get(commonKey));
        jedis.close();
    }

    /**
     * 集群
     */
    public static void cluster() {
        Set<HostAndPort> nodes = new HashSet<HostAndPort>();
        nodes.add(new HostAndPort(host, 6371));
        nodes.add(new HostAndPort(host, 6372));
        nodes.add(new HostAndPort(host, 6373));
        nodes.add(new HostAndPort(host, 6374));
        nodes.add(new HostAndPort(host, 6375));
        nodes.add(new HostAndPort(host, 6376));
        JedisPoolConfig config = getJedisPoolConfig();
        JedisCluster cluster = new JedisCluster(nodes, 10000, 10000, 100,
                password,
                config);
        cluster.set(commonKey, "cluster");
        System.out.println(cluster.get(commonKey));
        cluster.close();
    }

    /**
     * 哨兵
     */
    public static void sentinel() {
        JedisPoolConfig config = getJedisPoolConfig();
        Set<String> sentinels = new HashSet<String>();
        sentinels.add(new HostAndPort(host, 26379).toString());
        sentinels.add(new HostAndPort(host, 26380).toString());
        sentinels.add(new HostAndPort(host, 26381).toString());
        JedisSentinelPool pool = new JedisSentinelPool("mymaster", sentinels, config, 10000, password);
        Jedis jedis = pool.getResource();
        jedis.set(commonKey,"sentinel");
        System.out.println(jedis.get(commonKey));
    }

    /**
     * 获取连接池配置
     * 注意：
     * 在高版本的jedis jar包，比如本版本2.9.0，JedisPoolConfig没有setMaxActive和setMaxWait属性了
     * 这是因为高版本中官方废弃了此方法，用以下两个属性替换。
     * maxActive  ==>  maxTotal
     * maxWait==>  maxWaitMillis
     */
    private static JedisPoolConfig getJedisPoolConfig() {
        JedisPoolConfig config = new JedisPoolConfig();
        //可用连接实例的最大数目，默认为8；
        //如果赋值为-1，则表示不限制，如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)
        config.setMaxTotal(500);
        config.setMinIdle(2);
        //控制一个pool最多有多少个状态为idle(空闲)的jedis实例，默认值是8
        config.setMaxIdle(500);
        //等待可用连接的最大时间，单位是毫秒，默认值为-1，表示永不超时。
        //如果超过等待时间，则直接抛出JedisConnectionException
        config.setMaxWaitMillis(10000);
        //在空闲时检查有效性, 默认false
        config.setTestWhileIdle(true);
        //在borrow(用)一个jedis实例时，是否提前进行validate(验证)操作；
        //如果为true，则得到的jedis实例均是可用的
        config.setTestOnBorrow(true);
        //是否进行有效性检查
        config.setTestOnReturn(true);
        return config;
    }


}
