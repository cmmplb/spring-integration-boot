package com.cmmplb.mybatis.aspect;

import com.cmmplb.core.exception.LockerException;
import com.cmmplb.core.exception.RetryException;
import com.cmmplb.mybatis.plugin.propertise.LockerProperties;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author penglibo
 * @date 2021-08-20 15:51:47
 * @since jdk 1.8
 * 重试切面
 */

@Slf4j
@Aspect
@Component
@Order(1)
@EnableConfigurationProperties(LockerProperties.class)
public class RetryAspect {

    @Autowired
    private LockerProperties lockerProperties;

    @Pointcut("@annotation(com.cmmplb.mybatis.annotations.Retry)")
//    @Pointcut("execution(public * com.cmmplb.mybatis..*(..))")
    public void pointcut() {
    }

    /**
     * 环绕通知
     * @param joinPoint j
     * @return Object
     * @throws Throwable t
     */
    @Around("pointcut()")
    @Transactional(rollbackFor = Exception.class)
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        int count = 0;
        do {
            count++;
            try {
                return joinPoint.proceed();
            } catch (MyBatisSystemException ex) {
                if (ex.getCause().getCause() instanceof LockerException) {
                    if (count > lockerProperties.getRetryNumber()) {
                        throw new RetryException("重试超过设定次数");
                    } else {
                        Thread.sleep(500); // 等待0.5秒
                        log.info("触发乐观锁异常,{}次重试...", count);
                    }
                }
            }
        } while (count <= lockerProperties.getRetryNumber());
        return null;
    }
}
