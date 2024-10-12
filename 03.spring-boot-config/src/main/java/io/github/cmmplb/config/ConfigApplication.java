package io.github.cmmplb.config;

import io.github.cmmplb.core.utils.SpringApplicationUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author penglibo
 * @date 2021-03-29 14:26:40
 */

@SpringBootApplication
public class ConfigApplication {

    public static void main(String[] args) {
        SpringApplicationUtil.run(ConfigApplication.class, args);
    }

    // 使用@ConfigurationProperties注解为第三方bean进行属性绑定, 注意前缀是全小写的datasource
    // @Bean
    // @ConfigurationProperties(prefix = "datasource")
    // public DruidDataSource datasource(){
    //     DruidDataSource ds = new DruidDataSource();
    //     return ds;
    // }
}