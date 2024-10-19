package io.github.cmmplb.start.config;

import io.github.cmmplb.core.constants.StringConstant;
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
public class WebServerConfiguration {

    /**
     * 警告:UT026010: Buffer pool was not set on WebSocketDeploymentInfo, the default pool will be used
     * 没有为 WebSocketDeploymentInfo 设置 Buffer pool，将会使用默认值。
     * 解决方式：
     * 1. ‌排除undertow-websockets-jsr 依赖
     * 2. 通过编程式配置Buffer pool参数
     * @return WebServerFactoryCustomizer
     */
    @Bean
    public WebServerFactoryCustomizer<UndertowServletWebServerFactory> webServerFactoryCustomizer() {
        return factory -> factory.addDeploymentInfoCustomizers(deploymentInfo -> {
            WebSocketDeploymentInfo webSocketDeploymentInfo = new WebSocketDeploymentInfo();
            webSocketDeploymentInfo.setBuffers(new DefaultByteBufferPool(false, 1024));
            deploymentInfo.addServletContextAttribute(StringConstant.IO_UNDERTOW_WEBSOCKETS_JSR_WEBSOCKET_DEPLOYMENT_INFO, webSocketDeploymentInfo);
        });
    }
}
