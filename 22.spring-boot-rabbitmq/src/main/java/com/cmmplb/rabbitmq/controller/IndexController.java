package com.cmmplb.rabbitmq.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
