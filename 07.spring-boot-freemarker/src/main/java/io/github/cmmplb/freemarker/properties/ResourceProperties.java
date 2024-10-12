package io.github.cmmplb.freemarker.properties;

/**
 * @author penglibo
 * @date 2021-08-27 17:50:31
 * @since jdk 1.8
 */

//表示这个类是一个读取配置文件的类

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
@ConfigurationProperties(prefix = "resource")
@PropertySource(value = "classpath:resource.properties")
public class ResourceProperties {

    private String language;
}
