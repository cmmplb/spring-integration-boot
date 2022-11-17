package com.cmmplb.xss.controller;

import com.cmmplb.core.result.Result;
import com.cmmplb.core.result.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author penglibo
 * @date 2021-09-10 17:38:33
 * @since jdk 1.8
 */

@Slf4j
@RestController
@RequestMapping("/xss")
public class XssController {

    @GetMapping("/test")
    public Result<String> test(@RequestParam("content") String content) {
        log.info("输入内容: {}", content);
        return ResultUtil.success(content);
    }
}
