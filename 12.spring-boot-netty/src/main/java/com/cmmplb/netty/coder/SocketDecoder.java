package com.cmmplb.netty.coder;

import com.alibaba.fastjson.JSON;
import com.cmmplb.netty.socket.entity.NettyMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author penglibo
 * @date 2021-06-29 17:58:45
 * @since jdk 1.8
 * 解码器
 */

public class SocketDecoder extends ByteToMessageDecoder {

    private static final Logger logger = LoggerFactory.getLogger(SocketDecoder.class);

    // 每个通信包以11单字节+消息体字节，双方必须根据头部数据长度，定义为一包完整的消息包

    // 消息头字节大小：消息标识4字节+版本号1字节+数据类型1字节+数据长度4字节=11字节
    private static final int HEAD_LENGTH = 11;

    private static final int IDENTIFY = 1330010700; // 消息标识

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> out) throws Exception {
        logger.info("解码");
        try {
            if (buf.readableBytes() < HEAD_LENGTH) {//判断是否可读
                logger.info("数据包小于可读");
                return;
            }
            // 标记
            buf.markReaderIndex();
            // 首先读出消息标识
            int identify = buf.readInt();// 读4字节长度数据
            // 数据包头
            if (identify != IDENTIFY) {
                logger.info("数据包头不正确");
                return;
            }
            // 其次是版本号
            byte version = buf.readByte();
            // 数据类型
            byte dataType = buf.readByte();
            // 消息命令
            byte cmd = buf.readByte();
            // 读消息长度
            int dataLength = buf.readInt(); // 读4字节长度数据
            if (dataLength < 0) {
                logger.info("无数据长度");
                ctx.close();
                return;
            }
            if (buf.readableBytes() < dataLength) {//如果可读数据小于数据长度，判断不是一个完整的数据
                // 数据包不完整，等待下一个数据包
                logger.info("数据包不完整，等待下一个数据包");
                buf.resetReaderIndex(); //还原指针位置
                return;
            }

            byte[] req = new byte[dataLength];
            buf.readBytes(req);

            String data = new String(req, StandardCharsets.UTF_8);

            NettyMessage nettyMessage = new NettyMessage();
            nettyMessage.setIdentify(identify);
            nettyMessage.setVersion(version);
            nettyMessage.setDataType(dataType);
            nettyMessage.setCmd(cmd);
            nettyMessage.setLength(dataLength);
            nettyMessage.setData(data);

            logger.info("解码的数据：" + JSON.toJSONString(nettyMessage));

            // 添加数据
            out.add(nettyMessage);

        } catch (Exception e) {
            logger.error("解码异常");
            ctx.close();
        }
    }
}
