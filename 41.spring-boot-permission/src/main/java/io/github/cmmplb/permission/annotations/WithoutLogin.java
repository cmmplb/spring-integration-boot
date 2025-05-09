package io.github.cmmplb.permission.annotations;

import java.lang.annotation.*;


/**
 * @author plb
 * 未标记此注解的所有接口调用都需要登录权限
 */

@Target(value = {ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface WithoutLogin {

}
