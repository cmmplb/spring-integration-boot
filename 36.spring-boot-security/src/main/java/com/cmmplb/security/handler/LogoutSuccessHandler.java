package com.cmmplb.security.handler;

import io.github.cmmplb.core.utils.SpringApplicationUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author penglibo
 * @date 2021-09-19 23:20:24
 * @since jdk 1.8
 * 退出登录处理
 */

public class LogoutSuccessHandler implements org.springframework.security.web.authentication.logout.LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        // httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        // httpServletResponse.setContentType("application/json;charset=utf-8");
        // httpServletResponse.getWriter().write("退出成功，请重新登录");
        httpServletResponse.sendRedirect(SpringApplicationUtil.path); // 退出之后跳转到登录页
    }
}
