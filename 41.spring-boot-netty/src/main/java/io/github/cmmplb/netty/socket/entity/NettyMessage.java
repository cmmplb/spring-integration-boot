package io.github.cmmplb.netty.socket.entity;

import lombok.Data;

@Data
public class NettyMessage {

    /**
     * 消息标识:固定为"OFZL" - 79, 70, 90, 76
     */
    private int identify = 1330010700;

    /**
     * 版本号：当前为 0x02
     */
    private byte version = 0x02;

    /**
     * 数据类型：当前为0x01 json方式
     */
    private byte dataType = 0x01;

    /**
     * 消息命令：参见每个请求说明
     */
    private byte cmd;

    /**
     * 数据长度：消息体数据长度 -- 4字节数组
     */
    private int length;

    /**
     * 消息体-json格式
     */
    private String data;

}
