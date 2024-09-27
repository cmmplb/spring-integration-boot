package com.cmmplb.cache;

import lombok.extern.slf4j.Slf4j;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.UserManagedCache;
import org.ehcache.config.builders.*;
import org.ehcache.config.units.MemoryUnit;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

/**
 * @author penglibo
 * @date 2021-03-29 14:10:17
 */

@Slf4j
@SpringBootTest
public class CacheTest {


    private static String host = "192.168.68.129";
    private static String password = "cmmplb";

    private static String commonKey = "model";

    public static void main(String[] args) {
        standalone();
        cluster();
        sentinel();
    }

    @Test
    public void fun1() {
        CacheManager manager = CacheManagerBuilder.newCacheManagerBuilder()
                .withCache("myCache", CacheConfigurationBuilder.newCacheConfigurationBuilder(Integer.class, String.class, ResourcePoolsBuilder.heap(100).build()))
                .build(true);

        //通过别名获取缓存
        Cache<Integer, String> cache = manager.getCache("myCache", Integer.class, String.class);

        cache.put(1001, "haha");

        String value = cache.get(1001);
        System.out.println(value);

        // 移除一个给定的Cache，CacheManager不仅会删除它对Cache的引用，而且还会关闭它。
        manager.removeCache("myCache");
        // 关闭CacheManager提供的所有临时资源。
        manager.close();
    }

    // https://blog.csdn.net/lianghecai52171314/article/details/124419796
    // 创建CacheManager的同时指定Cache
    @Test
    public void fun2() {
        CacheManager manager = CacheManagerBuilder.newCacheManagerBuilder()
                .build(true);

        //创建Cache实例
        CacheConfigurationBuilder<Integer, String> builder = CacheConfigurationBuilder.newCacheConfigurationBuilder(Integer.class, String.class,
                ResourcePoolsBuilder.heap(10)  //堆内缓存，速度最快
                        .offheap(2, MemoryUnit.MB)  // 堆外内存，速度低于 heap，但是高于 disk
                // .disk(1, MemoryUnit.GB)   //磁盘缓存，速度最低，相对于 heap和off-heap，disk可以分配大量资源空间
        ).withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(10)));

        Cache<Integer, String> cache = manager.createCache("myCache", builder);
        //写缓存
        cache.put(1001, "xixi");
        //读缓存
        String value = cache.get(1001);
        System.out.println(value);

        manager.removeCache("myCache");
        //关闭资源
        manager.close();
    }

    // 将数据缓存到本地硬盘上
    @Test
    public void fun4() {
        CacheManager manager = CacheManagerBuilder
                .persistence("d:/CacheData")    //硬盘缓存文件位置
                .builder(CacheManagerBuilder.newCacheManagerBuilder())
                .build();
        manager.init();

        //创建Cache实例
        CacheConfigurationBuilder<Integer, String> builder = CacheConfigurationBuilder.newCacheConfigurationBuilder(Integer.class, String.class,
                ResourcePoolsBuilder.heap(10));

        Cache<Integer, String> cache = manager.createCache("myCache", builder);
        //写缓存
        cache.put(1001, "xixi");
        //读缓存
        String value = cache.get(1001);
        System.out.println(value);

        manager.removeCache("myCache");
        //关闭资源
        manager.close();
    }

    @Test
    public void fun3() {
        UserManagedCache<Integer, String> cache = UserManagedCacheBuilder.newUserManagedCacheBuilder(Integer.class, String.class)
                .build(false);
        cache.init();

        //写
        cache.put(11, "aa");
        //读
        String value = cache.get(11);
        System.out.println(value);

        cache.close();
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
        // JedisPoolConfig config = getJedisPoolConfig();
        // JedisCluster cluster = new JedisCluster(nodes, 10000, 10000, 100,
        //         password,
        //         config);
        // cluster.set(commonKey, "cluster");
        // System.out.println(cluster.get(commonKey));
        // cluster.close();
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
     * 在高版本的jedis jar包, 比如本版本2.9.0, JedisPoolConfig没有setMaxActive和setMaxWait属性了
     * 这是因为高版本中官方废弃了此方法, 用以下两个属性替换. 
     * maxActive  ==>  maxTotal
     * maxWait==>  maxWaitMillis
     */
    private static JedisPoolConfig getJedisPoolConfig() {
        JedisPoolConfig config = new JedisPoolConfig();
        //可用连接实例的最大数目, 默认为8；
        //如果赋值为-1, 则表示不限制, 如果pool已经分配了maxActive个jedis实例, 则此时pool的状态为exhausted(耗尽)
        config.setMaxTotal(500);
        config.setMinIdle(2);
        //控制一个pool最多有多少个状态为idle(空闲)的jedis实例, 默认值是8
        config.setMaxIdle(500);
        //等待可用连接的最大时间, 单位是毫秒, 默认值为-1, 表示永不超时. 
        //如果超过等待时间, 则直接抛出JedisConnectionException
        config.setMaxWait(Duration.ofMillis(10000));
        //在空闲时检查有效性, 默认false
        config.setTestWhileIdle(true);
        //在borrow(用)一个jedis实例时, 是否提前进行validate(验证)操作；
        //如果为true, 则得到的jedis实例均是可用的
        config.setTestOnBorrow(true);
        //是否进行有效性检查
        config.setTestOnReturn(true);
        return config;
    }


}
