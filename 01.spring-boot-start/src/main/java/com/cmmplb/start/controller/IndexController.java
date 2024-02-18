package com.cmmplb.start.controller;

import com.alibaba.fastjson.JSON;
import com.cmmplb.core.result.Result;
import com.cmmplb.core.result.ResultUtil;
import com.cmmplb.core.utils.StringUtil;
import com.cmmplb.start.domain.Start;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author penglibo
 * @date 2021-09-24 17:43:08
 * @since jdk 1.8
 */

@Slf4j
@RestController
public class IndexController {

    @RequestMapping("/")
    public Result<String> index(String name) {
        String res = "hello spring boot " + (StringUtil.isEmpty(name) ? "" : name);
        return ResultUtil.success(res);
    }

    @RequestMapping("/push")
    public Result<Boolean> index(@RequestBody Map<String, Object> map) throws InterruptedException {
        log.info("接收推送:{}", JSON.toJSONString(map));
        // Thread.sleep(1000 * 60 * 5);
        return ResultUtil.success();
    }

    @RequestMapping("/start")
    public Result<Start> start() {
        return ResultUtil.success(new Start("张三", 15));
    }


}
