package com.cmmplb.cache.entity;

import com.cmmplb.cache.service.IRedisMessage;
import lombok.extern.slf4j.Slf4j;

/**
 * @author penglibo
 * @date 2021-09-30 14:42:33
 * @since jdk 1.8
 */

@Slf4j
public class RedisChannel implements IRedisMessage {

    @Override
    public void receiveMessage(RedisMessage message) {
        log.info("channel {} , message {} ", message.getChannel(), message.msgBody);
    }

}
