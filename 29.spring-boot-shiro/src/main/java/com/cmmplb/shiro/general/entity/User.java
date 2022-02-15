package com.cmmplb.shiro.general.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author penglibo
 * @date 2021-09-18 15:20:26
 * @since jdk 1.8
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = -7483951075605976986L;

    /**
     * 用户id
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户状态:0-正常;1-禁用
     */
    private Byte status;
}
