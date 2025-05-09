package io.github.cmmplb.config.beans;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author penglibo
 * @date 2021-03-29 14:32:24
 * 对应属性配置
 */

@Data
@Component
public class ConfigProperties {

    @Value("${properties.name}")
    private String name;

    @Value("${properties.version}")
    private String version;
}
