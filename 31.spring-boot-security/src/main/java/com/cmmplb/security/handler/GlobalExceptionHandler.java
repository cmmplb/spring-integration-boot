package com.cmmplb.security.handler;


import com.cmmplb.core.result.HttpCodeEnum;
import com.cmmplb.core.result.Result;
import com.cmmplb.core.result.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

/**
 * @author plb
 * @data 2020/6/12 9:58
 * 全局异常捕获
 */

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler<T> extends com.cmmplb.core.handler.GlobalExceptionHandler<T> {

    @Override
    public Result<?> exceptionHandler(Exception e) {
        // 处理权限异常
        if ((e instanceof AccessDeniedException)) {
            log.error(e.getMessage(), e);
            return ResultUtil.custom(HttpCodeEnum.FORBIDDEN);
        }
        return super.exceptionHandler(e);
    }
}

