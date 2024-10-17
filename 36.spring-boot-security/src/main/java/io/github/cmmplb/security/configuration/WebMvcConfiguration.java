package io.github.cmmplb.security.configuration;

import io.github.cmmplb.security.configuration.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author penglibo
 * @date 2024-09-02 11:15:55
 * @since jdk 1.8
 */

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(SecurityProperties.class)
public class WebMvcConfiguration implements WebMvcConfigurer {

    // 延迟加载, 解决循环依赖
    @Lazy
    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 配置页面请求路径
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 首页路径映射
        registry.addViewController("/").setViewName("index");
        // 手机号登录
        registry.addViewController("/mobile/login").setViewName("mobile-login");
        // 账号密码登录
        registry.addViewController("/login").setViewName("login");
    }

}
