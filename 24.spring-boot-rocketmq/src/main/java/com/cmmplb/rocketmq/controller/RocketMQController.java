package com.cmmplb.rocketmq.controller;

import com.cmmplb.core.constants.StringConstant;
import com.cmmplb.rocketmq.service.ProducerService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author penglibo
 * @date 2021-05-26 16:04:02
 * @since jdk 1.8
 * 学习Rocket
 */

@Tag(name = "Rocket演示")
@ApiSupport(order = 1, author = StringConstant.AUTHOR)
@RestController
@RequestMapping("/rocketmq")
public class RocketMQController {

    @Autowired
    private ProducerService producerService;

    @Operation(summary = "发送普通消息")
    @ApiOperationSupport(order = 1)
    @GetMapping("/common")
    public void send2CommonQueue() {
        String msg = "发送普通消息";
        System.out.println(msg);
        producerService.send2CommonQueue(msg);
    }

    @Operation(summary = "发送事务消息")
    @ApiOperationSupport(order = 2)
    @GetMapping("/transaction")
    public void send2TransactionQueue() {
        String msg = "发送事务消息";
        System.out.println(msg);
        producerService.send2TransactionQueue(msg);
    }

}
