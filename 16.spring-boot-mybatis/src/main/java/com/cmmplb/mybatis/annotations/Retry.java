package com.cmmplb.mybatis.annotations;

import java.lang.annotation.*;

/**
 * @author penglibo
 * @date 2021-08-20 15:27:50
 * @since jdk 1.8
 * 乐观锁重试
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Retry {
}
