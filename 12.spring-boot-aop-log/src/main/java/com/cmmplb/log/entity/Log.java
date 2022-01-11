package com.cmmplb.log.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author penglibo
 * @date 2021-04-14 14:42:48
 */

@Data
public class Log implements Serializable {

    private static final long serialVersionUID = -6309732882044872298L;

    /**
     * 主键id
     */
    private Integer id;

    /**
     * 操作人
     */
    private String username;

    /**
     * 操作描述
     */
    private String operation;

    /**
     * 响应时间
     */
    private Integer time;

    /**
     * 请求方法
     */
    private String method;

    /**
     * 请求参数
     */
    private String params;

    /**
     * 日志类型:0-正常日志;2-异常日志
     */
    private Byte type;

    /**
     * 异常原因
     */
    private String excCause;

    /**
     * 异常描述
     */
    private String excDesc;

    /**
     * 异常位置
     */
    private String excLocation;

    /**
     * ip地址
     */
    private String ip;

    /**
     * 创建时间
     */
    private Date createTime;
}
