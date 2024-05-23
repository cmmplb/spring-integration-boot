package com.cmmplb.netty.socket.handler.impl;


import com.cmmplb.netty.socket.entity.BusinessEntity;
import com.cmmplb.netty.socket.enums.CmdEnum;
import com.cmmplb.netty.socket.factory.BusinessFactory;
import com.cmmplb.netty.socket.handler.DeviceAbstractHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author penglibo
 * @date 2021-06-30 14:33:21
 * @since jdk 1.8
 * 未知的设备请求
 */

@Component
public class DefaultHandlerImpl extends DeviceAbstractHandler {

    private static final Logger logger = LoggerFactory.getLogger(DefaultHandlerImpl.class);

    @Override
    public BusinessEntity business(Byte reqCmd, String dataJson) {

        logger.info("未知的设备请求");

        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        BusinessFactory.register(CmdEnum.HEARTBEAT.getMessage(), this);
    }
}
