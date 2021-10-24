package com.cmmplb.cache.service.impl;

import com.cmmplb.cache.config.RedisConfig;
import com.cmmplb.cache.entity.RedisMessage;
import com.cmmplb.cache.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import java.lang.management.ManagementFactory;

/**
 * @author penglibo
 * @date 2021-09-30 10:19:41
 * @since jdk 1.8
 */

@Slf4j
@Service
public class RedisMessageServiceImpl implements MessageListener {

    @Autowired
    private RedisUtil redisUtil;


    @Override
    public void onMessage(Message message, byte[] pattern) {
        onMessage(new RedisMessage(message.getChannel(), message.getBody()));
    }

    public void onMessage(RedisMessage message) {
        log.info("channel {} , message {} ", message.getChannel(), message.msgBody);
    }

    public void sendMessage(String message) {
        String nodeId = ManagementFactory.getRuntimeMXBean().getName();
        RedisMessage.MessageBody messageBody = new RedisMessage.MessageBody(message, nodeId);
        log.info("nodeId:{},messageBody:{}", nodeId, messageBody);
        redisUtil.convertAndSend(RedisConfig.REDIS_LISTENER_CHANNEL_MESSAGE, messageBody);
        // redisUtil.convertAndSend(RedisConfig.REDIS_ADAPTER_CHANNEL_MESSAGE, messageBody);
    }

}
