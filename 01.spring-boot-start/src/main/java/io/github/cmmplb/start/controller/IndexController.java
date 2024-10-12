package io.github.cmmplb.start.controller;

import io.github.cmmplb.core.result.Result;
import io.github.cmmplb.core.result.ResultUtil;
import io.github.cmmplb.core.utils.SpringUtil;
import io.github.cmmplb.start.domain.Start;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
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

    @RequestMapping("/start")
    public String start() {
        log.info("start");
        return "start";
    }
}
