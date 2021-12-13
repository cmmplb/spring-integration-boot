package com.cmmplb.shiro.handler;

import com.cmmplb.core.result.HttpCodeEnum;
import com.cmmplb.core.result.Result;
import com.cmmplb.core.result.ResultUtil;
import lombok.extern.slf4j.Slf4j;
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
public class GlobalExceptionHandler<T> extends com.cmmplb.core.handler.GlobalExceptionHandler<T> {

    @Override
    public Result<?> exceptionHandler(Exception e) {
        // 处理权限异常
        if ((e instanceof UnauthorizedException)) {
            log.error(e.getMessage(), e);
            return ResultUtil.custom(HttpCodeEnum.UNAUTHORIZED);
        }
        if ((e instanceof ExpiredSessionException)) {
            log.error(e.getMessage(), e);
            return ResultUtil.custom(HttpCodeEnum.UNAUTHORIZED);
        }
        if ((e instanceof ServletException)) {
            log.error(e.getMessage(), e);
            return ResultUtil.custom(HttpCodeEnum.INTERNAL_SERVER_ERROR);
        }

        return super.exceptionHandler(e);
    }
}
