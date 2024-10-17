package io.github.cmmplb.start.controller;

import io.github.cmmplb.core.result.Result;
import io.github.cmmplb.core.result.ResultUtil;
import io.github.cmmplb.core.utils.SpringUtil;
import io.github.cmmplb.core.utils.StringUtil;
import io.github.cmmplb.start.domain.Start;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author penglibo
 * @date 2021-09-24 17:43:08
 * @since jdk 1.8
 */

@Slf4j
@RestController
public class IndexController {

    @RequestMapping("/")
    public Result<Start> index() {
        log.info("index");
        Environment bean = SpringUtil.getBean(Environment.class);
        String name = bean.getProperty("name");
        String age = bean.getProperty("age");
        return ResultUtil.success(new Start(name, age));
    }

    @RequestMapping("/api")
    public Result<String> index(String name) {
        String res = "hello spring boot " + (StringUtil.isEmpty(name) ? "" : name);
        return ResultUtil.success(res);
    }

    @RequestMapping("/api/{name}")
    public Result<String> id(@PathVariable(value = "name") String name) {
        String res = "hello spring boot " + (StringUtil.isEmpty(name) ? "" : name);
        log.info("接收api请求:{}", name);
        return ResultUtil.success(res);
    }

    @RequestMapping("/start")
    public String start() {
        log.info("start");
        return "start";
    }
}
