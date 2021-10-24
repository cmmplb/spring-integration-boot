package com.cmmplb.mybatis.plus.vo;

import com.cmmplb.mybatis.plus.entity.Tag;
import com.cmmplb.mybatis.plus.entity.UserInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(value = "UserInfo")
    private UserInfo userInfo;

    /**
     * 用户标签集合
     */
    @ApiModelProperty(value = "用户标签集合")
    private List<Tag> tagList;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键", example = "1")
    private Long id;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名", example = "小明", required = true)
    private String name;

    /**
     * 性别
     */
    @ApiModelProperty(value = "性别:0-女;1-男", example = "1", required = true)
    private Byte sex;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号", example = "19999999999", required = true)
    private String mobile;

    /**
     * 用户状态：1-正常；2-禁用；
     */
    @ApiModelProperty(value = "用户状态:0-正常;1-禁用", example = "0")
    private Byte status;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", example = "2021-01-01 12:00:00")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH-mm-ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH-mm-ss")
    private Date createTime;

}
