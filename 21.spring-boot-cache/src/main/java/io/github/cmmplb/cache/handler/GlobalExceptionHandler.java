package com.cmmplb.cache.handler;

import io.github.cmmplb.core.result.HttpCodeEnum;
import io.github.cmmplb.core.result.Result;
import io.github.cmmplb.core.result.ResultUtil;
import io.lettuce.core.RedisCommandExecutionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.UnknownHostException;

/**
 * @author penglibo
 * @date 2021-12-10 15:39:00
 * @since jdk 1.8
 */

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler<T> extends io.github.cmmplb.core.handler.GlobalExceptionHandler<T> {

    public static final String USERNAME_PASSWORD = "username-password";

    @Override
    public Result<?> exceptionHandler(Exception e) {
        if ((e instanceof RedisConnectionFailureException)) {
            log.error(e.getMessage(), e);
            Throwable cause = e.getCause().getCause().getCause();
            if (cause instanceof UnknownHostException) {
                return ResultUtil.custom(HttpCodeEnum.INVALID_ERROR.getCode(), "请检查redis host");
            }
            if (cause instanceof RedisCommandExecutionException && cause.getMessage().contains(USERNAME_PASSWORD)) {
                return ResultUtil.custom(HttpCodeEnum.INVALID_ERROR.getCode(), "redis无效的用户名密码或用户已禁用");
            }
            return ResultUtil.custom(HttpCodeEnum.REDIS_CONNECTION_ERROR);
        }
        return super.exceptionHandler(e);
    }
}
