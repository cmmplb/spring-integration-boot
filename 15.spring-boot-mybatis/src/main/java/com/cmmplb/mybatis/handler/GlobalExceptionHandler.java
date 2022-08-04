package com.cmmplb.mybatis.handler;


import com.cmmplb.core.exception.LockerException;
import com.cmmplb.core.result.HttpCodeEnum;
import com.cmmplb.core.result.Result;
import com.cmmplb.core.result.ResultUtil;
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
public class GlobalExceptionHandler<T> extends com.cmmplb.core.handler.GlobalExceptionHandler<T> {

    @Override
    public Result<?> exceptionHandler(Exception e) {
        // 处理乐观锁异常-后续看是捕获等待重试还是直接抛出异常
        // if ((e instanceof LockerException)) {
        if ((e instanceof MyBatisSystemException)) {
            if (e.getCause().getCause() instanceof LockerException) {
                log.error(e.getMessage(), e);
                return ResultUtil.custom(HttpCodeEnum.LOCKER_ERROR);
            }
        }
        return super.exceptionHandler(e);
    }
}

