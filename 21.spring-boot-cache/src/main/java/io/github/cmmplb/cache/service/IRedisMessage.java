package io.github.cmmplb.cache.service;

import io.github.cmmplb.cache.domain.dto.RedisMessage;

/**
 * @author penglibo
 * @date 2021-09-30 14:43:33
 * @since jdk 1.8
 */
public interface IRedisMessage {

    String METHOD_NAME = "receiveMessage";

    void receiveMessage(RedisMessage message);
}


