package com.cmmplb.security.handler;

import io.github.cmmplb.core.utils.SpringApplicationUtil;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author penglibo
 * @date 2021-09-19 23:20:24
 * @since jdk 1.8
 * 登录成功处理
 */

public class AuthenticationSuccessHandler implements org.springframework.security.web.authentication.AuthenticationSuccessHandler {

    // private RequestCache requestCache = new HttpSessionRequestCache();

    // private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    //
    // @Autowired
    // private ObjectMapper mapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        // response.setContentType("application/json;charset=utf-8");
        // response.getWriter().write(mapper.writeValueAsString(authentication));
        // SavedRequest savedRequest = requestCache.getRequest(request, response); // 获取原来点击进来的地址
        // System.out.println(savedRequest.getRedirectUrl());
        // redirectStrategy.sendRedirect(request, response, savedRequest.getRedirectUrl());
        // redirectStrategy.sendRedirect(request, response, "/");
        response.sendRedirect(SpringApplicationUtil.path); // 登录成功跳转到首页
    }
}
