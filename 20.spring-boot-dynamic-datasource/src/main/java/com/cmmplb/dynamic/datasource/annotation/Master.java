package com.cmmplb.dynamic.datasource.annotation;

import com.baomidou.dynamic.datasource.annotation.DS;

import java.lang.annotation.*;

/**
 * @author penglibo
 * @date 2021-08-06 09:51:11
 * @since jdk 1.8
 * 自定义数据源注解--建议从3.2.1版本开始使用自定义注解-组件自带了@Master和@Slave注解. 
 */

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@DS("master") // 自定义一个master库的注解
public @interface  Master {


}
