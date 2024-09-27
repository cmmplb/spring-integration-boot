package com.cmmplb.mybatis.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Arrays;
import java.util.List;

/**
 * @author penglibo
 * @date 2023-03-29 11:22:25
 * @since jdk 1.8
 */

@Data
@ConfigurationProperties(prefix = TransactionProperties.PREFIX)
public class TransactionProperties {

    /**
     * 是否启用,默认false
     */
    private Boolean enabled = false;

    /**
     * 切入点表达式
     */
    private String expression = "execution(* com.momo.*.service..*.*(..))";

    /**
     * 事务属性只读权限扫描
     */
    private List<String> readOnlyList = Arrays.asList("find*", "get*", "load*", "list*", "select*", "check*", "query*");

    /**
     * 事务属性默认权限扫描
     */
    private List<String> defaultList = Arrays.asList("insert*", "delete*", "update*", "*");

    public static final String PREFIX = "transaction";

    public static final String ENABLED = "enabled";
}