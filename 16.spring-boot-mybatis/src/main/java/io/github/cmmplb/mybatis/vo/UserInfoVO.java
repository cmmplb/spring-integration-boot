package io.github.cmmplb.mybatis.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.cmmplb.mybatis.entity.Tag;
import io.github.cmmplb.mybatis.entity.UserInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author penglibo
 * @date 2021-08-17 15:58:38
 * @since jdk 1.8
 */

@Data
public class UserInfoVO implements Serializable {

    private static final long serialVersionUID = -5029097111709615443L;

    /**
     * 用户详情
     */
    @Schema(description = "UserInfo")
    private UserInfo userInfo;

    /**
     * 用户标签集合
     */
    @Schema(description = "用户标签集合")
    private List<Tag> tagList;

    /**
     * 主键
     */
    @Schema(description = "主键", example = "1")
    private Long id;

    /**
     * 用户名
     */
    @Schema(description = "用户名", example = "小明", required = true)
    private String name;

    /**
     * 性别
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
     * 创建时间
     */
    @Schema(description = "创建时间", example = "2021-01-01 12:00:00")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

}
