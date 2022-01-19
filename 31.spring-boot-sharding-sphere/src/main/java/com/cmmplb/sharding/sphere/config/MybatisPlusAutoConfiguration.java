package com.cmmplb.sharding.sphere.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author penglibo
 * @date 2021-04-10 17:48:33
 */

@Configuration
public class MybatisPlusAutoConfiguration implements WebMvcConfigurer {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    /**
     * 序列化枚举值为数据库存储值
     * 一、重写toString方法
     * 二、注解处理
     * @return Jackson2ObjectMapperBuilderCustomizer
     * private final int code;
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer customizer() {
        // 这里用的是重写toString
        /*
         * 或者:
         * ObjectMapper objectMapper = new ObjectMapper();
         * objectMapper.configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
         * 两种方式任选其一,然后在枚举中复写toString方法即可
         */
        return builder -> builder.featuresToEnable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
    }
}
