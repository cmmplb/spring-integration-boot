package io.github.cmmplb.cache.controller;

import com.alibaba.fastjson.JSON;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.github.cmmplb.cache.domain.entity.User;
import io.github.cmmplb.cache.service.UserService;
import io.github.cmmplb.cache.service.impl.RedisMessageListenerImpl;
import io.github.cmmplb.cache.utils.RedisUtil;
import io.github.cmmplb.core.beans.DataMap;
import io.github.cmmplb.core.constants.StringConstant;
import io.github.cmmplb.core.result.Result;
import io.github.cmmplb.core.result.ResultUtil;
import io.github.cmmplb.core.utils.DisplayUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.ehcache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ExecutionException;

/**
 * @author penglibo
 * @date 2021-09-13 11:50:10
 * @since jdk 1.8
 */

@Slf4j
@Tag(name = "缓存管理")
// @ApiSupport > @ApiSort > @Api  -  排序的规则是倒序
@ApiSort(1)
// 作者,方法名上ApiOperationSupport.author没有则取类名声明的作者
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
    private org.ehcache.CacheManager ehCacheManager;

    @Autowired
    private com.github.benmanes.caffeine.cache.Cache<String, String> caffeineCache;

    @Autowired
    private UserService userService;

    @Autowired
    private RedisMessageListenerImpl redisMessageService;

    @Operation(summary = "使用cache2k存取", description = "使用cache2k存取")
    @ApiOperationSupport(order = 1)
    @RequestMapping(value = "/cache2k", method = RequestMethod.GET)
    public Result<User> getInfoById() {
        String userInfo = cache2k.get("userInfo");
        log.info("userInfo:{}", userInfo);
        if (null == userInfo) {
            userInfo = JSON.toJSONString(userService.getInfoById(1L));
            cache2k.put("userInfo", userInfo);
        }
        return ResultUtil.success(JSON.parseObject(userInfo, User.class));
    }

    @Operation(summary = "使用guava存取")
    @ApiOperationSupport(order = 2)
    @RequestMapping(value = "/guava", method = RequestMethod.GET)
    public Result<User> guava() throws ExecutionException {
        String userInfo = guavaCache.getIfPresent("userInfo");
        log.info("userInfo:{}", userInfo);
        if (null == userInfo) {
            userInfo = JSON.toJSONString(userService.getInfoById(1L));
            guavaCache.put("userInfo", userInfo);
        }
        return ResultUtil.success(JSON.parseObject(userInfo, User.class));
    }

    @Operation(summary = "使用caffeine存取")
    @ApiOperationSupport(order = 3)
    @RequestMapping(value = "/caffeine", method = RequestMethod.GET)
    public Result<User> caffeine() {
        String userInfo = caffeineCache.getIfPresent("userInfo");
        log.info("userInfo:{}", userInfo);
        if (null == userInfo) {
            userInfo = JSON.toJSONString(userService.getInfoById(1L));
            caffeineCache.put("userInfo", userInfo);
        }
        return ResultUtil.success(JSON.parseObject(userInfo, User.class));
    }

    @Operation(summary = "使用redis存取")
    @ApiOperationSupport(order = 4)
    @RequestMapping(value = "/redis", method = RequestMethod.GET)
    public Result<User> save() {
        Object userInfo = redisUtil.get("userInfo");
        log.info("userInfo:{}", userInfo);
        if (null == userInfo) {
            userInfo = JSON.toJSONString(userService.getInfoById(1L));
            redisUtil.set("userInfo", userInfo);
        }
        return ResultUtil.success(JSON.parseObject(userInfo.toString(), User.class));
    }

    @Operation(summary = "使用ehCache3存取")
    @GetMapping(value = "/ehCache3")
    public Result<User> ehCache3() {
        Cache<String, String> cache = ehCacheManager.getCache("otherCache", String.class, String.class);
        String userInfo = cache.get("userInfo");
        log.info("userInfo:{}", userInfo);
        if (null == userInfo) {
            userInfo = JSON.toJSONString(userService.getInfoById(1L));
            cache.put("userInfo", userInfo);
        }
        return ResultUtil.success(JSON.parseObject(userInfo, User.class));
    }

    @Operation(summary = "发送消息")
    @ApiOperationSupport(order = 5)
    @RequestMapping(value = "/redis/send", method = RequestMethod.POST)
    public Result<Boolean> send() {
        return ResultUtil.success(redisMessageService.sendMessage());
    }

    private static Long count = 200L;

    @GetMapping("/zSet")
    public Map<String, Object> jackpot() {
        // 参与抽奖人数-自增 每次0-3
        Random rand = new Random();
        count = count + rand.nextInt(4);
        // 返回手机号列表-10个
        Set<String> phones = getPhones(10);
        // 设置手机号5分钟缓存处理
        if (!redisUtil.hasKey("phones")) {
            phones.forEach(phone -> {
                redisUtil.zAdd("phones", phone, System.currentTimeMillis());

            });
            redisUtil.expire("phones", 60 * 60 * 24 * 30);
        } else {
            redisUtil.zAdd("phones", DisplayUtil.displayMobile(getTel()), System.currentTimeMillis());
            Set<Object> sets = redisUtil.zrevrange("phones", 0, 9);
            if (!CollectionUtils.isEmpty(sets)) {
                phones = new HashSet<>();
                for (Object set : sets) {
                    phones.add(set.toString());
                }
            }
        }
        return new DataMap<String, Object>().set("count", count++).set("phones", phones);
    }

    public static Set<String> getPhones(int number) {
        Set<String> phones = new HashSet<>();
        for (int i = 0; i < number; i++) {
            phones.add(DisplayUtil.displayMobile(getTel()));
        }
        if (phones.size() < number) {
            for (int i = 0; i < number - phones.size(); i++) {
                phones.add(DisplayUtil.displayMobile(getTel()));
            }
        }
        return phones;
    }

    /**
     * 返回手机号码
     */
    private static String[] telFirst = "134,135,136,137,138,139,150,151,152,157,158,159,130,131,132,155,156,133,153".split(",");


    public static String getTel() {
        int index = getNum(0, telFirst.length - 1);
        String first = telFirst[index];
        String second = String.valueOf(getNum(1, 888) + 10000).substring(1);
        String third = String.valueOf(getNum(1, 9100) + 10000).substring(1);
        return first + second + third;
    }

    public static int getNum(int start, int end) {
        return (int) (Math.random() * (end - start + 1) + start);
    }

}
