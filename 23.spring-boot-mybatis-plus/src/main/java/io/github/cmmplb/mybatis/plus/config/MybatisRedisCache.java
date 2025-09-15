package io.github.cmmplb.mybatis.plus.config;

import io.github.cmmplb.redis.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.Cache;
import org.springframework.data.redis.connection.RedisServerCommands;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author penglibo
 * @date 2021-08-22 17:04:46
 * @since jdk 1.8
 * Mapper接口使用注解 ->@CacheNamespace(implementation=MybatisRedisCache.class,eviction=MybatisRedisCache.class)
 */

@Slf4j
public class MybatisRedisCache implements Cache {

    @Resource
    private RedisService redisService;

    // 读写锁
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);

    private String id;

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void putObject(Object key, Object value) {
        redisService.set(key.toString(), value);

    }

    @Override
    public Object getObject(Object key) {
        return redisService.get(key.toString());
    }

    @Override
    public Object removeObject(Object key) {
        return redisService.del(key.toString());
    }

    @Override
    public void clear() {
        Set<?> keys = redisService.keys("*:" + this.id + "*");
        if (!CollectionUtils.isEmpty(keys)) {
            redisService.del(String.valueOf(keys));
        }
    }

    @Override
    public int getSize() {
        Long size = redisService.execute(RedisServerCommands::dbSize);
        return size.intValue();
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return this.readWriteLock;
    }
}
