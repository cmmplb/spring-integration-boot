package com.cmmplb.mybatis.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @author penglibo
 * @date 2021-08-22 09:29:20
 * @since jdk 1.8
 * 标签表
 */

@Data
@Accessors(chain = true) // 使用这个可以让set返回实体, 便于链式set
@Schema(name = "Tag", description = "标签表")
public class Tag implements Serializable {

    /**
     * 主键
     */
    @Schema(name = "id", description = "主键id", example = "1")
    private Long id;

    /**
     * 标签名称, required = true替换为 requiredMode = Schema.RequiredMode.REQUIRED
     */
    @Schema(name = "name", description = "标签名称", example = "活跃用户", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    /**
     * 创建时间
     */
    @Schema(name = "createTime", description = "创建时间", example = "2021-01-01 12:00:00")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 创建人
     */
    @Schema(description = "创建人", example = "1")
    private Long createBy;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间", example = "2021-01-01 12:00:00")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 更新人
     */
    @Schema(description = "更新人", example = "1")
    private Long updateBy;

    /**
     * 逻辑删除:0-正常;1-删除
     */
    @Schema(description = "逻辑删除:0-正常;1-删除", example = "0")
    private Byte deleted;

    @Serial
    private static final long serialVersionUID = 1L;
}