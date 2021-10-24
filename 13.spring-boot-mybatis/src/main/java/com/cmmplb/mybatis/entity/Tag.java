package com.cmmplb.mybatis.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author penglibo
 * @date 2021-08-22 09:29:20
 * @since jdk 1.8
 * 标签表
 */

@Data
@Accessors(chain = true) // 使用这个可以让set返回实体，便于链式set
@ApiModel(value = "Tag", description = "标签表")
public class Tag implements Serializable {

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键", example = "1")
    private Long id;

    /**
     * 标签名称
     */
    @ApiModelProperty(value = "标签名称", example = "活跃用户", required = true)
    private String name;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", example = "2021-01-01 12:00:00")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH-mm-ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH-mm-ss")
    private Date createTime;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人", example = "1")
    private Long createBy;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间", example = "2021-01-01 12:00:00")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH-mm-ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH-mm-ss")
    private Date updateTime;

    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人", example = "1")
    private Long updateBy;

    /**
     * 逻辑删除:0-正常;1-删除
     */
    @ApiModelProperty(value = "逻辑删除:0-正常;1-删除", example = "0")
    private Byte deleted;

    private static final long serialVersionUID = 1L;
}