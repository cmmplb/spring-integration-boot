package io.github.cmmplb.netty.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author penglibo
 * @date 2021-08-25 15:32:29
 * @since jdk 1.8
 */

@Data
@Component
@ConfigurationProperties(prefix = "port")
public class PortProperties {

    /**
     * socket端口号
     */
    private int socket;

    /**
     * webSocket端口号
     */
    private int webSocket;
}
