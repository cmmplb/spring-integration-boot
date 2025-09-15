package io.github.cmmplb.mybatis.plugin.interceptor;

import io.github.cmmplb.core.constants.StringConstant;
import io.github.cmmplb.core.utils.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.CachingExecutor;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author penglibo
 * @date 2021-08-16 14:55:57
 * @since jdk 1.8
 * SQL查询处理拦截器
 */

@Slf4j
@Component
@Intercepts({@Signature(method = StringConstant.QUERY, type = Executor.class, args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class})})
public class SQLQueryParamInterceptor implements Interceptor {

    @Override
    public Object plugin(Object target) {
        // 调用插件
        if (target instanceof Executor || target instanceof StatementHandler || target instanceof ParameterHandler)
            return Plugin.wrap(target, this);
        return target;
    }

    @Override
    public void setProperties(Properties properties) {
    }

    @Override
    public Object intercept(Invocation invocation) throws Exception {
        Object target = invocation.getTarget();
        log.info("target:" + target);
        // 拦截 Executor 的 update 方法 生成sql前将 tenantId 设置到实体中
        // 该方法写入自己的逻辑
        if (target instanceof StatementHandler) {
            // 拦截 ParameterHandler 的 setParameters 方法 动态设置参数
            log.info("target:StatementHandler");
        }
        if (target instanceof CachingExecutor) {
            // 拦截 Executor 的 createCacheKey 方法, pageHelper插件会拦截 query 方法, 调用此方法, 提前将参数设置到参数集合中
            log.info("target:CachingExecutor");
        }
        Map<String, Object> map = new HashMap<>();
        Object[] args = invocation.getArgs();
        for (Object arg : args) {
            if ((arg instanceof Map)) {
                // map = getObjectToMap(arg); // 用这个转换有问题, 不会将获取到的参数设置到map
                map = ObjectUtil.cast(arg);  // todo:这里有问题, 单个参数无法传递, 需要修改
            }
        }
        // 注入创建人
        map.put("currentUserId", 999);
        // todo:
        args[1] = map;
        return invocation.proceed();
    }

    /**
     * Object转Map
     * @param obj obj
     * @return map
     * @throws IllegalAccessException i
     */
    public static Map<String, Object> getObjectToMap(Object obj) throws IllegalAccessException {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        Class<?> clazz = obj.getClass();
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object value = field.get(obj);
            if (value == null) {
                value = "";
            }
            map.put(fieldName, value);
        }
        return map;
    }

    /**
     * Map转Object
     * @param map       obj
     * @param beanClass bean
     * @return obj
     * @throws Exception e
     */
    public static Object mapToObject(Map<Object, Object> map, Class<?> beanClass) throws Exception {
        if (map == null)
            return null;
        Object obj = beanClass.newInstance();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            int mod = field.getModifiers();
            if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                continue;
            }
            field.setAccessible(true);
            if (map.containsKey(field.getName())) {
                field.set(obj, map.get(field.getName()));
            }
        }
        return obj;
    }
}
