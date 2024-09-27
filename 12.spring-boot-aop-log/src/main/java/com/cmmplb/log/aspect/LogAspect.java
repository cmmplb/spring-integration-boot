package com.cmmplb.log.aspect;

import cn.hutool.core.util.URLUtil;
import com.cmmplb.core.utils.IpUtil;
import com.cmmplb.core.utils.ServletUtil;
import com.cmmplb.core.utils.SpringUtil;
import com.cmmplb.log.constants.LogConstant;
import com.cmmplb.log.entity.Log;
import com.cmmplb.log.event.LogEvent;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.StandardReflectionParameterNameDiscoverer;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;

/**
 * @author penglibo
 * @date 2021-04-14 14:51:49
 * AspectJ支持的5中通知：
 * —@Before：前置通知在方法执行前执行->JoinPoint joinPoint
 * —@After：后置通知, 在方法执行后执行->JoinPoint joinPoint
 * —@AfterReturning：返回通知, 在方法返回结果之后执行->JoinPoint joinPoint,Object result
 * —@AfterThrowing：异常通知, 在方法抛出异常后执行->JoinPoint joinPoint,Exception ex
 * —@Around：环绕通知, 围绕着方法执行->ProceedingJoinPoint proceedingJoinPoint
 */

@Slf4j
@Aspect
@Component
public class LogAspect {

    // 配置织入点
    @Pointcut("@annotation(com.cmmplb.log.annotations.Log)")
    public void pointcut() {
    }

    /**
     * 环绕通知
     * @param joinPoint j
     * @return Object
     */
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("======记录日志======");
        // 保存日志
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Log log = new Log();
        com.cmmplb.log.annotations.Log logAnnotation = method.getAnnotation(com.cmmplb.log.annotations.Log.class);
        if (logAnnotation != null) {
            // 注解上的描述
            log.setType(logAnnotation.type().getDescription());
            log.setContent(logAnnotation.content());
            log.setBusinessType(logAnnotation.businessType().getType());
        }
        // 设置IP地址
        log.setIp(IpUtil.getIpAddress(ServletUtil.getRequest()));
        log.setUserAgent(ServletUtil.getRequest().getHeader(HttpHeaders.USER_AGENT));
        log.setRequestUri(URLUtil.getPath(ServletUtil.getRequest().getRequestURI()));
        // 请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        log.setMethod(ServletUtil.getRequest().getMethod());
        log.setMethodName(className + "." + methodName + "()");
        // 请求的方法参数值
        Object[] args = joinPoint.getArgs();
        // 请求的方法参数名称
        StandardReflectionParameterNameDiscoverer u = new StandardReflectionParameterNameDiscoverer();
        String[] paramNames = u.getParameterNames(method);
        if (args != null && paramNames != null) {
            StringBuilder params = new StringBuilder();
            for (int i = 0; i < args.length; i++) {
                params.append(paramNames[i]).append(": ").append(args[i]);
            }
            log.setParams(params.toString());
        }
        log.setStatus(LogConstant.LogStatusEnum.NORMAL.getType());
        long beginTime = System.currentTimeMillis();
        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            log.setStatus(LogConstant.LogStatusEnum.ABNORMAL.getType());
            log.setExcCause(e.getClass().toString());
            log.setExcDesc(e.getMessage());
            log.setExcLocation(e.getStackTrace()[0].toString());
            throw e;
        } finally {
            // 执行时长(毫秒)
            long time = System.currentTimeMillis() - beginTime;
            log.setTime(time);
            log.setCreateTime(new Date());
            // 使用事件发布
            SpringUtil.publishEvent(new LogEvent(log));
        }
        return result;
    }
}
