package com.cmmplb.security.filter;

import com.cmmplb.core.utils.SpringApplicationUtil;
import com.cmmplb.core.utils.StringUtils;
import com.cmmplb.security.handler.AuthenticationFailureHandler;
import com.cmmplb.security.handler.excetion.ValidateCodeException;
import com.cmmplb.security.utils.CaptchaUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author penglibo
 * @date 2021-08-30 17:35:43
 * @since jdk 1.8
 * 图形验证码过滤
 */

@Slf4j
public class ValidateCodeFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, @NonNull HttpServletResponse httpServletResponse, @NonNull FilterChain filterChain) throws ServletException, IOException {
        log.info("------------doFilterInternal------------");
        if (StringUtils.equalsIgnoreCase(SpringApplicationUtil.path + SmsCodeAuthenticationFilter.LOGIN, httpServletRequest.getRequestURI())
                && StringUtils.equalsIgnoreCase(httpServletRequest.getMethod(), HttpMethod.POST.name())) {
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
        if (StringUtils.isBlank(graphCode)) {
            throw new ValidateCodeException("图形验证码不能为空！");
        }
        if (!CaptchaUtil.validate(uuid, graphCode)) {
            throw new ValidateCodeException("图形验证码不正确！");
        }
        CaptchaUtil.delete(uuid); // 校验成功删除
    }

}
