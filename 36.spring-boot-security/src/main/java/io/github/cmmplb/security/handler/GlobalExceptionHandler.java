package io.github.cmmplb.security.handler;


import io.github.cmmplb.core.result.HttpCodeEnum;
import io.github.cmmplb.core.result.Result;
import io.github.cmmplb.security.handler.exception.MobileNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
// import org.springframework.security.authorization.AuthorizationDeniedException;

/**
 * @author plb
 * @date 2020/6/12 9:58
 * 全局异常捕获
 */

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler<T> extends io.github.cmmplb.core.handler.GlobalExceptionHandler<T> {

    @Override
    public Result<?> exceptionHandler(Exception e) {
        // 用户名不存在
        if ((e instanceof BadCredentialsException)) {
            return getResult(HttpCodeEnum.BAD_CREDENTIALS);
        }
        // 手机号不存在
        if (e instanceof MobileNotFoundException) {
            return getResult(HttpCodeEnum.MOBILE_NOT_FOUND);
        }
        // 处理权限异常
        // if ((e instanceof AuthorizationDeniedException)) {
        //     return getResult(HttpCodeEnum.FORBIDDEN);
        // }
        // 处理权限异常
        // if ((e instanceof AccessDeniedException)) {
        //     log.error(e.getMessage(), e);
        //     return ResultUtil.custom(HttpCodeEnum.FORBIDDEN);
        // }
        return super.exceptionHandler(e);
    }
}

