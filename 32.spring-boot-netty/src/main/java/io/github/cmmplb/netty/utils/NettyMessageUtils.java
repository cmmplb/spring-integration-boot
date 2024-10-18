package io.github.cmmplb.netty.utils;


import io.github.cmmplb.netty.socket.entity.NettyMessage;
import io.github.cmmplb.netty.socket.handler.ServerHandler;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author penglibo
 * @date 2021-07-02 16:39:05
 * @since jdk 1.8
 * 发送消息到设备工具类
 */

public class NettyMessageUtils {

    private static final Logger logger = LoggerFactory.getLogger(NettyMessageUtils.class);

    /**
     * @param message 消息内容
     * @param sns     设备code集合
     */
    public static void sendMessage(NettyMessage message, List<String> sns) {
        ConcurrentHashMap<String, ChannelHandlerContext> deviceMap = ServerHandler.deviceMap;
        for (String key : deviceMap.keySet()) {
            if (sns.contains(key)) {
                ChannelHandlerContext ctx = deviceMap.get(key);
                // 发送消息（编码器处理）
                logger.info("发送消息（编码器处理）");
                try {
                    ctx.writeAndFlush(message).sync();
                } catch (Exception e) {
                    logger.error("服务端发送消息失败", e);
                }
            }
        }
    }
}
