package io.github.cmmplb.security.configuration;

import io.github.cmmplb.security.filter.AuthenticationMobileLoginFilter;
import io.github.cmmplb.security.handler.AuthenticationFailureHandler;
import io.github.cmmplb.security.handler.AuthenticationSuccessHandler;
import io.github.cmmplb.security.mobile.AuthenticationMobileLoginProvider;
import io.github.cmmplb.security.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author penglibo
 * @date 2024-09-10 09:48:31
 * @since jdk 1.8
 * 手机号登录配置
 */
public class AuthenticationMobileLoginConfiguration extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final UserService userService;

    public AuthenticationMobileLoginConfiguration(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void configure(HttpSecurity http) {
        AuthenticationMobileLoginFilter mobileLoginFilter = new AuthenticationMobileLoginFilter();
        mobileLoginFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        mobileLoginFilter.setAuthenticationSuccessHandler(new AuthenticationSuccessHandler());
        mobileLoginFilter.setAuthenticationFailureHandler(new AuthenticationFailureHandler());
        http.authenticationProvider(new AuthenticationMobileLoginProvider(userService));
        http.addFilterAfter(mobileLoginFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
