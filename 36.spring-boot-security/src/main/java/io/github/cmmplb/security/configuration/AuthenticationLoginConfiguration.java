package io.github.cmmplb.security.configuration;

import io.github.cmmplb.security.filter.AuthenticationLoginFilter;
import io.github.cmmplb.security.handler.AuthenticationFailureHandler;
import io.github.cmmplb.security.handler.AuthenticationSuccessHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author penglibo
 * @date 2024-09-04 14:06:14
 * @since jdk 1.8
 */

public class AuthenticationLoginConfiguration extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final PasswordEncoder passwordEncoder;

    private final UserDetailsService userDetailsService;

    public AuthenticationLoginConfiguration(PasswordEncoder passwordEncoder,UserDetailsService userDetailsService){
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    private final AuthenticationLoginFilter authenticationLoginFilter = new AuthenticationLoginFilter();

    @Override
    public void init(HttpSecurity http) throws Exception {
        http.setSharedObject(AuthenticationLoginFilter.class, this.authenticationLoginFilter);
    }

    @Override
    public void configure(HttpSecurity http) {
        authenticationLoginFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        authenticationLoginFilter.setAuthenticationSuccessHandler(new AuthenticationSuccessHandler());
        authenticationLoginFilter.setAuthenticationFailureHandler(new AuthenticationFailureHandler());
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        http.authenticationProvider(daoAuthenticationProvider);
        http.addFilterBefore(authenticationLoginFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
