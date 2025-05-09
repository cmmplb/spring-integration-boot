package io.github.cmmplb.logging.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author penglibo
 * @date 2025-04-11 14:36:22
 * @since jdk 1.8
 */

@Data
@ConfigurationProperties(prefix = "logging.logback")
public class LogbackProperties {

    private Boolean enabled = true;
}
