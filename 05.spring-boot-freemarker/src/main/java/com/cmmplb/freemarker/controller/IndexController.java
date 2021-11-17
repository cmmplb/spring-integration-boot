package com.cmmplb.freemarker.controller;

import com.cmmplb.freemarker.properties.ResourceProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author penglibo
 * @date 2021-08-27 17:52:06
 * @since jdk 1.8
 */

@Controller
public class IndexController {

    @Autowired
    private ResourceProperties resourceProperties;

    @Autowired
    private Environment environment;

    @GetMapping("/")
    public String hello(ModelMap map) {
        map.addAttribute("resource", resourceProperties);
        map.addAttribute("port", environment.getProperty("server.port"));
        map.addAttribute("path", environment.getProperty("server.servlet.context-path"));
        return "index";
    }

    @RequestMapping(value = "/list")
    public String center(ModelMap map) {
        map.put("users", parseUsers());
        map.put("title", "用户列表");
        return "list";
    }

    private List<Map<String, Object>> parseUsers() {
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", "kevin_" + i);
            map.put("age", 10 + i);
            map.put("phone", "1860291105" + i);
            list.add(map);
        }
        return list;
    }
}
