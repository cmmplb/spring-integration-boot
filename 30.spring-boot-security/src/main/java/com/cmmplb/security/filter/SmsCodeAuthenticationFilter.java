package com.cmmplb.security.filter;

import com.cmmplb.security.handler.AuthenticationFailureHandler;
import com.cmmplb.security.handler.AuthenticationSuccessHandler;
import com.cmmplb.security.sms.SmsAuthenticationToken;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * @author penglibo
 * @date 2021-09-26 17:00:05
 * @since jdk 1.8
 * 添加手机号登录的地址
 * 参考org.springframework.security.oauth2.provider.client.ClientCredentialsTokenEndpointFilter
 */

public class SmsCodeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public static final String MOBILE_KEY = "mobile";
    public static final String LOGIN = "/login"; // 账号密码登录地址
    public static final String LOGIN_mobile = "/login/mobile"; // 手机号登录地址

    public SmsCodeAuthenticationFilter() {
        super(new AntPathRequestMatcher(LOGIN_mobile, HttpMethod.POST.name()));
    }

    // 用有参工造来实例化有问题->AuthenticationManager是null
    public SmsCodeAuthenticationFilter(HttpSecurity http) {
        super(new AntPathRequestMatcher(LOGIN_mobile, HttpMethod.POST.name()));
        this.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        this.setAuthenticationSuccessHandler(new AuthenticationSuccessHandler());
        this.setAuthenticationFailureHandler(new AuthenticationFailureHandler());
    }

    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (!request.getMethod().equalsIgnoreCase(HttpMethod.POST.name())) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }
        SmsAuthenticationToken authRequest = new SmsAuthenticationToken(Optional.of(request.getParameter(MOBILE_KEY)).orElse("").trim());
        authRequest.setDetails(super.authenticationDetailsSource.buildDetails(request));
        return this.getAuthenticationManager().authenticate(authRequest);
    }
}
