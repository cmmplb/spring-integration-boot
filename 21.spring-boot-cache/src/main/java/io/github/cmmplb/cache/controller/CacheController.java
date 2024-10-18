package io.github.cmmplb.cache.controller;

import com.alibaba.fastjson.JSON;
import io.github.cmmplb.cache.domain.entity.User;
import io.github.cmmplb.cache.service.UserService;
import io.github.cmmplb.cache.service.impl.RedisMessageListenerImpl;
import io.github.cmmplb.cache.utils.RedisUtil;
import io.github.cmmplb.core.constants.StringConstant;
import io.github.cmmplb.core.result.Result;
import io.github.cmmplb.core.result.ResultUtil;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

/**
 * @author penglibo
 * @date 2021-09-13 11:50:10
 * @since jdk 1.8
 */

@Api(tags = "缓存管理")
@Slf4j
@ApiSupport(order = 1, author = StringConstant.AUTHOR)
@RestController
@RequestMapping("/cache")
public class CacheController {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private org.cache2k.Cache<String, String> cache2k;

    @Autowired
    private com.google.common.cache.Cache<String, String> guavaCache;

    @Autowired
    private com.github.benmanes.caffeine.cache.Cache<String, String> caffeineCache;

    @Autowired
    private UserService userService;

    @Autowired
    private RedisMessageListenerImpl redisMessageService;

    @ApiOperation("使用cache2k存取")
    @ApiOperationSupport(order = 1)
    @RequestMapping(value = "/cache2k", method = RequestMethod.GET)
    public Result<User> getInfoById() {
        String userInfo = cache2k.get("userInfo");
        log.info("userInfo:{}", userInfo);
        if (null == userInfo) {
            userInfo = JSON.toJSONString(userService.getById(1L));
            cache2k.put("userInfo", userInfo);
        }
        return ResultUtil.success(JSON.parseObject(userInfo, User.class));
    }

    @ApiOperation("使用guava存取")
    @ApiOperationSupport(order = 2)
    @RequestMapping(value = "/guava", method = RequestMethod.GET)
    public Result<User> guava() throws ExecutionException {
        String userInfo = guavaCache.getIfPresent("userInfo");
        log.info("userInfo:{}", userInfo);
        if (null == userInfo) {
            userInfo = JSON.toJSONString(userService.getById(1L));
            guavaCache.put("userInfo", userInfo);
        }
        return ResultUtil.success(JSON.parseObject(userInfo, User.class));
    }

    @ApiOperation("使用caffeine存取")
    @ApiOperationSupport(order = 3)
    @RequestMapping(value = "/caffeine", method = RequestMethod.GET)
    public Result<User> caffeine() {
        String userInfo = caffeineCache.getIfPresent("userInfo");
        log.info("userInfo:{}", userInfo);
        if (null == userInfo) {
            userInfo = JSON.toJSONString(userService.getById(1L));
            caffeineCache.put("userInfo", userInfo);
        }
        return ResultUtil.success(JSON.parseObject(userInfo, User.class));
    }

    @ApiOperation("使用redis存取")
    @ApiOperationSupport(order = 4)
    @RequestMapping(value = "/redis", method = RequestMethod.GET)
    public Result<User> save() {
        Object userInfo = redisUtil.get("userInfo");
        log.info("userInfo:{}", userInfo);
        if (null == userInfo) {
            userInfo = JSON.toJSONString(userService.getById(1L));
            redisUtil.set("userInfo", userInfo);
        }
        return ResultUtil.success(JSON.parseObject(userInfo.toString(), User.class));
    }

    @ApiOperation("发送消息")
    @ApiOperationSupport(order = 5)
    @RequestMapping(value = "/redis/send", method = RequestMethod.POST)
    public Result<Boolean> send() {
        return ResultUtil.success(redisMessageService.sendMessage());
    }
}
