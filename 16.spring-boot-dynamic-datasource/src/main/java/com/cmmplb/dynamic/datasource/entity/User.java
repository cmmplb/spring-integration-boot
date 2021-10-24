package com.cmmplb.dynamic.datasource.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
* @author penglibo
* @date 2021-08-06 10:47:06
* @since jdk 1.8
*/

/**
 * 用户表
 */
@ApiModel(value = "com-cmmplb-dynamic-datasource-entity-User")
@Data
@TableName(value = "`user`")
public class User implements Serializable {
    public static final String COL_AGE = "age";
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 用户名
     */
    @TableField(value = "`name`")
    @ApiModelProperty(value = "用户名")
    private String name;

    /**
     * 用户余额
     */
    @TableField(value = "amount")
    @ApiModelProperty(value = "用户余额")
    private Integer amount;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_NAME = "name";

    public static final String COL_AMOUNT = "amount";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_UPDATE_TIME = "update_time";
}