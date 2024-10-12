package com.cmmplb.netty.socket.handler;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.cmmplb.netty.socket.dto.BaseDTO;
import com.cmmplb.netty.socket.entity.BusinessEntity;
import com.cmmplb.netty.socket.entity.NettyMessage;
import com.cmmplb.netty.socket.enums.CmdEnum;
import com.cmmplb.netty.socket.factory.BusinessFactory;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author penglibo
 * @date 2021-06-28 13:08:38
 * @since jdk 1.8
 * 5.0:ChannelInboundHandlerAdapter, ChannelOutboundHandlerAdapter, 和 ChannelDuplexHandlerAdapter 弃用了,  被 ChannelHandlerAdapter 取代.
 * 因为现在你无法区分一个 handler是 inbound handler 或者 outbound handler, 所以CombinedChannelDuplexHandler 被 ChannelHandlerAppender取代.
 */

public class ServerHandler extends /*ChannelHandlerAdapter*/ChannelInboundHandlerAdapter {  //

    private static final Logger logger = LoggerFactory.getLogger(ServerHandler.class);

    // 在这个类里面我们首先建立了一个channelGroup,
    // 每当有客户端连接的时候, 就添加到channelGroup里面,
    // 我们可以发送消息给固定的人, 也可以群发消息. 
    public static ChannelGroup channelGroup;

    // KEY是设备号,value是连接的设备通道
    public static ConcurrentHashMap<String, ChannelHandlerContext> deviceMap = new ConcurrentHashMap<>();

    static {
        channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("与客户端建立连接, 通道开启！");
        channelGroup.add(ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("与客户端断开连接, 通道关闭！");
        channelGroup.remove(ctx.channel());
        for (Iterator<String> iterator = deviceMap.keySet().iterator(); iterator.hasNext(); ) {
            String key = iterator.next();
            if (ctx.equals(deviceMap.get(key))) {
                deviceMap.remove(key);
            }
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("服务端接收客户端发送过来的数据结束之后调用");
        ctx.flush();
        System.out.println("信息接收完毕...");
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
        System.out.println("工程出现异常的时候调用");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            if (!(msg instanceof NettyMessage)) {
                ctx.fireChannelRead(msg);
                return;
            }

            NettyMessage messageReq = (NettyMessage) msg;

            // 请求参数-json
            String dataJson = messageReq.getData();
            // 将json转为对象
            BaseDTO dto = JSON.parseObject(dataJson, new TypeReference<BaseDTO>() {
            });
            // 获取到设备号
            if (!deviceMap.containsKey(dto.getSn())) {
                deviceMap.put(dto.getSn(), ctx);
            }

            logger.info("收到设备json" + JSON.parseObject(messageReq.getData(), new TypeReference<Map<String, Object>>() {
            }));

            BusinessEntity businessEntity = business(messageReq.getCmd(), dataJson);// 消息命令-处理设备业务逻辑
            if (null != businessEntity) {
                NettyMessage messageResp = new NettyMessage();
                // 请求命令
                messageResp.setCmd(businessEntity.getRespCmd());
                // 消息体
                String resJson = businessEntity.getBodyJson();

                messageResp.setLength(resJson.getBytes().length);
                messageResp.setData(resJson);

                // 发送消息（编码器处理）
                logger.info("发送消息（编码器处理）");
                ctx.writeAndFlush(messageResp).sync();
            }

        } catch (Exception e) {
            logger.error("异常" + e);
        }
    }

    /**
     * 处理设备调用不同的业务-有时间用设计模式优化了吧
     * @param reqCmd   请求命令
     * @param dataJson 请求参数
     * @return
     */
    private BusinessEntity business(Byte reqCmd, String dataJson) {
        String message = null;
        switch (CmdEnum.getInstance(reqCmd)) {
            // -------------------------------这里是设备主动请求的命令-------------------------------
            case HEARTBEAT:
                // 设备心跳
                message = CmdEnum.HEARTBEAT.getMessage();
                break;
            // -------------------------------这里是服务端调用返回的命令-------------------------------
            // 服务器给设备发送消息命令统一为0x30
            // 终端返回统一为0x31
            case CLIENT:
                // 业务区分通过可变数据字节中cmd分类
                message = CmdEnum.CLIENT.getMessage();
                break;
            default:
                // 未知的消息命令
                message = CmdEnum.DEFAULT.getMessage();
                break;
        }
        return BusinessFactory.getInvokeStrategy(message).business(reqCmd, dataJson);
    }


}