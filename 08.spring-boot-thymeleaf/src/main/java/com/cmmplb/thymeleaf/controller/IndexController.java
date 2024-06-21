package com.cmmplb.thymeleaf.controller;

import com.cmmplb.thymeleaf.entity.Account;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

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

    @RequestMapping("/account")
    public String index(Model m) {
        List<Account> list = new ArrayList<Account>();
        list.add(new Account("KangKang", "康康", "e10adc3949ba59abbe56e", "超级管理员", "17777777777"));
        list.add(new Account("Mike", "麦克", "e10adc3949ba59abbe56e", "管理员", "13444444444"));
        list.add(new Account("Jane", "简", "e10adc3949ba59abbe56e", "运维人员", "18666666666"));
        list.add(new Account("Maria", "玛利亚", "e10adc3949ba59abbe56e", "清算人员", "19999999999"));
        m.addAttribute("accountList", list);
        return "account";
    }

    @RequestMapping("/logout")
    public String logout(Model model) {
        // http://localhost/logout
        // https://ldap.crc.com.cn/idp/authCenter/GLO?redirectToLogin=true&redirectToUrl=http://localhost:80/account&entityId=CEST_DICP
        String requestUrl = "https://ldap-uat.crc.com.cn/idp/authCenter/GLO";
        String redirectToUrl = "http://localhost:80/main";
        String entityId = "CEST_DICP";
        String locationUrl = String.format("%s?redirectToLogin=true&redirectToUrl=%s&entityId=%s",
                requestUrl,
                redirectToUrl,
                entityId
        );
        model.addAttribute("locationUrl", locationUrl);
        return "logout";
    }

    @RequestMapping("/main")
    public String mainHandler(Model model) {
        model.addAttribute("locationUrl", "http://localhost:80/account");
        // try {
            // 重定向
            // ServletUtil.getResponse().sendRedirect("https://dicp-uat.crbeverage.com/login");
            // 请求转发
            // request.setAttribute("msg", "登录失败，用户名或密码错误");
            // ServletUtil.getRequest().getRequestDispatcher("login.jsp").forward(ServletUtil.getRequest(), ServletUtil.getResponse());
        // } catch (Exception e) {
        //     throw new RuntimeException(e);
        // }
        return "logout";
    }
}
