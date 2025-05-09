package io.github.cmmplb.config.beans;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author penglibo
 * @date 2024-10-19 14:59:04
 * @since jdk 1.8
 */

@Configuration
// @PropertySource(
//         // 配置文件路径
//         value = "classpath:/config/local/application-custom.yml",
//         // 当配置文件不存在时，是忽略还是报错
//         ignoreResourceNotFound = true,
//         // 配置文件编码
//         encoding = "UTF-8",
//         // 配置文件加载工厂
//         factory = YamlPropertySourceFactory.class
//         // factory = DefaultPropertySourceFactory.class
// )
public class Config {

    // 使用@ConfigurationProperties注解为第三方bean进行属性绑定, 注意前缀是全小写的datasource
    @Bean
    @ConfigurationProperties(prefix = "bean")
    public ConfigBean configBean() {
        return new ConfigBean();
    }
}
