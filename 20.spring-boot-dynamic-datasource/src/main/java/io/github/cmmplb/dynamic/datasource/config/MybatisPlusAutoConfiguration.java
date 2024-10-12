package com.cmmplb.dynamic.datasource.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.TableNameHandler;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.*;
import com.fasterxml.jackson.databind.SerializationFeature;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

/**
 * @author penglibo
 * @date 2021-04-10 17:48:33
 */

// 开启二级缓存-mybatis-plus版本必须低于2.0.9才可以使用二级缓存
@EnableCaching
@Configuration
@MapperScan(basePackages = {"com.cmmplb.dynamic.datasource.dao"})
public class MybatisPlusAutoConfiguration {

    /**
     * mybatis-plus插件配置
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
        HashMap<String, TableNameHandler> map = new HashMap<String, TableNameHandler>(2) {
            private static final long serialVersionUID = 6367364676663301512L;

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
        dynamicTableNameInnerInterceptor.setTableNameHandlerMap(map);
        interceptor.addInnerInterceptor(dynamicTableNameInnerInterceptor);

        /*
         * -分页拦截器- 如果是不同类型的库, 请不要指定DbType, 其会自动判断. 
         * -对于单一数据库类型来说,都建议配置该值,避免每次分页都去抓取数据库类型
         * -新的分页插件,一缓和二缓遵循mybatis的规则,需要设置 MybatisConfiguration#useDeprecatedExecutor = false
         *  避免缓存出现问题(该属性会在旧插件移除后一同移除)
         */
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        //interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));

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
     * @return
     * @JsonValue 标记响应json值
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
