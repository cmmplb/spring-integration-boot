package com.cmmplb.netty.config;

import com.cmmplb.netty.properties.PortProperties;
import com.cmmplb.netty.socket.server.SocketServer;
import com.cmmplb.netty.websocket.server.WebSocketServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author penglibo
 * @date 2021-07-10 10:58:49
 * @since jdk 1.8
 */

@Component
public class NettyServerConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(NettyServerConfiguration.class);

    @Autowired
    private PortProperties portProperties;

    @PostConstruct
    public void init() {
        // new SocketServer(portProperties.getSocket()).start();
        logger.info("启动socket服务器");
        // ... 多个启动
        logger.info("启动webSocket服务器");
        new WebSocketServer(portProperties.getWebSocket()).start(); // websocket使用spring容器启动
    }
}
