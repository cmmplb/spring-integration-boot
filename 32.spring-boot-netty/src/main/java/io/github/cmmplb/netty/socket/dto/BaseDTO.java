package io.github.cmmplb.netty.socket.dto;

import lombok.Data;

/**
 * @author penglibo
 * @date 2021-07-01 13:02:32
 * @since jdk 1.8
 */

@Data
public class BaseDTO {

    /**
     * 设备序列号
     */
    private String sn;

    /**
     * 消息ID
     */
    private String messageId;

    /**
     * 消息类型
     */
    private String message;
}
