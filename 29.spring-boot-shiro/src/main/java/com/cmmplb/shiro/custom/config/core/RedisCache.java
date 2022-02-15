package com.cmmplb.shiro.custom.config.core;

import com.cmmplb.core.utils.ObjectUtil;
import com.cmmplb.redis.serializer.FastJson2JsonRedisSerializer;
import com.cmmplb.shiro.general.constants.AuthorizationConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.CollectionUtils;
import org.crazycake.shiro.IRedisManager;
import org.crazycake.shiro.exception.CacheManagerPrincipalIdNotAssignedException;
import org.crazycake.shiro.exception.PrincipalIdNullException;
import org.crazycake.shiro.exception.PrincipalInstanceException;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author penglibo
 * @date 2022-02-14 17:58:42
 * @since jdk 1.8
 * 改写了一些东西
 * {@link org.crazycake.shiro.RedisCache}
 */

@Slf4j
public class RedisCache<K, V> implements Cache<K, V> {

    private RedisSerializer<String> keySerializer = new StringRedisSerializer();
    private RedisSerializer<Object> valueSerializer = new FastJson2JsonRedisSerializer<>(Object.class);
    ;
    private IRedisManager redisManager;
    private String keyPrefix = AuthorizationConstants.UID_PERMISSIONS_CACHE_PREFIX;
    private int expire;
    private String principalIdFieldName = RedisCacheManager.DEFAULT_PRINCIPAL_ID_FIELD_NAME;

    public RedisCache(IRedisManager redisManager, int expire, String principalIdFieldName) {
        if (redisManager == null) {
            throw new IllegalArgumentException("redisManager cannot be null.");
        }
        this.redisManager = redisManager;
        this.expire = expire;
        if (principalIdFieldName != null) {
            this.principalIdFieldName = principalIdFieldName;
        }
    }

    @Override
    public V get(K key) throws CacheException {
        if (key == null) {
            return null;
        }

        String redisCacheKey = getRedisCacheKey(key);
        byte[] rawValue = redisManager.get(keySerializer.serialize(redisCacheKey));
        if (rawValue == null) {
            return null;
        }
        return ObjectUtil.cast(valueSerializer.deserialize(rawValue));
    }

    @Override
    public V put(K key, V value) throws CacheException {
        if (key == null) {
            log.warn("Saving a null key is meaningless, return value directly without call Redis.");
            return value;
        }
        String redisCacheKey = getRedisCacheKey(key);
        redisManager.set(keySerializer.serialize(redisCacheKey), value != null ? valueSerializer.serialize(value) : null, expire);
        return value;
    }

    @Override
    public V remove(K key) throws CacheException {
        if (key == null) {
            return null;
        }
        String redisCacheKey = getRedisCacheKey(key);
        byte[] rawValue = redisManager.get(keySerializer.serialize(redisCacheKey));
        redisManager.del(keySerializer.serialize(redisCacheKey));
        return ObjectUtil.cast(valueSerializer.deserialize(rawValue));
    }

    private String getRedisCacheKey(K key) {
        if (key == null) {
            return null;
        }
        /*if (keySerializer instanceof StringSerializer) {
            return this.keyPrefix + getStringRedisKey(key);
        }
        return key;*/
        return this.keyPrefix + getStringRedisKey(key);
    }

    private String getStringRedisKey(K key) {
        String redisKey;
        if (key instanceof PrincipalCollection) {
            redisKey = getRedisKeyFromPrincipalIdField((PrincipalCollection) key);
        } else {
            redisKey = key.toString();
        }
        return redisKey;
    }

    private String getRedisKeyFromPrincipalIdField(PrincipalCollection key) {
        Object principalObject = key.getPrimaryPrincipal();
        if (principalObject instanceof String) {
            return principalObject.toString();
        }
        Method pincipalIdGetter = getPrincipalIdGetter(principalObject);
        return getIdObj(principalObject, pincipalIdGetter);
    }

    private String getIdObj(Object principalObject, Method pincipalIdGetter) {
        String redisKey;
        try {
            Object idObj = pincipalIdGetter.invoke(principalObject);
            if (idObj == null) {
                throw new PrincipalIdNullException(principalObject.getClass(), this.principalIdFieldName);
            }
            redisKey = idObj.toString();
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new PrincipalInstanceException(principalObject.getClass(), this.principalIdFieldName, e);
        }
        return redisKey;
    }

    private Method getPrincipalIdGetter(Object principalObject) {
        Method pincipalIdGetter;
        String principalIdMethodName = this.getPrincipalIdMethodName();
        try {
            pincipalIdGetter = principalObject.getClass().getMethod(principalIdMethodName);
        } catch (NoSuchMethodException e) {
            throw new PrincipalInstanceException(principalObject.getClass(), this.principalIdFieldName);
        }
        return pincipalIdGetter;
    }

    private String getPrincipalIdMethodName() {
        if (this.principalIdFieldName == null || "".equals(this.principalIdFieldName)) {
            throw new CacheManagerPrincipalIdNotAssignedException();
        }
        return "get" + this.principalIdFieldName.substring(0, 1).toUpperCase() + this.principalIdFieldName.substring(1);
    }


    @Override
    public void clear() throws CacheException {
        Set<byte[]> keys = redisManager.keys(keySerializer.serialize(this.keyPrefix + "*"));
        if (keys == null || keys.size() == 0) {
            return;
        }
        for (byte[] key : keys) {
            redisManager.del(key);
        }
    }

    @Override
    public int size() {
        return ObjectUtil.cast(redisManager.dbSize(keySerializer.serialize(this.keyPrefix + "*")));
    }

    @Override
    public Set<K> keys() {
        Set<byte[]> keys = redisManager.keys(keySerializer.serialize(this.keyPrefix + "*"));

        if (CollectionUtils.isEmpty(keys)) {
            return Collections.emptySet();
        }

        Set<K> convertedKeys = new HashSet<K>();
        for (byte[] key : keys) {
            convertedKeys.add(ObjectUtil.cast(keySerializer.deserialize(key)));
        }
        return convertedKeys;
    }

    @Override
    public Collection<V> values() {
        Set<byte[]> keys = redisManager.keys(keySerializer.serialize(this.keyPrefix + "*"));

        if (CollectionUtils.isEmpty(keys)) {
            return Collections.emptySet();
        }

        List<V> values = new ArrayList<V>(keys.size());
        for (byte[] key : keys) {
            V value = ObjectUtil.cast(valueSerializer.deserialize(redisManager.get(key)));
            if (value != null) {
                values.add(value);
            }
        }
        return Collections.unmodifiableList(values);
    }
}
