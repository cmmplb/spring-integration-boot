package com.cmmplb.cache.controller;

import com.cmmplb.cache.utils.RedisUtil;
import com.cmmplb.core.result.Result;
import com.cmmplb.core.result.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author penglibo
 * @date 2021-09-30 10:18:25
 * @since jdk 1.8
 */

@RestController
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    private RedisUtil redisUtil;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Result<Boolean> save() {
        System.out.println("1..." + redisUtil.get("name"));
        redisUtil.set("name", "cmmplb");
        System.out.println("2..." + redisUtil.get("name"));
        return ResultUtil.success(true);
    }

}
