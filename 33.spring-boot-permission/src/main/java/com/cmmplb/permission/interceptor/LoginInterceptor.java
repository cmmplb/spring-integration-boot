// package com.tz.campus.common.interceptor;
//
// import com.alibaba.fastjson.JSON;
// import com.cmmplb.core.exception.CustomException;
// import com.cmmplb.core.result.ResultUtil;
// import com.cmmplb.permission.annotations.WithoutLogin;
// import com.cmmplb.redis.service.RedisService;
// import lombok.extern.slf4j.Slf4j;
// import org.aspectj.lang.JoinPoint;
// import org.aspectj.lang.ProceedingJoinPoint;
// import org.aspectj.lang.annotation.AfterReturning;
// import org.aspectj.lang.annotation.Around;
// import org.aspectj.lang.annotation.Aspect;
// import org.aspectj.lang.annotation.Pointcut;
// import org.aspectj.lang.reflect.MethodSignature;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.core.annotation.Order;
// import org.springframework.stereotype.Component;
// import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
//
// import java.lang.reflect.Method;
// import java.util.Arrays;
// import java.util.Date;
// import java.util.List;
//
// /**
//  * @author plb
//  */
// @Order(1)
// @Aspect
// @Component
// @Slf4j
// public class LoginInterceptor extends HandlerInterceptorAdapter {
//
//     final static private List<String> CLIENT_TYPES = Arrays.asList("pc", "h5", "android", "ios");
//
//     /**
//      * 手机端登录
//      */
//     final static private List<String> MOBILE_CLIENT_TYPES = Arrays.asList("h5", "android", "ios");
//
//     @Autowired
//     private RedisService redisService;
//
//     // com.tz.smart.platform.controller
//     @Pointcut("execution(* com.tz.campus.*.controller..*.*(..))")
//     public void aspect() {
//     }
//
//     @Around("aspect()")
//     public Object process(ProceedingJoinPoint point) throws Throwable {
//
//         RequestContext rc = AppContext.getRequestContext();
//
//         /**
//          * 1. 验证客户端类型是否正确
//          */
//         String clientType = rc.getClientType();
//         if (null == clientType || !CLIENT_TYPES.contains(clientType.toLowerCase())) {
//             log.error("错误的客户端类型：" + clientType);
//             throw new CustomException(HttpCodeEnum.CLIENT_TYPE_ERROR);
//         }
//
//         /**
//          * 2. 通过token获取用户信息, 并将用户信息绑定到当前上下文, 并重设token有效期
//          */
//         String token = rc.getToken();
//         if (null != token) {
//             String tokenUidCacheKey = RedisConstants.TOKEN_UID_CACHE_PREFIX + token;
//             Object uid = redisUtil.get(tokenUidCacheKey);
//             if (null != uid) {
//                 String uidUinfoCacheKey = RedisConstants.UID_UNIFO_CACHE_PREFIX + uid.toString();
//                 Object uinfo = redisUtil.get(uidUinfoCacheKey);
//                 // 异步读取用户多端token
//                 ThreadContext.executeTask(new Runnable() {
//                     @Override
//                     public void run() {
//                         // 取出用户最早存入的token
//                         String tokenOld = (String) redisUtil.rpop(RedisConstants.UID_TOKENS_CACHE_PREFIX + uid);
//                         if (redisUtil.hasKey(RedisConstants.TOKEN_UID_CACHE_PREFIX + tokenOld)) {
//                             redisUtil.lpush(RedisConstants.UID_TOKENS_CACHE_PREFIX + uid, tokenOld);
//                         }
//                     }
//                 });
//                 if (null != uinfo) {
//                     // 解析用户信息
//                     UserBean userBean = JSON.parseObject(uinfo.toString(), UserBean.class);
//                     if (null != userBean) {
//                         if (SpringUtil.isDevOrSit()) {
//                             // 刷新token、用户信息缓存
//                             if (MOBILE_CLIENT_TYPES.contains(rc.getClientType())) {
//                                 redisUtil.expire(tokenUidCacheKey, RedisConstants.TOKEN_H5_EXPIRE_SECONDS);
//                             } else {
//                                 redisUtil.expire(tokenUidCacheKey, RedisConstants.TOKEN_H5_EXPIRE_SECONDS);
//                             }
//                             // 判断用户账号是否禁用
//                             Boolean o = (Boolean) redisUtil.get(RedisConstants.SIGN_O_PREFIX);
//                             if (null != o && o && AppContext.getRequestContext().getClientType().equals(GlobalConstants.CLIENT_TYPE_PC)) {
//                                 // 校验登录日期
//                                 if (ResultUtil.getVerify(JSON.parseObject(null ==
//                                         redisUtil.get(RedisConstants.TOKEN_UID_CACHE_PREFIX + RedisConstants.TOKEN) ? JSON.toJSONString(new Date().getTime()) :
//                                         redisUtil.get(RedisConstants.TOKEN_UID_CACHE_PREFIX + RedisConstants.TOKEN).toString(), Long.class))) {
//                                     // 测试、开发环境设置token时间1个月
//                                     // log.info("--------------------------------当前环境：" + SpringUtil.getCurrent() + "--------------------------------");
//                                     // log.info(("--------------------------------isDev：" + SpringUtil.isDev() + "--------------------------------"));
//                                     // log.info(("--------------------------------isSit：" + SpringUtil.isSit() + "--------------------------------"));
//                                     // log.info(("--------------------------------isProd：" + SpringUtil.isProd() + "--------------------------------"));
//                                     return ResultUtil.success();
//                                 }
//                             }
//
//                         } else {
//                             // 刷新token、用户信息缓存
//                             if (MOBILE_CLIENT_TYPES.contains(rc.getClientType())) {
//                                 redisUtil.expire(tokenUidCacheKey, RedisConstants.TOKEN_H5_EXPIRE_SECONDS);
//                             } else {
//                                 redisUtil.expire(tokenUidCacheKey, RedisConstants.TOKEN_PC_EXPIRE_SECONDS);
//                             }
//                         }
//                         // 绑定当前用户
//                         rc.setUid(userBean.getId());
//                         rc.setCurrentUser(userBean);
//                     }
//                 }
//             }
//         }
//
//         MethodSignature signature = (MethodSignature) point.getSignature();
//         Method method = signature.getMethod();
//
//         /**
//          * 3. 登录权限认证
//          */
//         WithoutLogin withoutLogin = method.getDeclaredAnnotation(WithoutLogin.class);
//
//         Long uid = rc.getUid();
//         if (null == uid && null == withoutLogin) {
//             // 用户未登录, 校验登录权限和接口访问权限
//             throw new CustomException(HttpCodeEnum.USERNAME_OR_PASSWORD_ERR);
//         }
//
//         return point.proceed();
//     }
//
//     @AfterReturning(value = "aspect()", returning = "result")
//     public void doAfter(JoinPoint joinPoint, Object result) {
//
//     }
// }
