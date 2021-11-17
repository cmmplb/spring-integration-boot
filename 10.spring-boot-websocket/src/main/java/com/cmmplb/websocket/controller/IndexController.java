package com.cmmplb.websocket.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author penglibo
 * @date 2021-09-24 17:51:53
 * @since jdk 1.8
 */
@RestController
public class IndexController {

    @GetMapping("/")
    public ModelAndView index() {
        return new ModelAndView("index.html");
    }
}
