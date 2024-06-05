package com.cmmplb.security.filter;

import com.cmmplb.core.utils.SpringApplicationUtil;
import com.cmmplb.security.handler.AuthenticationFailureHandler;
import com.cmmplb.security.handler.exception.ValidateCodeException;
import com.cmmplb.security.utils.SmsCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author penglibo
 * @date 2021-09-26 14:37:49
 * @since jdk 1.8
 * 短信验证码过滤
 */

@Slf4j
public class SmsCodeFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, @NonNull HttpServletResponse httpServletResponse, @NonNull FilterChain filterChain) throws ServletException, IOException {
        log.info("------------doFilterInternal------------");
        if (StringUtils.equalsIgnoreCase(SpringApplicationUtil.path + SmsCodeAuthenticationFilter.LOGIN_mobile, httpServletRequest.getRequestURI())
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

    private void validateCode(HttpServletRequest httpServletRequest) throws ServletRequestBindingException {
        String mobile = ServletRequestUtils.getStringParameter(httpServletRequest, "mobile");
        String smsCode = ServletRequestUtils.getStringParameter(httpServletRequest, "smsCode");
        if (StringUtils.isBlank(smsCode)) {
            throw new ValidateCodeException("短信验证码不能为空！");
        }
        if (!SmsCodeUtil.validate(mobile, smsCode)) {
            throw new ValidateCodeException("短信验证码不正确！");
        }
        // 删除验证码
        // SmsCodeUtil.delete(mobile);
    }
}
