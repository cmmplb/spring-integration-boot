package com.cmmplb.websocket.controller;

import com.cmmplb.websocket.config.properties.ServerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author plb
 * @createdate 2021-01-06
 */

@RestController
@RequestMapping("/log")
public class LogController {

    @Autowired
    private ServerProperties serverProperties;

    @GetMapping("/index")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("public/log.html");
        modelAndView.addObject("logo", "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2610641288,2499424050&fm=26&gp=0.jpg");
        modelAndView.addObject("port", serverProperties.getPort());
        modelAndView.addObject("contextPath", serverProperties.getContextPath());
        return modelAndView;
    }
}
