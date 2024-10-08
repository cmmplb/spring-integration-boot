package com.cmmplb.devtools.controller;

import io.github.cmmplb.core.result.Result;
import io.github.cmmplb.core.result.ResultUtil;
import io.github.cmmplb.core.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author penglibo
 * @date 2024-05-07 17:01:07
 * @since jdk 1.8
 */

@Slf4j
@RestController
public class IndexController {

    @RequestMapping("/devtools")
    public Result<String> index(String name) {

        String res = "hello spring boot" + (StringUtil.isEmpty(name) ? "" : name);
        return ResultUtil.success(res);
    }
}

