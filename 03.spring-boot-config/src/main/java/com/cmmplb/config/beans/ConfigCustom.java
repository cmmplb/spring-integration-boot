package com.cmmplb.config.beans;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author penglibo
 * @date 2021-03-29 14:48:11
 * 自定义配置文件
 */

@Data
@Component // 如果不想放入容器, 就在引用的地方添加@EnableConfigurationProperties(ConfigCustom.class)
@ConfigurationProperties(prefix = "custom") // 去除配置文件的前缀
@PropertySource("classpath:static/custom.properties")
public class ConfigCustom {

    private String name;

    private String version;
}
