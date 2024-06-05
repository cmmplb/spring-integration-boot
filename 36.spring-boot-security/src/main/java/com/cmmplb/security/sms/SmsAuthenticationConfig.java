package com.cmmplb.security.sms;

import com.cmmplb.security.filter.SmsCodeAuthenticationFilter;
import com.cmmplb.security.handler.AuthenticationFailureHandler;
import com.cmmplb.security.handler.AuthenticationSuccessHandler;
import com.cmmplb.security.service.UserService;
import com.cmmplb.security.sms.SmsAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * @author penglibo
 * @date 2021-09-26 17:20:34
 * @since jdk 1.8
 * 短信验证码登录配置
 */

@Component
public class SmsAuthenticationConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private UserService userService;

    @Override
    public void configure(HttpSecurity http) {
        SmsCodeAuthenticationFilter smsAuthenticationFilter = new SmsCodeAuthenticationFilter();
        smsAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        smsAuthenticationFilter.setAuthenticationSuccessHandler(new AuthenticationSuccessHandler());
        smsAuthenticationFilter.setAuthenticationFailureHandler(new AuthenticationFailureHandler());
        http.authenticationProvider(new SmsAuthenticationProvider(userService))
                .addFilterAfter(smsAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}

