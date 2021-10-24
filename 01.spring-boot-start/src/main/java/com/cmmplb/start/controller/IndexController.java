package com.cmmplb.start.controller;

import com.cmmplb.core.utils.StringUtils;
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
    public String index(String name) {
        return "hello spring boot " + (StringUtils.isEmpty(name) ? "" : name);
    }
}
