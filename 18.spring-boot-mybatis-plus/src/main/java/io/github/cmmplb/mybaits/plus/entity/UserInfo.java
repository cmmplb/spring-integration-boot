package com.cmmplb.mybatis.plus.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author penglibo
 * @date 2021-08-22 11:47:03
 * @since jdk 1.8
 * 用户详情表
 */

@Data
@Accessors(chain = true) // 使用这个可以让set返回实体, 便于链式set
@TableName(value = "user_info")
@ApiModel(value = "UserInfo", description = "用户详情表")
public class UserInfo implements Serializable {

    /**
     * 主键-对应用户信息表id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "主键-对应用户信息表id", example = "1")
    private Long id;

    /**
     * 头像
     */
    @TableField(value = "icon")
    @ApiModelProperty(value = "头像")
    private String icon;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value = "创建时间", example = "2021-01-01 12:00:00")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    @ApiModelProperty(value = "更新时间", example = "2021-01-01 12:00:00")
    private Date updateTime;

    /**
     * 逻辑删除:0-正常;1-删除
     */
    @TableField(value = "deleted")
    @ApiModelProperty(value = "逻辑删除:0-正常;1-删除", example = "1")
    private Byte deleted;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_ICON = "icon";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_UPDATE_TIME = "update_time";

    public static final String COL_DELETED = "deleted";
}