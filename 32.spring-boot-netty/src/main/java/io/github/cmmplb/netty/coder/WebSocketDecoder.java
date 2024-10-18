package io.github.cmmplb.netty.coder;

import com.google.common.primitives.Bytes;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author penglibo
 * @date 2021-06-29 17:58:45
 * @since jdk 1.8
 * 解码器
 */

public class WebSocketDecoder extends ByteToMessageDecoder {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketDecoder.class);

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> out) throws Exception {
        logger.info("解码");
        try {
            List<Byte> byteList = new ArrayList<>();
            while (buf.isReadable()) {
                byteList.add(buf.readByte());
            }
            String message = new String(Bytes.toArray(byteList), StandardCharsets.UTF_8);
            //System.out.println("解码的数据:" + message);
            // 添加数据
            out.add(message);
        } catch (Exception e) {
            logger.error("解码异常");
            ctx.close();
        }
    }
}
