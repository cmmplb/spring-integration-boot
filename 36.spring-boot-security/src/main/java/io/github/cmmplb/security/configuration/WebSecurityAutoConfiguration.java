package io.github.cmmplb.security.configuration;

import io.github.cmmplb.security.configuration.properties.SecurityProperties;
import io.github.cmmplb.security.handler.GlobalExceptionHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author penglibo
 * @date 2024-09-02 11:45:29
 * @since jdk 1.8
 */

@Configuration
public class WebSecurityAutoConfiguration {

    /**
     * 配置全局异常处理器
     */
    @Bean
    public GlobalExceptionHandler<?> globalExceptionHandler() {
        return new GlobalExceptionHandler<>();
    }

    /**
     * 方法权限安全配置
     */
    @Bean
    @ConditionalOnProperty(prefix = SecurityProperties.PREFIX, name = SecurityProperties.ENABLED, havingValue = "true")
    public MethodSecurityEnabledConfiguration methodSecurityEnabledConfiguration() {
        return new MethodSecurityEnabledConfiguration();
    }

    /**
     * 配置密码编码器，这个对象是spring security中用户密码加密的核心对象，它负责用户密码的加密
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
        // return new NoOpPasswordEncoder(); // 不使用加密, 明文对比
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
