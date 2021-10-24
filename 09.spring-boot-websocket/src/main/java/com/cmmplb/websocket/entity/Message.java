package com.cmmplb.websocket.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;

@Data
public class Message {

    /**
     * 发送者name
     */
    public String from;

    /**
     * 接收者name
     */
    public String to;

    /**
     * 发送的文本
     */
    public String text;

    /**
     * 发送时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    public Date date;
}
