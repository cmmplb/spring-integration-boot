package io.github.cmmplb.mybatis.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @author penglibo
 * @date 2021-08-22 09:29:20
 * @since jdk 1.8
 * 用户标签关联表
 */

@Data
@Schema(name = "Tag", description = "用户标签关联表")
public class UserTag implements Serializable {

    /**
     * 主键
     */
    @Schema(name = "id", description = "主键", example = "1")
    private Long id;

    /**
     * 用户id
     */
    @Schema(description = "用户id", example = "1")
    private Long userId;

    /**
     * 标签id
     */
    @Schema(description = "标签id", example = "1")
    private Long tagId;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间", example = "2021-01-01 12:00:00")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 创建人
     */
    @Schema(description = "创建人", example = "1")
    private Long createBy;

    @Serial
    private static final long serialVersionUID = 1L;
}