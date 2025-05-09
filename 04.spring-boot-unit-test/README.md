# Spring-Boot-快速开始

运行控制台打印警告： UT026010: Buffer pool was not set on WebSocketDeploymentInfo, the default pool will be used

没有为 WebSocketDeploymentInfo 设置 Buffer pool，将会使用默认值。

**解决方式：**

1. ‌排除 undertow-websockets-jsr 依赖

````xml

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-undertow</artifactId>
    <exclusions>
        <exclusion>
            <groupId>io.undertow</groupId>
            <artifactId>undertow-websockets-jsr</artifactId>
        </exclusion>
    </exclusions>
</dependency>
````

2. 通过编程式配置 Buffer pool 参数

````java

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
````