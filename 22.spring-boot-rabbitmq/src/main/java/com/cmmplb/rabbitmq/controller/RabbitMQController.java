package com.cmmplb.rabbitmq.controller;

import com.cmmplb.rabbitmq.constants.RabbitMqConstants;
import com.cmmplb.core.constants.StringConstants;
import com.cmmplb.rabbitmq.service.ProducerService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author penglibo
 * @date 2021-05-26 16:04:02
 * @since jdk 1.8
 * 学习RabbitMQ五种模式
 */

@Api(tags = "RabbitMQ五种模式演示")
@ApiSupport(order = 1, author = StringConstants.AUTHOR)
@RestController
@RequestMapping("/rabbitmq")
public class RabbitMQController {

    @Autowired
    private ProducerService producerService;

    @ApiOperation("简单队列模式-一个生产者对应一个消费者")
    @ApiOperationSupport(order = 1)
    @GetMapping("/simple/queue")
    public void send2SimpleQueue() {
        System.out.println("生产消息：简单队列模式-一个生产者对应一个消费者");
        String message = "simple queue message";
        producerService.send2SimpleQueue(message);
    }

    @ApiOperation("Work模式-一个生产者对应多个消费者")
    @ApiOperationSupport(order = 2)
    @GetMapping("/work/queue")
    public void send2WorkQueue() {
        System.out.println("生产消息：Work模式-一个生产者对应多个消费者");
        String message = "work queue message - ";
        for (int i = 0; i < 5; i++) {
            producerService.send2WorkQueue(message + i);
        }
    }

    @ApiOperation("订阅模型-Fanout-一条消息被多个消费者消费")
    @ApiOperationSupport(order = 3)
    @GetMapping("/fanout/queue")
    public void send2FanoutQueue() {
        System.out.println("生产消息：订阅模型-Fanout-一条消息被多个消费者消费");
        String message = "fanout queue message - ";
        producerService.send2FanoutQueue(message);
    }

    @ApiOperation("订阅模型-Direct-根据Routing Key接收到消息")
    @ApiOperationSupport(order = 4)
    @GetMapping("/direct/queue")
    public void send2DirectQueue() {
        System.out.println("生产消息：订阅模型-Direct-根据Routing Key接收到消息");
        String message = "direct queue message";
        producerService.send2DirectQueue(RabbitMqConstants.ONE_DIRECT_ROUTING_KEY, message);
        producerService.send2DirectQueue(RabbitMqConstants.TWO_DIRECT_ROUTING_KEY, message);
        producerService.send2DirectQueue(RabbitMqConstants.THREE_DIRECT_ROUTING_KEY, message);
    }

    @ApiOperation("订阅模型-Topic-根据RoutingKey路由消息-同时匹配通配符")
    @ApiOperationSupport(order = 5)
    @GetMapping("/topic/queue")
    public void send2TopicQueue() {
        System.out.println("生产消息：订阅模型-Topic-根据RoutingKey路由消息-同时匹配通配符接收到消息");
        String message = "topic queue message";
        producerService.send2TopicQueue("m.a.m", message); // *.a.*
        producerService.send2TopicQueue("m.m.b", message); // *.*.b  #表示0个或若干个关键字，*表示一个关键字
        producerService.send2TopicQueue("c.c.c", message); // c.#
    }

    @ApiOperation("死信队列")
    @ApiOperationSupport(order = 6)
    @GetMapping("/dead/queue")
    public void send2DeadQueue() {
        System.out.println("生产消息：" +
                "两种方式：1、可以发送到有消费者的队列，拒绝消费; 2、发送到没有消费者的队列，设置队列过期时间。");
        String message = "dead queue message";
        producerService.send2DeadQueue("common.123", message); // *.a.*
    }

}
