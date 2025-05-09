package io.github.cmmplb.start.configuration.properties;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author penglibo
 * @date 2025-04-11 14:39:13
 * @since jdk 1.8
 */

@Configuration
@EnableConfigurationProperties(LogbackProperties.class)
public class LogbackConfiguration {


}
