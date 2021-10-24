package com.cmmplb.cache.controller;

import com.cmmplb.cache.service.impl.RedisMessageServiceImpl;
import com.cmmplb.core.constants.StringConstants;
import com.cmmplb.core.result.Result;
import com.cmmplb.core.result.ResultUtil;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author penglibo
 * @date 2021-09-30 10:18:25
 * @since jdk 1.8
 */

@Api(tags = "redis消息订阅")
@ApiSupport(order = 1, author = StringConstants.AUTHOR)
@RestController
@RequestMapping("/redis/message")
public class RedisMessageController {

    @Autowired
    private RedisMessageServiceImpl redisMessageService;

    @ApiOperation("发送消息")
    @ApiOperationSupport(order = 1)
    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public Result<Boolean> save() {
        redisMessageService.sendMessage("发送消息");
        return ResultUtil.success(true);
    }

}
