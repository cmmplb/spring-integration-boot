package com.cmmplb.netty.websocket.server;//package com.cmmplb.netty.websocket.server;
//
//import com.cmmplb.netty.properties.PortProperties;
//import com.cmmplb.netty.websocket.handler.WebSocketHandler;
//import io.netty.bootstrap.ServerBootstrap;
//import io.netty.channel.ChannelFuture;
//import io.netty.channel.ChannelInitializer;
//import io.netty.channel.ChannelOption;
//import io.netty.channel.nio.NioEventLoopGroup;
//import io.netty.channel.socket.SocketChannel;
//import io.netty.channel.socket.nio.NioServerSocketChannel;
//import io.netty.handler.codec.http.HttpObjectAggregator;
//import io.netty.handler.codec.http.HttpServerCodec;
//import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
//import io.netty.handler.stream.ChunkedWriteHandler;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.DisposableBean;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
///**
// * @author penglibo
// * @date 2021-06-19 14:01:12
// * @since jdk 1.8
// * spring容器代理启动
// */
//
//@Slf4j
//@Component
//public class WebSocketServerSpring implements CommandLineRunner, DisposableBean {
//
//    @Autowired
//    private PortProperties portProperties;
//
//    private NioEventLoopGroup boosGroup = new NioEventLoopGroup();
//    private NioEventLoopGroup group = new NioEventLoopGroup();
//
//    public void start() {
//        log.info("正在启动webSocket服务器……");
//        boosGroup = new NioEventLoopGroup();
//        group = new NioEventLoopGroup();
//        ServerBootstrap serverBootstrap = new ServerBootstrap();
//        serverBootstrap.option(ChannelOption.SO_BACKLOG, 1024);
//        serverBootstrap.group(group, boosGroup)
//                .channel(NioServerSocketChannel.class)
//                .localAddress(portProperties.getWebSocket())
//                .childHandler(new ChannelInitializer<SocketChannel>() {
//                    @Override
//                    protected void initChannel(SocketChannel socketChannel) throws Exception {
//                        System.out.println("收到新连接:" + socketChannel.localAddress());
//                        // 编解码 http 请求
//                        socketChannel.pipeline().addLast(new HttpServerCodec());
//                        // 写文件内容
//                        socketChannel.pipeline().addLast(new ChunkedWriteHandler());
//                        // 聚合解码 HttpRequest/HttpContent/LastHttpContent 到 FullHttpRequest
//                        // 保证接收的 Http 请求的完整性
//                        socketChannel.pipeline().addLast(new HttpObjectAggregator(8192));
//                        // 处理其他的 WebSocketFrame
//                        socketChannel.pipeline().addLast(new WebSocketServerProtocolHandler("/ws", "WebSocket", true, 65536 * 10));
//                        // 这个类的代码是模板代码, 最核心的就是ch.pipeline().addLast(new MyWebSocketHandler()),
//                        // 可以根据自己的需求配置即可
//                        socketChannel.pipeline().addLast(new WebSocketHandler());
//                    }
//                });
//        ChannelFuture sync = null; // 服务器异步创建绑定
//        try {
//            sync = serverBootstrap.bind(portProperties.getWebSocket()).sync();
//            log.info("启动webSocket服务器启动成功, 正在监听端口:" + portProperties.getWebSocket());
//            sync.channel().closeFuture().sync(); //以异步的方式关闭端口
//        } catch (InterruptedException e) {
//            log.info("启动出现异常：" + e);
//        }
//        try {
//            group.shutdownGracefully().sync(); // 释放线程池资源
//            boosGroup.shutdownGracefully().sync(); // 释放线程池资源
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void destroy() throws Exception {
//        System.out.println("释放 websocket server");
//        group.shutdownGracefully();
//        boosGroup.shutdownGracefully();
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        start();
//    }
//}
