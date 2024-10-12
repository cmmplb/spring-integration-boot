package com.cmmplb.netty.socket.client;

import com.cmmplb.netty.coder.WebSocketDecoder;
import com.cmmplb.netty.coder.WebSocketEncoder;
import com.cmmplb.netty.socket.handler.ClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.TimeUnit;

/**
 * @author penglibo
 * @date 2021-07-17 17:49:50
 * @since jdk 1.8
 */

public class TcpClient extends Thread {

    private String serverIp;

    private int serverPort;

    public static void main(String[] args) throws InterruptedException {
        new TcpClient("127.0.0.1", 8778).init();
        // new TcpClient("192.168.110.102", 8899).init();
    }

    public void init() {
        TcpClient tcpClient = this;
        final ClientHandler handler = new ClientHandler(tcpClient);
        EventLoopGroup worker = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(worker);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel sc) throws Exception {
                sc.pipeline().addLast(new WebSocketEncoder());
                sc.pipeline().addLast(new WebSocketDecoder());
                sc.pipeline().addLast(handler); //绑定消息处理类
            }
        });
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
        // 绑定监听器--实现断线重连
        bootstrap.remoteAddress(serverIp, serverPort);
        bootstrap.connect().addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (!channelFuture.isSuccess()) {
                    System.out.println("接近开关服务器连接失败, 正在重试");
                    final EventLoop loop = channelFuture.channel().eventLoop();
                    loop.schedule(new Runnable() {
                        @Override
                        public void run() {
                            tcpClient.init();
                        }
                    }, 5L, TimeUnit.SECONDS); // 每5秒重连一次
                }
            }
        });

    }

    public TcpClient(String serverIp, int serverPort) {
        this.serverIp = serverIp;
        this.serverPort = serverPort;
    }

    @Override
    public void run() {
        init();
    }
}
