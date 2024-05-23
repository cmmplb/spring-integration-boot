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
     * 日志状态:0-正常;1-异常
     */
    private Byte status;

    /**
     * 操作类型
     */
    private String type;

    /**
     * 操作内容
     */
    private String content;

    /**
     * 业务类型:0-新增;1-删除;2-修改;3-查询;4-导入;5-导出;
     */
    private Byte businessType;

    /**
     * 操作IP地址
     */
    private String ip;

    /**
     * 用户代理
     */
    private String userAgent;

    /**
     * 请求URI
     */
    private String requestUri;

    /**
     * 请求方式
     */
    private String method;

    /**
     * 请求方法
     */
    private String methodName;

    /**
     * 请求参数
     */
    private String params;

    /**
     * 执行时间(毫秒)
     */
    private Long time;

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
     * 创建时间
     */
    private Date createTime;
}
