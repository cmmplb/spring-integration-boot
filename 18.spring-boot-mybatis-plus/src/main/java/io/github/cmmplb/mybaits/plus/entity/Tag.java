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
 * 标签表
 */

@Data
@Accessors(chain = true) // 使用这个可以让set返回实体, 便于链式set
@TableName(value = "tag")
@ApiModel(value = "Tag", description = "标签表")
public class Tag implements Serializable {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "主键", example = "1")
    private Long id;

    /**
     * 标签名称
     */
    @TableField(value = "`name`")
    @ApiModelProperty(value = "标签名称", example = "活跃用户")
    private String name;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value = "创建时间", example = "2021-01-01 12:00:00")
    private Date createTime;

    /**
     * 创建人
     */
    @TableField(value = "create_by")
    @ApiModelProperty(value = "创建人", example = "1")
    private Long createBy;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    @ApiModelProperty(value = "更新时间", example = "2021-01-01 12:00:00")
    private Date updateTime;

    /**
     * 更新人
     */
    @TableField(value = "update_by")
    @ApiModelProperty(value = "更新人", example = "1")
    private Long updateBy;

    /**
     * 逻辑删除:0-正常;1-删除
     */
    @TableField(value = "deleted")
    @ApiModelProperty(value = "逻辑删除:0-正常;1-删除", example = "1")
    private Byte deleted;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_NAME = "name";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_CREATE_BY = "create_by";

    public static final String COL_UPDATE_TIME = "update_time";

    public static final String COL_UPDATE_BY = "update_by";

    public static final String COL_DELETED = "deleted";
}