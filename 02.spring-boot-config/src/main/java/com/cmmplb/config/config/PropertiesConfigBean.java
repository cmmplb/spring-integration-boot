package com.cmmplb.config.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.Ordered;

/**
 * @author penglibo
 * @date 2021-09-18 16:04:54
 * @since jdk 1.8
 */

@Data
@ConfigurationProperties("properties")
public class PropertiesConfigBean implements PropertiesConfig, Ordered {

    private String name;

    private String version;

    @Override
    public int getOrder() {
        return 0;
    }
}
