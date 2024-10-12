package com.cmmplb.mybatis.plus.plugin.interceptor;


import io.github.cmmplb.core.constants.StringConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.CachingExecutor;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * @author penglibo
 * @date 2021-08-16 17:26:26
 * @since jdk 1.8
 * SQL更新、新增处理拦截器
 */

@Slf4j
@Component
@Intercepts({@Signature(method = StringConstant.UPDATE, type = Executor.class, args = {MappedStatement.class, Object.class})})
public class SQLUpdateParamInterceptor implements Interceptor {


    @SuppressWarnings("unchecked")
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
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
        // todo:这里有问题, 单个参数无法传递, 需要修改
        /*if (null != args && args.length > 1) {
            Object arg = args[1];
            if ((arg instanceof Map)) {
                //noinspection rawtypes
                map = (Map) arg;
                map.put("currentUserId", 999);
                args[1] = map;
            } else {
                map = object2Map(arg);
                map.put("currentUserId", 999);
                Object o = map2Object(map, arg.getClass());
                args[1] = o;
            }
        }*/
        return invocation.proceed();
    }

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

    /**
     * Object转Map
     * @param obj obj
     * @return map
     * @throws IllegalAccessException i
     */
    public static Map<Object, Object> getObjectToMap(Object obj) throws IllegalAccessException {
        Map<Object, Object> map = new LinkedHashMap<Object, Object>();
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

    /**
     * 实体对象转成Map
     * @param obj 实体对象
     * @return Map
     */
    public static Map<String, Object> object2Map(Object obj) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (obj == null) {
            return map;
        }
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                map.put(field.getName(), field.get(obj));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * Map转成实体对象
     * @param map   map实体对象包含属性
     * @param clazz 实体对象类型
     * @return t
     */
    public static <T> T map2Object(Map<String, Object> map, Class<T> clazz) {
        if (map == null) {
            return null;
        }
        T obj = null;
        try {
            obj = clazz.newInstance();

            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                int mod = field.getModifiers();
                if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                    continue;
                }
                field.setAccessible(true);
                String filedTypeName = field.getType().getName();
                if (filedTypeName.equalsIgnoreCase("java.util.date")) {
                    String datetimestamp = String.valueOf(map.get(field.getName()));
                    if (datetimestamp.equalsIgnoreCase("null")) {
                        field.set(obj, null);
                    } else {
                        field.set(obj, new Date(Long.parseLong(datetimestamp)));
                    }
                } else {
                    field.set(obj, map.get(field.getName()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }
}
