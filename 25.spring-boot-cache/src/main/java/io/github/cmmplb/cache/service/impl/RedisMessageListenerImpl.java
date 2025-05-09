package io.github.cmmplb.cache.service.impl;

import io.github.cmmplb.cache.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

/**
 * @author penglibo
 * @date 2021-09-30 10:19:41
 * @since jdk 1.8
 */

@Slf4j
@Service
public class RedisMessageListenerImpl implements MessageListener {

    @Autowired
    private RedisUtil redisUtil;

    public static final String REDIS_LISTENER_CHANNEL_MESSAGE = "redis";

    public static final String REDIS_CHANNEL_MESSAGE = "user-*";

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String channel = new String(message.getChannel(), StandardCharsets.UTF_8);
        String messageBody = new String(message.getBody(), StandardCharsets.UTF_8);
        log.info("channel: {} , message: {} ", channel, messageBody);
    }

    public boolean sendMessage() {
        redisUtil.convertAndSend(REDIS_LISTENER_CHANNEL_MESSAGE, "发送消息1");
        redisUtil.convertAndSend("user-1", "发送消1息2");
        return true;
    }

}
