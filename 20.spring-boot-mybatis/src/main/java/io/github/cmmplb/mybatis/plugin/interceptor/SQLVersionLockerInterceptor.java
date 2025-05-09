package io.github.cmmplb.mybatis.plugin.interceptor;

/**
 * @author penglibo
 * @date 2021-08-18 09:10:54
 * @since jdk 1.8
 * 乐观锁：数据版本插件
 */

import io.github.cmmplb.mybatis.handler.exception.LockerException;
import io.github.cmmplb.mybatis.plugin.propertise.LockerProperties;
import io.github.cmmplb.core.constants.StringConstant;
import io.github.cmmplb.core.utils.PatternUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeException;
import org.apache.ibatis.type.TypeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;
import java.util.regex.Pattern;

@Slf4j
@Component
@Intercepts({
        @Signature(type = StatementHandler.class, method = StringConstant.PREPARE, args = {Connection.class, Integer.class}),
        @Signature(type = ParameterHandler.class, method = StringConstant.SET_PARAMETERS, args = {PreparedStatement.class}),
        @Signature(type = Executor.class, method = StringConstant.UPDATE, args = {MappedStatement.class, Object.class})
})
@EnableConfigurationProperties(LockerProperties.class)
public class SQLVersionLockerInterceptor implements Interceptor {

    @Autowired
    private LockerProperties lockerProperties;

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public Object intercept(Invocation invocation) throws Throwable {
        String interceptMethod = invocation.getMethod().getName();
        if (StringConstant.PREPARE.equals(interceptMethod)) {

            StatementHandler routingHandler = (StatementHandler) processTarget(invocation.getTarget());
            MetaObject routingMeta = SystemMetaObject.forObject(routingHandler);
            MetaObject mo = routingMeta.metaObjectForProperty("delegate");
            String originalSql = (String) mo.getValue("boundSql.sql");
            boolean locker = resolve(mo, lockerProperties.getColumn());
            if (!locker)
                return invocation.proceed();

            String builder = originalSql + " AND " + lockerProperties.getColumn() + " = ?";
            mo.setValue("boundSql.sql", builder);

        } else if (StringConstant.SET_PARAMETERS.equals(interceptMethod)) {

            ParameterHandler handler = (ParameterHandler) processTarget(invocation.getTarget());
            MetaObject mo = SystemMetaObject.forObject(handler);

            boolean locker = resolve(mo, lockerProperties.getColumn());
            if (!locker)
                return invocation.proceed();

            BoundSql boundSql = (BoundSql) mo.getValue("boundSql");
            Object parameterObject = boundSql.getParameterObject();
            if (parameterObject instanceof MapperMethod.ParamMap<?>) {
                MapperMethod.ParamMap<?> paramMap = (MapperMethod.ParamMap<?>) parameterObject;
                if (!paramMap.containsKey(lockerProperties.getColumn())) {
                    throw new TypeException("所有原始类型参数必须添加MyBatis @Param 注解");
                }
            }

            Configuration configuration = ((MappedStatement) mo.getValue("mappedStatement")).getConfiguration();
            MetaObject pm = configuration.newMetaObject(parameterObject);
            Object value = pm.getValue(lockerProperties.getProperty());
            ParameterMapping versionMapping = new ParameterMapping.Builder(configuration, lockerProperties.getProperty(), Object.class).build();
            TypeHandler typeHandler = versionMapping.getTypeHandler();
            JdbcType jdbcType = versionMapping.getJdbcType();

            if (value == null && jdbcType == null) {
                jdbcType = configuration.getJdbcTypeForNull();
            }

            int versionLocation = boundSql.getParameterMappings().size() + 1;
            try {
                PreparedStatement ps = (PreparedStatement) invocation.getArgs()[0];
                typeHandler.setParameter(ps, versionLocation, value, jdbcType);
            } catch (TypeException | SQLException e) {
                throw new TypeException("设置参数“版本”失败, 原因： " + e, e);
            }

            if (!Objects.equals(Objects.requireNonNull(value).getClass(), Long.class) && Objects.equals(value.getClass(), long.class) && log.isDebugEnabled()) {
                log.error("属性类型错误, 版本属性的类型必须是Long或long");
            }

            // increase version
            if (value instanceof Integer) {
                pm.setValue(lockerProperties.getProperty(), Integer.parseInt(String.valueOf(value)) + 1);
            } else {
                pm.setValue(lockerProperties.getProperty(), Long.parseLong(String.valueOf(value)) + 1);
            }

        } else if ("update".equals(interceptMethod)) {
            MappedStatement ms = (MappedStatement) invocation.getArgs()[0];
            SqlCommandType sct = ms.getSqlCommandType();
            if (sct.equals(SqlCommandType.UPDATE)) {
                int result = (int) invocation.proceed();
                Object param = invocation.getArgs()[1];
                BoundSql boundSql = ms.getBoundSql(param);
                String sql = boundSql.getSql();
                String paramJson = param.toString();
                if (result == 0) {
                    // "\\s*|\t|\r|\n"
                    throw new LockerException("[触发乐观锁, 更新失败], 失败SQL:" + PatternUtil.replaceStrBlank(sql) + ", 参数: " + paramJson);
                }
                return result;
            }
        }
        return invocation.proceed();
    }

    /**
     * Plugin.wrap 方法会自动判断拦截器的签名和被拦截对象的接口是否匹配, 只有匹配的情况下才会使用动态代理拦截目标对象.
     * @param target 被拦截的对象
     * @return 代理对象
     */
    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    /**
     * 设置参数
     */
    @Override
    public void setProperties(Properties properties) {

    }

    public static boolean resolve(MetaObject mo, String versionColumn) {
        String originalSql = (String) mo.getValue("boundSql.sql");
        MappedStatement ms = (MappedStatement) mo.getValue("mappedStatement");
        if (Objects.equals(ms.getSqlCommandType(), SqlCommandType.UPDATE)) {
            // sql中包含version = ?
            return Pattern.matches("[\\s\\S]*?" + versionColumn + "[\\s\\S]*?=[\\s\\S]*?\\?[\\s\\S]*?", originalSql.toLowerCase());
        }
        return false;
    }

    public static Object processTarget(Object target) {
        if (Proxy.isProxyClass(target.getClass())) {
            MetaObject mo = SystemMetaObject.forObject(target);
            return processTarget(mo.getValue("h.target"));
        }

        // 插件中必须保持结果对象是 StatementHandler 或 ParameterHandler
        if (!(target instanceof StatementHandler) && !(target instanceof ParameterHandler)) {
            throw new LockerException("插件初始化失败");
        }
        return target;
    }
}
