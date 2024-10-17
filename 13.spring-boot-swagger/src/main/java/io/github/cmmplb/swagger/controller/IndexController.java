package io.github.cmmplb.swagger.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author penglibo
 * @date 2021-09-24 17:42:33
 * @since jdk 1.8
 */

@Controller
public class IndexController {

    @RequestMapping("/")
    public String index() {
        return "index";
    }
}
