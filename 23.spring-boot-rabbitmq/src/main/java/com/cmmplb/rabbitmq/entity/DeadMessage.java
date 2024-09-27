package com.cmmplb.rabbitmq.entity;

import lombok.Data;

/**
 * @author penglibo
 * @date 2024-08-29 17:42:49
 * @since jdk 1.8
 */

@Data
public class DeadMessage {

    // 延时消息类型:1-有消费者，拒绝消费;2-无消费者，消息过期
    private int type;

    private String message;
}
