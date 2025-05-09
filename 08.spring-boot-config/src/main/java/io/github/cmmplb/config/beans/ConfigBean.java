package io.github.cmmplb.config.beans;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author penglibo
 * @date 2021-03-29 14:39:56
 */

@Data
@Component // 如果不想放入容器, 就在引用的地方添加@EnableConfigurationProperties(ConfigBean.class)
@ConfigurationProperties(prefix = "bean") // 去前缀
public class ConfigBean {

    private String name;

    private String version;
}
