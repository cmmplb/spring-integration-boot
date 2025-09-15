package io.github.cmmplb.xss.controller;

import io.github.cmmplb.core.result.Result;
import io.github.cmmplb.core.result.ResultUtil;
import io.github.cmmplb.xss.dto.ContentDTO;
import io.github.cmmplb.xss.dto.UserLoginDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author penglibo
 * @date 2021-09-10 17:38:33
 * @since jdk 1.8
 */

@Slf4j
@RestController
@RequestMapping("/xss")
public class XssController {

    @GetMapping("/params")
    public Result<String> params(@Valid UserLoginDTO dto) {
        log.info("输入内容: {}", dto.toString());
        return ResultUtil.success(dto.toString());
    }

    @PostMapping("/json")
    public Result<String> json(@RequestBody @Validated UserLoginDTO dto) {
        log.info("输入内容: {}", dto.toString());
        return ResultUtil.success(dto.toString());
    }

    @PostMapping("/comments")
    public Result<String> comments(@RequestBody /*@Validated*/ ContentDTO dto) {
        log.info("输入内容: {}", dto.getContent());
        return ResultUtil.success(dto.getContent());
    }

    @GetMapping("/cookie")
    public Result<String> cookie(@RequestParam(value = "cookie") String cookie) {
        log.info("输入内容: {}", cookie);
        return ResultUtil.success(cookie);
    }
}
