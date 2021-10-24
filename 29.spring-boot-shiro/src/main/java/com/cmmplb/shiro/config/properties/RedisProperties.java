package com.cmmplb.shiro.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author penglibo
 * @date 2021-09-27 10:58:02
 * @since jdk 1.8
 */

@Data
@Component
@ConfigurationProperties(prefix = "spring.redis")
public class RedisProperties {

    /**
     * 端口号
     */
    private int port;

    /**
     * 地址
     */
    private String host;

    /**
     * 超时时间
     */
    private int timeout;

    /**
     * 数据库
     */
    private int database;

    /**
     * 密码
     */
    private String password;

    /**
     * see {@link org.crazycake.shiro.RedisManager#DEFAULT_HOST}
     * 高版本整合shiro-redis的RedisManager设置host和port是一起的。
     * @return
     */
    public String getHostAndPort() {
        return host + ":" + port;
    }
}
