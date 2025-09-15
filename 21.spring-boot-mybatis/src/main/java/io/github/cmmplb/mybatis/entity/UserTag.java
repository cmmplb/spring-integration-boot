package io.github.cmmplb.mybatis.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author penglibo
 * @date 2021-08-22 09:29:20
 * @since jdk 1.8
 * 用户标签关联表
 */

@Data
@ApiModel(value = "Tag", description = "用户标签关联表")
public class UserTag implements Serializable {

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键", example = "1")
    private Long id;

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id", example = "1")
    private Long userId;

    /**
     * 标签id
     */
    @ApiModelProperty(value = "标签id", example = "1")
    private Long tagId;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", example = "2021-01-01 12:00:00")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人", example = "1")
    private Long createBy;

    private static final long serialVersionUID = 1L;
}