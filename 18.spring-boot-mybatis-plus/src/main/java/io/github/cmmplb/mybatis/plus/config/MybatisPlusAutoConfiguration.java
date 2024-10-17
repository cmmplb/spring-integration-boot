package io.github.cmmplb.mybatis.plus.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.TableNameHandler;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.*;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.github.cmmplb.mybatis.plus.config.resolver.SqlFilterArgumentResolver;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.HashMap;
import java.util.List;

/**
 * @author penglibo
 * @date 2021-04-10 17:48:33
 */

@Configuration
@MapperScan(basePackages = {"io.github.cmmplb.mybatis.plus.dao"})
public class MybatisPlusAutoConfiguration implements WebMvcConfigurer {

    /**
     * SQL 过滤器避免SQL 注入
     * @param argumentResolvers arg
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new SqlFilterArgumentResolver());
    }

    /**
     * mybatis-plus分页插件 v-3.4.0以上版本使用
     * 该插件是核心插件,目前代理了 Executor#query 和 Executor#update 和 StatementHandler#prepare 方法
     * 目前已有的功能:
     * -自动分页: PaginationInnerInterceptor
     * -多租户: TenantLineInnerInterceptor
     * -动态表名: DynamicTableNameInnerInterceptor
     * -乐观锁: OptimisticLockerInnerInterceptor
     * -sql性能规范: IllegalSQLInnerInterceptor
     * -防止全表更新与删除: BlockAttackInnerInterceptor
     * <p>
     * 注意:
     * 使用多个功能需要注意顺序关系,建议使用如下顺序
     * -多租户,动态表名
     * -分页,乐观锁
     * -sql性能规范,防止全表更新与删除
     * -总结: 对sql进行单次改造的优先放入,不对sql进行改造的最后放入
     * @return MybatisPlusInterceptor
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

        /*
         * 多租户
         * -如果用了分页插件注意先 add TenantLineInnerInterceptor 再 add PaginationInnerInterceptor
         * -用了分页插件必须设置 MybatisConfiguration#useDeprecatedExecutor = false
         */
        interceptor.addInnerInterceptor(new TenantLineInnerInterceptor(new TenantLineHandler() {
            @Override
            public Expression getTenantId() {
                return new LongValue(1);
            }

            // 这是 default 方法,默认返回 false 表示所有表都需要拼多租户条件
            @Override
            public boolean ignoreTable(String tableName) {
                return !"user".equalsIgnoreCase(tableName);
            }
        }));

        /*
         * 动态表名
         */
        DynamicTableNameInnerInterceptor dynamicTableNameInnerInterceptor = new DynamicTableNameInnerInterceptor();
        // 低版本配置
        HashMap<String, TableNameHandler> map = new HashMap<String, TableNameHandler>(2) {
            {
                /*添加表名规则*/
                /*put("user", (sql, tableName) -> {
                    String year = "_2018";
                    int random = new Random().nextInt(10);
                    if (random % 2 == 1) {
                        year = "_2019";
                    }
                    return tableName + year;
                });*/
            }
        };
        // 高版本配置
        TableNameHandler tableNameHandler = new TableNameHandler() {
            @Override
            public String dynamicTableName(String sql, String tableName) {
                /*添加表名规则*/
                // 这里可以更新tableName
                return tableName;
            }
        };
        dynamicTableNameInnerInterceptor.setTableNameHandler(tableNameHandler);
        interceptor.addInnerInterceptor(dynamicTableNameInnerInterceptor);

        /*
         * -分页拦截器- 如果是不同类型的库, 请不要指定DbType, 其会自动判断.
         * -对于单一数据库类型来说,都建议配置该值,避免每次分页都去抓取数据库类型
         * -新的分页插件,一缓和二缓遵循mybatis的规则,需要设置 MybatisConfiguration#useDeprecatedExecutor = false
         *  避免缓存出现问题(该属性会在旧插件移除后一同移除)
         */
        // interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));

        /*
         * 乐观锁插件-在实体类的字段上加上@Version注解
         * -支持的数据类型只有:int,Integer,long,Long,Date,Timestamp,LocalDateTime
         * -整数类型下 newVersion = oldVersion + 1
         * -newVersion 会回写到 entity 中
         * -仅支持 updateById(id) 与 update(entity, wrapper) 方法
         * -在 update(entity, wrapper) 方法下, wrapper 不能复用!!!
         */
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());

        /*
         * 防止全表更新与删除
         */
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());

        return interceptor;
    }

    /**
     * 序列化枚举值为数据库存储值
     * 一、重写toString方法
     * 二、注解处理
     * @return Jackson2ObjectMapperBuilderCustomizer
     * private final int code;
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer customizer() {
        // 这里用的是重写toString
        /*
         * 或者:
         * ObjectMapper objectMapper = new ObjectMapper();
         * objectMapper.configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
         * 两种方式任选其一,然后在枚举中复写toString方法即可
         */
        return builder -> builder.featuresToEnable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
    }
}
