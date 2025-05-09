package io.github.cmmplb.shiro.general.handler;

import io.github.cmmplb.core.result.HttpCodeEnum;
import io.github.cmmplb.core.result.Result;
import io.github.cmmplb.core.result.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.session.ExpiredSessionException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.ServletException;

/**
 * @author penglibo
 * @date 2021-09-27 17:45:27
 * @since jdk 1.8
 */

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler<T> extends io.github.cmmplb.core.handler.GlobalExceptionHandler<T> {

    @Override
    public Result<?> exceptionHandler(Exception e) {
        // 认证
        if ((e instanceof UnauthenticatedException)) {
            print(e);
            return ResultUtil.custom(HttpCodeEnum.UNAUTHORIZED);
        }
        // 权限
        if ((e instanceof UnauthorizedException)) {
            print(e);
            return ResultUtil.custom(HttpCodeEnum.FORBIDDEN);
        }
        // 登录过期
        if ((e instanceof ExpiredSessionException)) {
            print(e);
            return ResultUtil.custom(HttpCodeEnum.UNAUTHORIZED);
        }
        if ((e instanceof ServletException)) {
            print(e);
            return ResultUtil.custom(HttpCodeEnum.INTERNAL_SERVER_ERROR);
        }
        return super.exceptionHandler(e);
    }

    public static void print(Exception e) {
        log.info(e.getMessage());
        log.info("{}", e.getStackTrace()[0]);
    }
}
