package com.cmmplb.swagger.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author penglibo
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 解决404报错swagger-ui.html
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    /**
     * 通用拦截器排除swagger设置，所有拦截器都会自动加swagger相关的资源排除信息
     */
    // @Override
    // public void addInterceptors(InterceptorRegistry registry) {
    //     try {
    //         Field registrationsField = FieldUtils.getField(InterceptorRegistry.class, "registrations", true);
    //         List<InterceptorRegistration> registrations = (List<InterceptorRegistration>) ReflectionUtils.getField(registrationsField, registry);
    //         if (registrations != null) {
    //             for (InterceptorRegistration interceptorRegistration : registrations) {
    //                 interceptorRegistration
    //                         .excludePathPatterns("/swagger**/**")
    //                         .excludePathPatterns("/webjars/**")
    //                         .excludePathPatterns("/v3/**")
    //                         .excludePathPatterns("/doc.html");
    //             }
    //         }
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    // }
}