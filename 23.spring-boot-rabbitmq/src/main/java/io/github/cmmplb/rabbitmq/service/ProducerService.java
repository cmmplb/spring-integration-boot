package io.github.cmmplb.rabbitmq.service;

/**
 * @author penglibo
 * @date 2021-05-26 15:41:19
 * @since jdk 1.8
 */

public interface ProducerService {

    /**
     * 发送消息到简单队列
     * @param message
     */
    void send2SimpleQueue(String message);

    /**
     * 发送到work队列
     * @param message
     */
    void send2WorkQueue(String message);

    /**
     * 发送到订阅模型Fanout(广播模式)
     * @param message
     */
    void send2FanoutQueue(String message);

    /**
     * 发送到订阅模型Direct(路由模式)
     * @param routingKey
     * @param message
     */
    void send2DirectQueue(String routingKey, String message);

    /**
     * 发送到订阅模型Topic(通配符模式)
     * @param routingKey
     * @param message
     */
    void send2TopicQueue(String routingKey, String message);

    /**
     * 发送到死信队列
     * @param routingKey
     * @param message
     */
    void send2DeadQueue(String routingKey, String message);
}
