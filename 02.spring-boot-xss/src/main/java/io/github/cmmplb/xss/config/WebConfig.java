package io.github.cmmplb.xss.config;

import io.github.cmmplb.xss.converter.XSSMappingJackson2HttpMessageConverter;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author penglibo
 * @date 2025-06-09 09:27:48
 * @since jdk 1.8
 */

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // 自定义 json 消息解析器
    @Bean
    public HttpMessageConverters xssHttpMessageConverters() {
        return new HttpMessageConverters(new XSSMappingJackson2HttpMessageConverter());
    }
}