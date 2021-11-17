package com.cmmplb.velocity.congtroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * @author penglibo
 * @date 2021-11-17 15:32:33
 * @since jdk 1.8
 */

@Controller
public class IndexController {

    @RequestMapping("/")
    public String index(Map map){
        map.put("message", "获取用户信息");
        map.put("name", "张三");
        map.put("age", "24");
        return "index";
    }

}
