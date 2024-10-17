package io.github.cmmplb.mybatis.plus.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author penglibo
 * @date 2022-04-02 15:51:34
 * @since jdk 1.8
 */

@Data
@TableName(value = "tb_admin")
public class Admin implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 登陆名称
     */
    @TableField(value = "loginname")
    private String loginname;

    /**
     * 密码
     */
    @TableField(value = "password")
    private String password;

    /**
     * 状态
     */
    @TableField(value = "state")
    private String state;
}
