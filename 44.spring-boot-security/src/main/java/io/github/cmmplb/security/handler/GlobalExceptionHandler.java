package io.github.cmmplb.security.handler;


import io.github.cmmplb.core.result.HttpCodeEnum;
import io.github.cmmplb.core.result.Result;
import io.github.cmmplb.core.result.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
        // 处理权限异常
        if ((e instanceof AccessDeniedException)) {
            log.error(e.getMessage(), e);
            return ResultUtil.custom(HttpCodeEnum.FORBIDDEN);
        }
        return super.exceptionHandler(e);
    }
}

