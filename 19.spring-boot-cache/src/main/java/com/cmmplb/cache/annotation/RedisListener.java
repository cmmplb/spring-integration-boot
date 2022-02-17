package com.cmmplb.cache.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @author penglibo
 * @date 2021-09-30 14:58:26
 * @since jdk 1.8
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface RedisListener {

    String value() default "channel";

    @AliasFor("value")
    String channel() default "channel";
}
