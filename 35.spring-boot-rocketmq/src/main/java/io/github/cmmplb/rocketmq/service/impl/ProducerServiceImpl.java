package io.github.cmmplb.rocketmq.service.impl;

import io.github.cmmplb.rocketmq.constants.RocketMqConstants;
import io.github.cmmplb.rocketmq.service.ProducerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

/**
 * @author penglibo
 * @date 2021-09-18 10:31:20
 * @since jdk 1.8
 */

@Slf4j
@Service
public class ProducerServiceImpl implements ProducerService {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Override
    public void send2CommonQueue(String msg) {
        // 发送消息
        rocketMQTemplate.convertAndSend(RocketMqConstants.COMMON_TOPIC, msg);
        // 发送spring的Message
        rocketMQTemplate.send(RocketMqConstants.COMMON_TOPIC, MessageBuilder.withPayload(msg).build());
        // 发送异步消息
        rocketMQTemplate.asyncSend(RocketMqConstants.COMMON_TOPIC, msg, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.println("发送成功");
            }

            @Override
            public void onException(Throwable throwable) {
                System.out.println("发送失败");
            }
        });
        // 发送顺序消息
        rocketMQTemplate.syncSendOrderly(RocketMqConstants.COMMON_TOPIC, "98456237,创建", "98456237");
        rocketMQTemplate.syncSendOrderly(RocketMqConstants.COMMON_TOPIC, "98456237,支付", "98456237");
        rocketMQTemplate.syncSendOrderly(RocketMqConstants.COMMON_TOPIC, "98456237,完成", "98456237");
    }

    @Override
    public void send2TransactionQueue(String message) {
        Message<String> msg = MessageBuilder.withPayload(message).build();

        rocketMQTemplate.sendMessageInTransaction(RocketMqConstants.TRANSACTION_TOPIC, msg, null);
    }

    @RocketMQTransactionListener
    static class TransactionListener implements RocketMQLocalTransactionListener {
        @Override
        public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
            System.out.println("执行本地事务");
            return RocketMQLocalTransactionState.UNKNOWN;
        }

        @Override
        public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
            System.out.println("执行事务回查");
            return RocketMQLocalTransactionState.COMMIT;
        }
    }
}
