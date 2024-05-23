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
 * 用户标签关联表
 */

@Data
@Accessors(chain = true) // 使用这个可以让set返回实体，便于链式set
@TableName(value = "user_tag")
@ApiModel(value = "UserTag", description = "用户标签关联表")
public class UserTag implements Serializable {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "主键", example = "1")
    private Long id;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    @ApiModelProperty(value = "用户id", example = "1")
    private Long userId;

    /**
     * 标签id
     */
    @TableField(value = "tag_id")
    @ApiModelProperty(value = "标签id", example = "1")
    private Long tagId;

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

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_USER_ID = "user_id";

    public static final String COL_TAG_ID = "tag_id";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_CREATE_BY = "create_by";
}