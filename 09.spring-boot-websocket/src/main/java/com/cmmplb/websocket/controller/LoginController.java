package com.cmmplb.websocket.controller;

import com.cmmplb.core.result.Result;
import com.cmmplb.core.result.ResultUtil;
import com.cmmplb.core.utils.RandomUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author penglibo
 * @date 2021-05-28 17:01:10
 * @since jdk 1.8
 */

@RestController
@RequestMapping("/login")
public class LoginController {

    private AtomicInteger userId = new AtomicInteger();

    @GetMapping("/index")
    public ModelAndView index() {
        return new ModelAndView("public/login.html");
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request) {
        request.getSession().removeAttribute("username");
        return new ModelAndView("/index.html");
    }

    @PostMapping("/do")
    public Result<Map<String, Object>> doLogin(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        HttpSession session = request.getSession();
        // 模拟数据库，生成一个新用户
        int userId = this.userId.getAndIncrement();
        String username = RandomUtil.getRandomName();
        map.put("userId", userId);
        map.put("username", username);
        map.put("token", session.getId());
        session.setAttribute("userId", userId);
        session.setAttribute("username", username);
        return ResultUtil.success(map);
    }
}
