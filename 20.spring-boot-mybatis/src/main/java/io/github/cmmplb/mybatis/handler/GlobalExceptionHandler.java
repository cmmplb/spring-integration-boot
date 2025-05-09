package io.github.cmmplb.mybatis.handler;


import io.github.cmmplb.core.result.HttpCodeEnum;
import io.github.cmmplb.core.result.Result;
import io.github.cmmplb.core.result.ResultUtil;
import io.github.cmmplb.mybatis.handler.exception.LockerException;
import io.github.cmmplb.mybatis.handler.exception.RetryException;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.MyBatisSystemException;
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
        // 乐观锁异常重试失败异常
        if ((e instanceof RetryException)) {
            return ResultUtil.custom(HttpCodeEnum.RETRY_ERROR);
        }
        if ((e instanceof MyBatisSystemException)) {
            if (e.getCause().getCause() instanceof LockerException) {
                log.error(e.getMessage(), e);
                return ResultUtil.custom(HttpCodeEnum.LOCKER_ERROR);
            }
        }
        return super.exceptionHandler(e);
    }
}

