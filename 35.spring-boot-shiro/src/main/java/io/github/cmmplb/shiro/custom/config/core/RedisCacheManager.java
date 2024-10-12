package io.github.cmmplb.shiro.custom.config.core;

import lombok.Data;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.crazycake.shiro.IRedisManager;
import org.crazycake.shiro.serializer.ObjectSerializer;
import org.crazycake.shiro.serializer.RedisSerializer;
import org.crazycake.shiro.serializer.StringSerializer;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author penglibo
 * @date 2022-02-14 17:58:42
 * @since jdk 1.8
 * 自定义Redis缓存管理, 重写keyPrefix, 原来的RedisCacheManager会在keyPrefix加上类的全限定名
 * {@link org.crazycake.shiro.RedisCacheManager}
 */

@Data
@SuppressWarnings({"rawtypes", "unchecked"})
public class RedisCacheManager implements CacheManager {

    // fast lookup by name map
    private final ConcurrentMap<String, Cache> caches = new ConcurrentHashMap<>();
    // private RedisSerializer<?> keySerializer = new StringSerializer();
    // private RedisSerializer<?> keySerializer = new StringSerializer();
    private RedisSerializer<?> valueSerializer = new ObjectSerializer();
    private RedisSerializer<?> keySerializer = new StringSerializer();

    private IRedisManager redisManager;

    // expire time in seconds
    public static final int DEFAULT_EXPIRE = 1800;
    private int expire = DEFAULT_EXPIRE;
    // 配置缓存的话要求放在session里面的实体类必须有个id标识, 这里对应的是用户id
    public static final String DEFAULT_PRINCIPAL_ID_FIELD_NAME = "id";
    private String principalIdFieldName = DEFAULT_PRINCIPAL_ID_FIELD_NAME;

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        Cache<K, V> cache = caches.get(name);
        if (cache == null) {
            cache = new RedisCache<>(redisManager, expire, principalIdFieldName);
            caches.put(name, cache);
        }
        return cache;
    }
}
