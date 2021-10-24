package com.cmmplb.config.beans;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author penglibo
 * @date 2021-03-29 15:22:22
 */

@Data
@Component
@ConfigurationProperties(prefix = "maven.pom")
public class Pom {

    private String name;

    private String version;
}
