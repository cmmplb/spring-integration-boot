package com.cmmplb.dynamic.datasource.config;

import com.baomidou.dynamic.datasource.aop.DynamicDatasourceNamedInterceptor;
import com.baomidou.dynamic.datasource.processor.DsProcessor;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author penglibo
 * @date 2021-08-06 14:20:20
 * @since jdk 1.8
 * 自定义切面方式切换数据源
 * 使用这个方式，原注解方式并不会失效。
 * 注意：不要在同一个切面同时使用注解又使用自定义切面。
 */

@Configuration
public class AspectConfig {

    // 实现com.cmmplb.dynamic.datasource包service下所有类的add和update,delete开头的方法使用master数据源，select使用slave数据源。

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Bean
    public DynamicDatasourceNamedInterceptor dsAdvice(DsProcessor dsProcessor) {
        DynamicDatasourceNamedInterceptor interceptor = new DynamicDatasourceNamedInterceptor(dsProcessor);
        Map<String, String> patternMap = new HashMap<>();
        patternMap.put("select*", "slave_1");
        patternMap.put("get*", "slave_1");
        patternMap.put("add*", "master");
        patternMap.put("update*", "master");
        patternMap.put("delete*", "master");
        interceptor.addPatternMap(patternMap);
        return interceptor;
    }

    @Bean
    public Advisor dsAdviceAdvisor(DynamicDatasourceNamedInterceptor dsAdvice) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution (* com.cmmplb.dynamic.datasource.service.*.*(..))");
        return new DefaultPointcutAdvisor(pointcut, dsAdvice);
    }
}
