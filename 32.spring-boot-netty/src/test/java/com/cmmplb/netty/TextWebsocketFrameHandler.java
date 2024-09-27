package com.cmmplb.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;

/**
 * TextWebSocketFrame 表示一个文本帧
 */
@Slf4j
public class TextWebsocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        log.info(">>>>>>>>>>>服务端收到消息：{}", msg.text());


        /**
         * 回复消息
         */
        ctx.writeAndFlush(new TextWebSocketFrame("服务器收到了,并返回："+msg.text()));
    }


    /**
     * 当web客户端连接后, 触发方法
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        /**
         * 这个ID是唯一的
         */
        log.info(">>>>>>>>>>>> channelId:{} 连接",ctx.channel().id().asLongText());

        /**
         *  这个ID不是唯一的
         */
        log.info(">>>>>>>>>>>> channelId:{} 连接",ctx.channel().id().asShortText());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        log.info(">>>>>>>>>>>>> channelId:{} 关闭了",ctx.channel().id().asLongText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error(">>>>>>>>发送异常：{}",cause.getMessage());
        ctx.close();
    }
}