package io.github.cmmplb.rocketmq.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author penglibo
 * @date 2021-09-26 09:26:24
 * @since jdk 1.8
 */

@Controller
public class IndexController {

    @GetMapping("/")
    public String index() {
        return "index";
    }
}
