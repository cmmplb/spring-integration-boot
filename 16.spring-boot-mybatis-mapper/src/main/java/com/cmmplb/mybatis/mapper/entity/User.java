package com.cmmplb.mybatis.mapper.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author plb
 * @date 2020/6/15 11:16
 * 用户信息表
 */

@Data
@Accessors(chain = true) // 使用这个可以让set返回实体，便于链式set
@ApiModel(value = "User", description = "用户信息表")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键", example = "1")
    private Long id;

    /**
     * 租户id
     */
    @ApiModelProperty(value = "租户id", example = "1", required = true)
    private Long tenantId;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名", example = "小明", required = true)
    private String name;

    /**
     * 性别:0-女;1-男
     */
    @ApiModelProperty(value = "性别:0-女;1-男", example = "1", required = true)
    private Byte sex;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号", example = "19999999999", required = true)
    private String mobile;

    /**
     * 用户状态:0-正常;1-禁用
     */
    @ApiModelProperty(value = "用户状态:0-正常;1-禁用", example = "0")
    private Byte status;

    /**
     * 乐观锁版本号
     */
    @ApiModelProperty(value = "乐观锁版本号", example = "1")
    private Integer version;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", example = "2021-01-01 12:00:00")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH-mm-ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH-mm-ss")
    private Date createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间", example = "2021-01-01 12:00:00")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH-mm-ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH-mm-ss")
    private Date updateTime;

    /**
     * 逻辑删除:0-正常;1-删除
     */
    @ApiModelProperty(value = "逻辑删除:0-正常;1-删除", example = "0")
    private Byte deleted;
}