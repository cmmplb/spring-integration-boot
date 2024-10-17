package io.github.cmmplb.security.configuration;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

/**
 * @author penglibo
 * @date 2024-07-30 16:12:27
 * @since jdk 1.8
 * 开启/关闭注解权限控制, 通过配置开关定义, 这个要单独配置, 不然关闭安全认证后请求接口会被注解权限拦截.
 */

// 开启注解权限控制
@EnableMethodSecurity(prePostEnabled = true, jsr250Enabled = true, securedEnabled = true)
public class MethodSecurityEnabledConfiguration {

}