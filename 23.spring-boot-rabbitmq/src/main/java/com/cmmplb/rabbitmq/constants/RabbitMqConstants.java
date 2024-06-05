package com.cmmplb.rabbitmq.constants;

/**
 * @author penglibo
 * @date 2021-05-19 16:24:31
 * @since jdk 1.8
 */
public interface RabbitMqConstants {

    // -----------------------------简单队列模式-----------------------------------------

    /**
     * 简单队列-一个生产者对应一个消费者
     */
    String SIMPLE_QUEUE = "simple_queue";

    // -----------------------------Work模式-----------------------------------------

    /**
     * Work模式-一个生产者对应多个消费者
     */
    String WORK_QUEUE = "work_queue";

    // ----------------------------订阅模型-Fanout(广播模式)-----------------------------------------

    /**
     * 订阅模型-Fanout-一条消息被多个消费者消费
     */
    String ONE_FANOUT_QUEUE = "one_fanout_queue";
    String TWO_FANOUT_QUEUE = "two_fanout_queue";
    /**
     * 订阅模型-Fanout-交换机
     */
    String FANOUT_EXCHANGE = "fanout.exchange";

    // ---------------------------订阅模型-Direct(路由模式)--------------------------------------------

    /**
     * 订阅模型-Direct-根据Routing Key接收到消息
     */
    String ONE_DIRECT_QUEUE = "one_direct_queue";
    String TWO_DIRECT_QUEUE = "two_direct_queue";
    /**
     * 订阅模型-Direct-交换机
     */
    String DIRECT_EXCHANGE = "direct.exchange";
    /**
     * 订阅模型-Direct-routingKey
     */
    String ONE_DIRECT_ROUTING_KEY = "direct.routingKey.one";
    String TWO_DIRECT_ROUTING_KEY = "direct.routingKey.two";
    String THREE_DIRECT_ROUTING_KEY = "direct.routingKey.three";

    // --------------------------订阅模型-Topic (主题模式)-----------------------------------------

    /**
     * 订阅模型-Topic-根据RoutingKey路由消息-同时匹配通配符
     */
    String ONE_TOPIC_QUEUE = "one_topic_queue";
    String TWO_TOPIC_QUEUE = "two_topic_queue";
    /**
     * 订阅模型-Topic-交换机
     */
    String TOPIC_EXCHANGE = "topic.exchange";
    /**
     * 订阅模型-Topic-通配符-routingKey
     */
    String ONE_TOPIC_ROUTING_KEY = "*.a.*";
    String TWO_TOPIC_ROUTING_KEY = "*.*.b"; // #表示0个或若干个关键字，*表示一个关键字
    String THREE_TOPIC_ROUTING_KEY = "c.#";


    // -----------------------------死信队列模式-----------------------------------------
    /**
     * 死信队列-将消息拒绝消费消息(丢失消息)给死信队列或者发送消息到一个没有消费者的普通队列中，过期时间达到后死信队列会接收到消息
     */
    String DEAD_QUEUE = "dead_queue";
    /**
     * 普通队列，绑定死信交换机
     */
    String COMMON_QUEUE = "common_queue";
    /**
     * 死信交换机
     */
    String DEAD_EXCHANGE = "dead.exchange";
    /**
     * 普通topic交换机
     */
    String COMMON_EXCHANGE = "common.exchange";
    /**
     * 死信队列路由key
     */
    String DEAD_ROUTING_KEY = "dead.#";
    /**
     * 普通路由key
     */
    String COMMON_ROUTING_KEY = "common.#";
    /**
     * 过期时间，单位毫秒
     */
    Integer MESSAGE_TTL_TIME = 1000 * 3;


}
