package com.cmmplb.rocketmq.controller;

import com.cmmplb.core.constants.StringConstants;
import com.cmmplb.rocketmq.service.ProducerService;
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
 * 学习Rocket
 */

@Api(tags = "Rocket演示")
@ApiSupport(order = 1, author = StringConstants.AUTHOR)
@RestController
@RequestMapping("/rocketmq")
public class RocketMQController {

    @Autowired
    private ProducerService producerService;

    @ApiOperation("发送普通消息")
    @ApiOperationSupport(order = 1)
    @GetMapping("/common")
    public void send2CommonQueue() {
        String msg = "发送普通消息";
        System.out.println(msg);
        producerService.send2CommonQueue(msg);
    }

    @ApiOperation("发送事务消息")
    @ApiOperationSupport(order = 2)
    @GetMapping("/transaction")
    public void send2TransactionQueue() {
        String msg = "发送事务消息";
        System.out.println(msg);
        producerService.send2TransactionQueue(msg);
    }

}
