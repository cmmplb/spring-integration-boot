package com.cmmplb.swagger.configuration;

import com.cmmplb.core.utils.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.config.annotation.*;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author penglibo
 */

@Slf4j
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 解决404报错swagger-ui.html
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 基于thymeleaf映射登录页面
        // registry.addViewController("/doc").setViewName("doc");
    }

    /**
     * 通用拦截器排除swagger设置, 所有拦截器都会自动加swagger相关的资源排除信息
     */
    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) {
        try {
            Field registrationsField = FieldUtils.getField(InterceptorRegistry.class, "registrations", true);
            List<InterceptorRegistration> registrations = ObjectUtil.cast(ReflectionUtils.getField(registrationsField, registry));
            if (registrations != null) {
                for (InterceptorRegistration interceptorRegistration : registrations) {
                    interceptorRegistration
                            .excludePathPatterns("/swagger**/**")
                            .excludePathPatterns("/webjars/**")
                            .excludePathPatterns("/v3/**")
                            .excludePathPatterns("/templates/doc.html");
                }
            }
        } catch (Exception e) {
            log.error("配置swagger异常：", e);
        }
    }
}