package com.cmmplb.security.config;

import com.cmmplb.security.filter.SmsCodeFilter;
import com.cmmplb.security.filter.ValidateCodeFilter;
import com.cmmplb.security.handler.*;
import com.cmmplb.security.service.UserService;
import com.cmmplb.security.sms.SmsAuthenticationConfig;
import com.cmmplb.security.sms.SmsAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author penglibo
 * @date 2021-09-19 23:20:24
 * @EnableGlobalMethodSecurity(securedEnabled=true) 开启@Secured 注解过滤权限-支持角色级别权限控制
 * @EnableGlobalMethodSecurity(jsr250Enabled=true) 开启@RolesAllowed jsr250注解过滤权限
 * @EnableGlobalMethodSecurity(prePostEnabled=true) 使用EL表达式方法级别的安全性 4个注解可用
 * -@PreAuthorize 在方法调用之前,基于表达式的计算结果来限制对方法的访问
 * -@PostAuthorize 允许方法调用,但是如果表达式计算结果为false,将抛出一个安全性异常
 * -@PostFilter 允许方法调用,但必须按照表达式来过滤方法的结果
 * -@PreFilter 允许方法调用,但必须在进入方法之前过滤输入值
 * see{@link com.cmmplb.security.controller.AnnotationController}
 * @since jdk 1.8
 * todo:new出来的对象使用容器来管理
 */

@EnableWebSecurity // 开启springSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true, securedEnabled = true) // 开启注解权限控制-》
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SmsAuthenticationConfig authenticationConfig;

    /**
     * 添加授权账号
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 动态查询获取账号权限
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable() // 关闭csrf跨域检查
                .authenticationProvider(new SmsAuthenticationProvider(userService)) // 配置手机号验证提供者
                .apply(authenticationConfig) // 将短信验证码认证配置加到 Spring Security 中

                .and() // 并行条件------------------------------------------------------

                .authorizeRequests() // 打开请求认证
                // 几种规则: -/**.html   -/js/*.js   -/js/j?.js
                .antMatchers("/js/**", "/css/**").permitAll() // 放行登陆页、首页和静态资源
                .antMatchers("/doc.html", "/**/webjars/**", "/**/swagger-ui.html**", "/**/swagger-resources/**", /*v2改成v3了*/"/**/v3/api-docs/**").permitAll() // 放行swagger相关资源
                .antMatchers("/resource/**").hasAuthority("resource") // 配置资源管理权限
                .antMatchers("/user/**").hasAuthority("user") // 配置用户管理权限
                // .antMatchers("/user/**").hasRole("user") // 配置用户管理权限 -hasRole("user")等同于hasAuthority("ROLE_user")
                .antMatchers("/basic/**", "/login", "/login/mobile").permitAll() // 基础管理下的请求都放行、账号密码登录、手机号登录
                .anyRequest().authenticated() // 其他请求都需要登录

                .and() // 并行条件------------------------------------------------------

                .exceptionHandling()
                .accessDeniedHandler(new AccessDeniedHandler()) // 权限不足处理

                .and() // 并行条件------------------------------------------------------

                // 添加UsernamePasswordAuthenticationFilter前面
                .addFilterBefore(new ValidateCodeFilter(), UsernamePasswordAuthenticationFilter.class) // 添加图形验证码校验过滤器
                .addFilterBefore(new SmsCodeFilter(), UsernamePasswordAuthenticationFilter.class) // 添加短信验证码校验过滤器
                // .addFilterAfter(new SmsCodeAuthenticationFilter(http), UsernamePasswordAuthenticationFilter.class) // 添加短信认证在之后 - 这个配置有问题，然后在上面apply了com.cmmplb.security.config.WebSecurityConfigurer.authenticationConfig

                .rememberMe()
                .rememberMeParameter("isRemeberMe") // 值可以是on|yew|1|true，就会记住token到cookie
                .userDetailsService(userService)
                .and()
                .formLogin() // 表单登录
                .usernameParameter("username")
                .passwordParameter("password")
                .loginPage("/login") // 配置登录页地址
                .successHandler(new AuthenticationSuccessHandler()) // 处理登录成功
                .defaultSuccessUrl("/") // 登录成功跳转页面
                .failureForwardUrl("/basic/failure") // 登录失败跳转页面
                .failureHandler(new AuthenticationFailureHandler()) // 登陆失败处理的handler

                .and() // 并行条件------------------------------------------------------

                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")  // 退出登录后跳转页面
                .logoutSuccessHandler(new LogoutSuccessHandler()) // 退出登录处理
                .deleteCookies("JSESSIONID")

                .and() // 并行条件------------------------------------------------------

                .sessionManagement() // 添加 Session管理器
                // .invalidSessionUrl("/login/session/invalid") // Session失效后跳转到这个链接
                .invalidSessionUrl("/login") // Session失效后跳转到这个链接
                .maximumSessions(1)// 并发登录限制1
                .maxSessionsPreventsLogin(true)
                .expiredSessionStrategy(new SessionExpiredHandler())
        ;

    }
}
