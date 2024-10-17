package io.github.cmmplb.mybatis.plus.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.cmmplb.mybatis.plus.entity.Tag;
import io.github.cmmplb.mybatis.plus.entity.UserInfo;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
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

    @Serial
    private static final long serialVersionUID = -5029097111709615443L;

    /**
     * 用户详情
     */
    private UserInfo userInfo;

    /**
     * 用户标签集合
     */
    private List<Tag> tagList;

    /**
     * 主键
     */
    private Long id;

    /**
     * 用户名
     */
    private String name;

    /**
     * 性别
     */
    private Byte sex;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 用户状态:0-正常;1-禁用
     */
    private Byte status;

    /**
     * 创建时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

}