package com.cmmplb.log.aspect;

import com.cmmplb.core.threads.ThreadContext;
import com.cmmplb.core.utils.IpUtil;
import com.cmmplb.core.utils.ServletUtil;
import com.cmmplb.core.utils.SpringUtil;
import com.cmmplb.log.annotations.SysLog;
import com.cmmplb.log.dao.LogDao;
import com.cmmplb.log.entity.Log;
import com.cmmplb.log.event.SysLogEvent;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.Objects;

/**
 * @author penglibo
 * @date 2021-04-14 14:51:49
 * @AspectJ支持的5中通知：
 * —@Before：前置通知在方法执行前执行->JoinPoint joinPoint
 * —@After：后置通知，在方法执行后执行->JoinPoint joinPoint
 * —@AfterReturning：返回通知，在方法返回结果之后执行->JoinPoint joinPoint,Object result
 * —@AfterThrowing：异常通知，在方法抛出异常后执行->JoinPoint joinPoint,Exception ex
 * —@Around：环绕通知，围绕着方法执行->ProceedingJoinPoint proceedingJoinPoint
 */

@Aspect
@Component
public class LogAspect {

    @Autowired
    private LogDao logDao;

    @Pointcut("@annotation(com.cmmplb.log.annotations.SysLog)")
    public void pointcut() {
    }

    /**
     * 后置通知
     * @param joinPoint
     * @return
     */
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        // 保存日志
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Log log = new Log();
        SysLog logAnnotation = method.getAnnotation(SysLog.class);
        if (logAnnotation != null) {
            // 注解上的描述
            log.setOperation(logAnnotation.value());
        }
        // 请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        log.setMethod(className + "." + methodName + "()");
        // 请求的方法参数值
        Object[] args = joinPoint.getArgs();
        // 请求的方法参数名称
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
        String[] paramNames = u.getParameterNames(method);
        if (args != null && paramNames != null) {
            StringBuilder params = new StringBuilder();
            for (int i = 0; i < args.length; i++) {
                params.append(paramNames[i]).append(": ").append(args[i]);
            }
            log.setParams(params.toString());
        }
        // 设置IP地址
        log.setIp(IpUtil.getIpAddress(ServletUtil.getRequest()));
        // 模拟一个用户名
        log.setUsername("cmmplb");
        log.setCreateTime(new Date());
        log.setType((byte) 0);
        long beginTime = System.currentTimeMillis();
        // 执行方法
        Object result = null;
        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            log.setType((byte) 1);
            log.setExcCause(e.getClass().toString());
            log.setExcDesc(e.getMessage());
            log.setExcLocation(e.getStackTrace()[0].toString());
            throw e;
        } finally {
            // 执行时长(毫秒)
            long time = System.currentTimeMillis() - beginTime;
            log.setTime((int) time);
            // 自定义异步线程添加
            ThreadContext.executeTask(new Runnable() {
                @Override
                public void run() {
                    // 保存系统日志
                    logDao.saveLog(log);
                }
            });
            // 使用事件发布
            SpringUtil.publishEvent(new SysLogEvent(log));
        }
        return result;
    }
}
