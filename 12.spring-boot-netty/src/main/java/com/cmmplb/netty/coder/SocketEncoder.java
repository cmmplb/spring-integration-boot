package com.cmmplb.netty.coder;

import com.alibaba.fastjson.JSON;
import com.cmmplb.netty.socket.entity.NettyMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * @author penglibo
 * @date 2021-06-29 17:58:20
 * @since jdk 1.8
 * 编码器
 */

public class SocketEncoder extends MessageToByteEncoder<NettyMessage> {

    private static final Logger logger = LoggerFactory.getLogger(SocketEncoder.class);

    // 消息头字节大小：消息标识4字节+版本号1字节+数据类型1字节+消息命令1字节+数据长度4字节=11字节

    // 向设备写数据
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, NettyMessage message, ByteBuf byteBuf) throws Exception {
        logger.info("编码");

        // 写入消息标识4字节
        byteBuf.writeInt(message.getIdentify());
        // 写入版本号1字节
        byteBuf.writeByte(message.getVersion());
        // 写入数据类型1字节
        byteBuf.writeByte(message.getDataType());
        // 写入消息命令1字节
        byteBuf.writeByte(message.getCmd());
        // 写入数据长度4字节
        byteBuf.writeInt(message.getLength());
        if (!StringUtils.isEmpty(message.getData())) {
            // 写入数据
            byteBuf.writeBytes(message.getData().getBytes());
        }
        logger.info("编码发送的数据：" + JSON.toJSONString(message));
    }
}
