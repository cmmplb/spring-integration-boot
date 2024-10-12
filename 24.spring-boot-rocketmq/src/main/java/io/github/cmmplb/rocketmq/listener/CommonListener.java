package io.github.cmmplb.rocketmq.listener;

import io.github.cmmplb.rocketmq.constants.RocketMqConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author penglibo
 * @date 2021-09-18 10:56:11
 * @since jdk 1.8
 * 监听普通消息
 */

@Slf4j
@Component
@RocketMQMessageListener(topic = RocketMqConstants.COMMON_TOPIC, consumerGroup = RocketMqConstants.ROCKET_GROUP)
public class CommonListener implements RocketMQListener<String> {

    @Override
    public void onMessage(String s) {
        System.out.println("收到： "+s);
    }
}
