package com.cmmplb.start.controller;

import com.cmmplb.core.result.Result;
import com.cmmplb.core.result.ResultUtil;
import com.cmmplb.core.utils.StringUtil;
import com.cmmplb.start.domain.Start;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author penglibo
 * @date 2021-09-24 17:43:08
 * @since jdk 1.8
 */
@RestController
public class IndexController {

    @RequestMapping("/")
    public Result<String> index(String name) {
        String res = "hello spring boot " + (StringUtil.isEmpty(name) ? "" : name);
        return ResultUtil.success(res);
    }

    @RequestMapping("/start")
    public Result<Start> start() {
        return ResultUtil.success(new Start("张三",15));
    }


}
