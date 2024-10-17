package io.github.cmmplb.security.filter;

import io.github.cmmplb.security.handler.AuthenticationFailureHandler;
import io.github.cmmplb.security.handler.AuthenticationSuccessHandler;
import io.github.cmmplb.security.mobile.AuthenticationMobileLoginToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Optional;

/**
 * @author penglibo
 * @date 2024-09-10 09:56:46
 * @since jdk 1.8
 * 添加手机号登录的地址
 * 参考org.springframework.security.oauth2.provider.client.ClientCredentialsTokenEndpointFilter
 */
public class AuthenticationMobileLoginFilter extends AbstractAuthenticationProcessingFilter {

    public static final String MOBILE_KEY = "mobile";
    // 手机号登录地址
    public static final String MOBILE_LOGIN = "/mobile/login";

    public AuthenticationMobileLoginFilter() {
        super(new AntPathRequestMatcher(MOBILE_LOGIN, HttpMethod.POST.name()));
    }

    // 用有参工造来实例化有问题->AuthenticationManager是null
    public AuthenticationMobileLoginFilter(HttpSecurity http) {
        super(new AntPathRequestMatcher(MOBILE_LOGIN, HttpMethod.POST.name()));
        this.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        this.setAuthenticationSuccessHandler(new AuthenticationSuccessHandler());
        this.setAuthenticationFailureHandler(new AuthenticationFailureHandler());
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (!request.getMethod().equalsIgnoreCase(HttpMethod.POST.name())) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }
        AuthenticationMobileLoginToken authRequest = new AuthenticationMobileLoginToken(Optional.of(request.getParameter(MOBILE_KEY)).orElse("").trim());
        authRequest.setDetails(super.authenticationDetailsSource.buildDetails(request));
        return this.getAuthenticationManager().authenticate(authRequest);
    }
}