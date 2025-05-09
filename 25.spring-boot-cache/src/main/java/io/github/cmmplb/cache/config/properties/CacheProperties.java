package io.github.cmmplb.cache.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author penglibo
 * @date 2024-05-21 17:31:16
 * @since jdk 1.8
 */

@Data
@ConfigurationProperties(prefix = "cache")
public class CacheProperties {

    /**
     * 是否开启缓存
     */
    private boolean enabled = false;

    /**
     * 缓存类型, 由于springboot2.5版本没有cache2k整合, 单独创个类型
     */
    private CacheType cacheType;

    /**
     * {@link org.springframework.boot.autoconfigure.cache.CacheType}
     */
    public enum CacheType {

        CACHE2K,

        EHCACHE,

        CAFFEINE,

        REDIS,
    }

    public static final String PREFIX = "cache";

    public static final String ENABLED = "enabled";
}
