package io.github.cmmplb.cache.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Accessors(chain = true) // 使用这个可以让set返回实体, 便于链式set
@Schema(name = "User", description = "用户信息表")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Schema(description = "主键", example = "1")
    private Long id;

    /**
     * 租户id
     */
    @Schema(description = "租户id", example = "1", required = true)
    private Long tenantId;

    /**
     * 用户名
     */
    @Schema(description = "用户名", example = "小明", required = true)
    private String name;

    /**
     * 性别:0-女;1-男
     */
    @Schema(description = "性别:0-女;1-男", example = "1", required = true)
    private Byte sex;

    /**
     * 手机号
     */
    @Schema(description = "手机号", example = "19999999999", required = true)
    private String mobile;

    /**
     * 用户状态:0-正常;1-禁用
     */
    @Schema(description = "用户状态:0-正常;1-禁用", example = "0")
    private Byte status;

    /**
     * 乐观锁版本号
     */
    @Schema(description = "乐观锁版本号", example = "1")
    private Integer version;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间", example = "2021-01-01 12:00:00")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间", example = "2021-01-01 12:00:00")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 逻辑删除:0-正常;1-删除
     */
    @Schema(description = "逻辑删除:0-正常;1-删除", example = "0")
    private Byte deleted;
}