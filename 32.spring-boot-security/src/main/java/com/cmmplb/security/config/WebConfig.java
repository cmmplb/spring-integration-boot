package com.cmmplb.security.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author penglibo
 * @date 2021-09-19 22:57:43
 * @since jdk 1.8
 */

@Slf4j
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * 自定义UserDetailsService
     * 如果没有定义，在UserDetailsServiceAutoConfiguration中默认注入一个包含user用户的org.springframework.security.provisioning.InMemoryUserDetailsManager
     * see {@link org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration 52 | 74}
     * 也可以采用修改configure(AuthenticationManagerBuilder auth)方法并注入authenticationManagerBean的方式
     * @return
     */
    /*@Bean // 使用自定义UserDetailsService
    public UserDetailsService userDetailsService() {
        // 当定义用户之后，控制台就不会打印默认的user密码了。
        InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager(
                // 模拟数据库定义三个用户
                User.withUsername("admin").password(passwordEncoder().encode("admin")).authorities("resource", "user").build(),
                User.withUsername("user").password(passwordEncoder().encode("user")).authorities("user").build(),
                User.withUsername("resource").password(passwordEncoder().encode("resource")).authorities("resource").build()
        );
        return userDetailsManager;
        // return new JdbcUserDetailsManager(DataSource dataSource);
    }*/

    /**
     * 配置页面请求路径
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/login").setViewName("login");
    }

    /**
     * 自定义SpringBoot 错误异常
     */
    @Bean
    public ConfigurableServletWebServerFactory webServerFactory() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.addErrorPages(new ErrorPage(HttpStatus.FORBIDDEN, "/basic/unauthorized"));
        return factory;
    }

    /**
     * 密码编码器
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
        // return new NoOpPasswordEncoder(); // 不使用加密，明文对比
        // 使用自定义密码编码器
        /*return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                log.info("rawPassword:{},", rawPassword);
                return MD5Util.encode(rawPassword.toString());
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                log.info("rawPassword:{},encodedPassword:{}", rawPassword, encodedPassword);
                return MD5Util.encode(rawPassword.toString()).equals(encodedPassword);
            }
        };*/
    }

}
