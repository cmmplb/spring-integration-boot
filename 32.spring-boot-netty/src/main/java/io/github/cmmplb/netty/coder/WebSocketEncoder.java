package com.cmmplb.netty.coder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author penglibo
 * @date 2021-06-29 17:58:20
 * @since jdk 1.8
 * 编码器
 */

public class WebSocketEncoder extends MessageToByteEncoder<String> {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketEncoder.class);

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, String message, ByteBuf byteBuf) throws Exception {
        logger.info("编码");
        // 写入数据
        byteBuf.writeBytes(message.getBytes());
        logger.info("编码发送的数据：" + message);
    }
}
