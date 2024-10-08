package com.cmmplb.dynamic.datasource;

import io.github.cmmplb.core.utils.SpringApplicationUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author penglibo
 * @date 2021-08-03 09:22:34
 * @since jdk 1.8
 */

@SpringBootApplication// (exclude = DruidDataSourceAutoConfigure.class) // 排除原生Druid的快速配置类。-动态数据源v3.3.3及以上版本不用排除了。
public class DynamicDatasourceApplication {

    public static void main(String[] args) {
        SpringApplicationUtil.run(DynamicDatasourceApplication.class, args);
    }

}
