package io.github.cmmplb.netty.socket.entity;

import lombok.Data;

/**
 * @author penglibo
 * @date 2021-07-01 13:53:56
 * @since jdk 1.8
 * 业务处理信息
 */

@Data
public class BusinessEntity {

    /**
     * 响应的命令
     */
    private Byte respCmd;

    /**
     * 响应消息体json内容
     */
    String bodyJson;
}
