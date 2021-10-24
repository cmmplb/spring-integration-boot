package com.cmmplb.xss.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author penglibo
 * @date 2021-09-10 17:38:33
 * @since jdk 1.8
 */

@RestController
@RequestMapping("/xss")
public class XssController {

    @GetMapping("/test")
    public String test(@RequestParam("content") String content) {
        System.out.println(content);
        return content;
    }
}
