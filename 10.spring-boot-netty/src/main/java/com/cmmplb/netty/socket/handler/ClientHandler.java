package com.cmmplb.netty.socket.handler;

import com.cmmplb.netty.socket.client.TcpClient;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.EventLoop;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * @author penglibo
 * @date 2021-07-17 17:50:57
 * @since jdk 1.8
 * 5.0:ChannelInboundHandlerAdapter, ChannelOutboundHandlerAdapter, 和 ChannelDuplexHandlerAdapter 弃用了， 被 ChannelHandlerAdapter 取代.
 * 因为现在你无法区分一个 handler是 inbound handler 或者 outbound handler, 所以CombinedChannelDuplexHandler 被 ChannelHandlerAppender取代.
 */
public class ClientHandler extends ChannelHandlerAdapter/*ChannelInboundHandlerAdapter*/ {

    private TcpClient client;

    //有物体接近开关
    private final static String near = "ACED0005740008EFBFBD020101505C";

    public static ChannelGroup channelGroup;

    static {
        channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    }

    public ClientHandler(TcpClient client) {
        this.client = client;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("接近开关与服务端建立连接，通道开启！");
        channelGroup.add(ctx.channel());
        // 通道建立成功，发送消息
        //System.out.println("发送消息");
        //SendMessage.sendMessage("FE 02 00 00 00 01 AD C5");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("接近开关与服务端断开连接，通道关闭！");
        channelGroup.remove(ctx.channel());
        // 实现断线重连
        final EventLoop eventLoop = ctx.channel().eventLoop();
        eventLoop.schedule(new Runnable() {
            @Override
            public void run() {
                client.init();
            }
        }, 5L, TimeUnit.SECONDS); // 每5秒重连一次
        super.channelInactive(ctx);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("接近开关客户端接收服务端发送过来的数据结束之后调用");

        ctx.flush();
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
        System.out.println("接近开关工程出现异常的时候调用");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIP = insocket.getAddress().getHostAddress();
        System.out.println(clientIP);
    }

}
