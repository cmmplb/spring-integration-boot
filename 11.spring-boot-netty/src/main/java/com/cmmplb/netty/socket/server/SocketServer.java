package com.cmmplb.netty.socket.server;


import com.cmmplb.netty.coder.SocketDecoder;
import com.cmmplb.netty.coder.SocketEncoder;
import com.cmmplb.netty.socket.handler.ServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author penglibo
 * @date 2021-06-28 12:51:47
 * @since jdk 1.8
 */

@Slf4j
public class SocketServer extends Thread {

    private final int port;

    public void init() {
        log.info("正在启动socket服务器……");
        //服务端要建立两个group，一个负责接收客户端的连接，一个负责处理数据传输
        //连接处理group
        NioEventLoopGroup boss = new NioEventLoopGroup();//主线程组
        //事件处理group
        NioEventLoopGroup work = new NioEventLoopGroup();//工作线程组
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();//引导对象
            // 绑定处理group
            bootstrap.group(boss, work);//配置工作线程组
            //保持连接数
            bootstrap.option(ChannelOption.SO_BACKLOG, 1024);//缓冲区
            //有数据立即发送
            bootstrap.childOption(ChannelOption.TCP_NODELAY, true);
            //保持长连接
            bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);//ChannelOption对象设置TCP套接字的参数，非必须步骤

            bootstrap.channel(NioServerSocketChannel.class);//配置为NIO的socket通道

            //处理新连接
            bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                // 增加任务处理
                protected void initChannel(SocketChannel ch) throws Exception {//绑定通道参数
                    ch.pipeline().addLast("logging", new LoggingHandler("DEBUG"));//设置log监听器，并且日志级别为debug，方便观察运行流程
                    //使用了netty自带的编码器和解码器
                    ch.pipeline().addLast(new SocketDecoder());
                    ch.pipeline().addLast(new SocketEncoder());
                    //心跳检测，读超时，写超时，读写超时
                    ch.pipeline().addLast(new IdleStateHandler(30, 0, 0, TimeUnit.SECONDS));
                    ch.pipeline().addLast("handler", new ServerHandler());//业务处理类，最终的消息会在这个handler中进行业务处理
                }
            });

            ChannelFuture future = bootstrap.bind(port).sync();//使用了Future来启动线程，并绑定了端口
            log.info("启动socket服务器启动成功，正在监听端口:" + port);
            future.channel().closeFuture().sync();//以异步的方式关闭端口

        } catch (InterruptedException e) {
            log.info("启动出现异常：" + e);
        } finally {
            work.shutdownGracefully();
            boss.shutdownGracefully();//出现异常后，关闭线程组
            log.info("socket服务器已经关闭");
        }

    }

    public static void main(String[] args) {
        new SocketServer(8001).init();
    }

    public SocketServer(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        init();
    }

}
