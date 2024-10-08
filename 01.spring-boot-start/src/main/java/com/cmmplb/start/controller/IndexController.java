package com.cmmplb.start.controller;

import com.alibaba.fastjson.JSON;
import com.cmmplb.start.domain.Start;
import io.github.cmmplb.core.result.Result;
import io.github.cmmplb.core.result.ResultUtil;
import io.github.cmmplb.core.utils.ServletUtil;
import io.github.cmmplb.core.utils.SpringUtil;
import io.github.cmmplb.core.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PathVariable;
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

    @RequestMapping("/api")
    public Result<String> index(String name) {
        log.info("接收请求:{}", ServletUtil.getHeader("name"));
        String res = "hello spring boot " + (StringUtil.isEmpty(name) ? "" : name);
        return ResultUtil.success(res);
    }

    @RequestMapping("/api/{name}")
    public Result<String> id(@PathVariable(value = "name") String name) {
        String res = "hello spring boot " + (StringUtil.isEmpty(name) ? "" : name);
        log.info("接收api请求:{}", name);
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
        Environment bean = SpringUtil.getBean(Environment.class);
        String name = bean.getProperty("name");
        String age = bean.getProperty("age");
        return ResultUtil.success(new Start(name, age));
    }


}
