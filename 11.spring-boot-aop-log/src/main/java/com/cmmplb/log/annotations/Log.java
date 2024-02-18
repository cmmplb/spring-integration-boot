package com.cmmplb.log.annotations;

import com.cmmplb.log.constants.LogConstant;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @author penglibo
 * @date 2021-04-14 14:35:49
 * 日志
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

    /**
     * 操作类型
     */
    @AliasFor("type")
    LogConstant.LogOperationTypeEnum value() default LogConstant.LogOperationTypeEnum.DEFAULT;

    /**
     * 操作内容
     */
    String content() default "";

    /**
     * 操作类型
     */
    @AliasFor("value")
    LogConstant.LogOperationTypeEnum type() default LogConstant.LogOperationTypeEnum.DEFAULT;

    /**
     * 日志业务类型
     */
    LogConstant.LogBusinessTypeEnum businessType() default LogConstant.LogBusinessTypeEnum.DEFAULT;

}
