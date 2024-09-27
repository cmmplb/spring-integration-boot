package com.cmmplb.security.filter;

import com.cmmplb.core.utils.SpringApplicationUtil;
import com.cmmplb.core.utils.StringUtil;
import com.cmmplb.security.handler.AuthenticationFailureHandler;
import com.cmmplb.security.handler.exception.ValidateCodeException;
import com.cmmplb.security.utils.CaptchaUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @author penglibo
 * @date 2024-09-10 09:03:15
 * @since jdk 1.8
 * 图形验证码过滤
 */

@Slf4j
public class ValidateCodeFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, @NonNull HttpServletResponse httpServletResponse, @NonNull FilterChain filterChain) throws ServletException, IOException {
        if (StringUtil.equalsIgnoreCase(SpringApplicationUtil.path + "/login", httpServletRequest.getRequestURI())
                && StringUtil.equalsIgnoreCase(httpServletRequest.getMethod(), HttpMethod.POST.name())) {
            try {
                validateCode(httpServletRequest);
            } catch (ValidateCodeException e) {
                new AuthenticationFailureHandler().onAuthenticationFailure(httpServletRequest, httpServletResponse, e);
                return;
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private void validateCode(HttpServletRequest httpServletRequest) {
        String uuid = httpServletRequest.getParameter("uuid");
        String graphCode = httpServletRequest.getParameter("graphCode");
        if (StringUtil.isBlank(graphCode)) {
            throw new ValidateCodeException("图形验证码不能为空！");
        }
        if (!CaptchaUtil.validate(uuid, graphCode)) {
            throw new ValidateCodeException("图形验证码不正确！");
        }
        // 校验成功删除
        CaptchaUtil.delete(uuid);
    }

}
