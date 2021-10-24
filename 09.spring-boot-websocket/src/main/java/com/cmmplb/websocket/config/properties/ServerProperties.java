package com.cmmplb.websocket.config.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author penglibo
 * @date 2021-08-25 14:08:35
 * @since jdk 1.8
 */

@Data
@Component
public class ServerProperties {

    @Value("${server.port}")
    private String port;

    @Value("${server.servlet.context-path}")
    private String contextPath;
}
