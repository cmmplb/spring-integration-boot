package com.cmmplb.start.config;

import com.cmmplb.core.constants.StringConstants;
import io.undertow.server.DefaultByteBufferPool;
import io.undertow.websockets.jsr.WebSocketDeploymentInfo;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author penglibo
 * @date 2021-09-24 17:54:24
 * @since jdk 1.8
 */

@Configuration
public class WebServerConfigration {

    /**
     * 解决报警告:UT026010: Buffer pool was not set on WebSocketDeploymentInfo, the default pool will be used
     * @return WebServerFactoryCustomizer
     */
    @Bean
    @SuppressWarnings("rawtypes")
    public WebServerFactoryCustomizer webServerFactoryCustomizer() {
        return (WebServerFactoryCustomizer<UndertowServletWebServerFactory>) factory -> factory.addDeploymentInfoCustomizers(deploymentInfo -> {
            WebSocketDeploymentInfo webSocketDeploymentInfo = new WebSocketDeploymentInfo();
            webSocketDeploymentInfo.setBuffers(new DefaultByteBufferPool(false, 1024));
            deploymentInfo.addServletContextAttribute(StringConstants.IO_UNDERTOW_WEBSOCKETS_JSR_WEBSOCKET_DEPLOYMENT_INFO, webSocketDeploymentInfo);
        });
    }
}
