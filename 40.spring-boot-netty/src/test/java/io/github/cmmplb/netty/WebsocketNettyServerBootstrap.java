package io.github.cmmplb.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * 服务启动类
 */
public class WebsocketNettyServerBootstrap {

    public static void main(String[] args) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            //创建服务器端的启动对象, 配置参数
            ServerBootstrap bootstrap = new ServerBootstrap();
            //设置两个线程组
            bootstrap.group(bossGroup, workerGroup)
                    //使用NioServerSocketChannel 作为服务器的通道实现
                    .channel(NioServerSocketChannel.class)
                    //设置线程队列得到连接个数
                    .option(ChannelOption.SO_BACKLOG, 128)
                    //设置保持活动连接状态
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    //给workerGroup 的 EventLoop 对应的管道设置处理器
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            /**
                             * 因为基于http协议, 使用http的编解码器
                             */
                            pipeline.addLast(new HttpServerCodec());

                            /**
                             * 是以块方式写, 添加 ChunkedWriteHandler 处理器
                             */
                            pipeline.addLast(new ChunkedWriteHandler());
                            /**
                             * http数据传输过程是分段的, HttpObjectAggregator, 就是可以将多段聚合
                             * 当浏览器发送大量数据时, 就会发出多次http请求
                             */
                            pipeline.addLast(new HttpObjectAggregator(8192));
                            /**
                             * 对于websocket数据是以 帧 形式传递
                             *  浏览器请求时 ws://localhost:7000/hello 其中 hello会与下面的对应
                             *  WebSocketServerProtocolHandler 核心功能是将http协议升级为ws协议, 保持长链接
                             */
                            pipeline.addLast(new WebSocketServerProtocolHandler("/hello"));


                            //自定义handler
                            pipeline.addLast(new TextWebsocketFrameHandler());
                        }
                    });

            //启动服务器并绑定一个端口并且同步生成一个 ChannelFuture 对象
            ChannelFuture cf = bootstrap.bind(7000).sync();
            if (cf.isSuccess()) {
                System.out.println("websocket server start---------------");
            }

            //对关闭通道进行监听
            cf.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //发送异常关闭
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();

        }
    }
}