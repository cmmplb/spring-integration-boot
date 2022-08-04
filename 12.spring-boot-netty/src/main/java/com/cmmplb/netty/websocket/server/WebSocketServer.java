package com.cmmplb.netty.websocket.server;

import com.cmmplb.netty.websocket.handler.WebSocketHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author penglibo
 * @date 2021-06-19 14:01:12
 * @since jdk 1.8
 */

@Slf4j
public class WebSocketServer extends Thread {

    private final int port;

    public void init() {
        log.info("正在启动webSocket服务器……");
        NioEventLoopGroup boosGroup = new NioEventLoopGroup();
        NioEventLoopGroup group = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.option(ChannelOption.SO_BACKLOG, 1024);
        serverBootstrap.group(group, boosGroup)
                .channel(NioServerSocketChannel.class)
                .localAddress(port)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        System.out.println("收到新连接:" + socketChannel.localAddress());
                        // 编解码 http 请求
                        socketChannel.pipeline().addLast(new HttpServerCodec());
                        // 写文件内容
                        socketChannel.pipeline().addLast(new ChunkedWriteHandler());
                        // 聚合解码 HttpRequest/HttpContent/LastHttpContent 到 FullHttpRequest
                        // 保证接收的 Http 请求的完整性
                        socketChannel.pipeline().addLast(new HttpObjectAggregator(8192));
                        // 处理其他的 WebSocketFrame
                        socketChannel.pipeline().addLast(new WebSocketServerProtocolHandler("/ws", "WebSocket", true, 65536 * 10));
                        // 这个类的代码是模板代码，最核心的就是ch.pipeline().addLast(new MyWebSocketHandler())，
                        // 可以根据自己的需求配置即可
                        socketChannel.pipeline().addLast(new WebSocketHandler());
                    }
                });
        ChannelFuture sync = null; // 服务器异步创建绑定
        try {
            sync = serverBootstrap.bind(port).sync();
            log.info("启动webSocket服务器启动成功，正在监听端口:" + port);
            sync.channel().closeFuture().sync(); //以异步的方式关闭端口
        } catch (InterruptedException e) {
            log.info("启动出现异常：" + e);
        }
        try {
            group.shutdownGracefully().sync(); // 释放线程池资源
            boosGroup.shutdownGracefully().sync(); // 释放线程池资源
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new WebSocketServer(8002).init();
    }

    public WebSocketServer(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        init();
    }
}
