package com.cmmplb.dynamic.datasource;

import com.cmmplb.core.utils.SpringApplicationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author penglibo
 * @date 2021-08-03 09:22:34
 * @since jdk 1.8
 */

@SpringBootApplication// (exclude = DruidDataSourceAutoConfigure.class) // 排除原生Druid的快速配置类。-动态数据源v3.3.3及以上版本不用排除了。
public class SpringBootDynamicDatasourceApplication {

    public static void main(String[] args) {
        SpringApplicationUtil.run(SpringBootDynamicDatasourceApplication.class, args);
    }

}
