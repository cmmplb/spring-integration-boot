package io.github.cmmplb.activiti.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author penglibo
 * @date 2021-08-17 15:58:38
 * @since jdk 1.8
 */

@Data
public class UserInfoVO implements Serializable {

    private static final long serialVersionUID = -5029097111709615443L;

    /**
     * 主键
     */
    @Schema(description = "主键", example = "1")
    private Long id;

    /**
     * 用户名
     */
    @Schema(description = "用户名", example = "admin")
    private String username;

    /**
     * 姓名
     */
    @Schema(description = "姓名", example = "小明")
    private String name;

    /**
     * 用户状态:0-正常;1-禁用
     */
    @Schema(description = "用户状态:0-正常;1-禁用", example = "0")
    private Byte status;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间", example = "2021-01-01 12:00:00")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

}
