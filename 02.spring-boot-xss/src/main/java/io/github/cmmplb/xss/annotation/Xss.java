package io.github.cmmplb.xss.annotation;

import io.github.cmmplb.xss.validator.XssValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author penglibo
 * @date 2025-06-06 15:44:35
 * @since jdk 1.8
 */

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = XssValidator.class)
public @interface Xss {

    String message() default "输入包含XSS风险内容";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
