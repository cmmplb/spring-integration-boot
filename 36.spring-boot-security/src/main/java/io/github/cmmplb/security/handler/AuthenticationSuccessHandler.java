package io.github.cmmplb.security.handler;

import io.github.cmmplb.core.utils.SpringApplicationUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;

import java.io.IOException;

/**
 * @author penglibo
 * @date 2024-09-02 17:34:21
 * @since jdk 1.8
 * 登录成功处理，
 */

@Slf4j
public class AuthenticationSuccessHandler implements org.springframework.security.web.authentication.AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("===登录成功处理===:{}", authentication.getName());
        // 登录成功跳转到首页
        response.sendRedirect(SpringApplicationUtil.path);
    }
}
