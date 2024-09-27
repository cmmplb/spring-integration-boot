package com.cmmplb.security.configuration;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author penglibo
 * @date 2021-04-10 17:48:33
 */

@Configuration
@MapperScan(basePackages = {"com.cmmplb.security.dao"})
public class MybatisPlusAutoConfiguration {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        /*
         * -分页拦截器- 如果是不同类型的库, 请不要指定DbType, 其会自动判断.
         * -对于单一数据库类型来说,都建议配置该值,避免每次分页都去抓取数据库类型
         * -新的分页插件,一缓和二缓遵循mybatis的规则,需要设置 MybatisConfiguration#useDeprecatedExecutor = false
         *  避免缓存出现问题(该属性会在旧插件移除后一同移除)
         */
        // interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));

        /*
         * 防止全表更新与删除
         */
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());

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
