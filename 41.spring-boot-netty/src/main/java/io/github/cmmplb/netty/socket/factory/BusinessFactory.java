package io.github.cmmplb.netty.socket.factory;


import io.github.cmmplb.netty.socket.handler.DeviceAbstractHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * @author penglibo
 * @date 2021-06-30 14:08:22
 * @since jdk 1.8
 * 工厂设计模式
 */

public class BusinessFactory {

    private static final Map<String, DeviceAbstractHandler> strategyMap = new HashMap<>();

    public static DeviceAbstractHandler getInvokeStrategy(String message) {
        return strategyMap.get(message);
    }

    public static void register(String message, DeviceAbstractHandler abstractHandler) {
        if (null == message || null == abstractHandler) {
            return;
        }
        strategyMap.put(message, abstractHandler);
    }
}
