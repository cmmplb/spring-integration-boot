package com.cmmplb.shiro.general.beans;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author plb
 * @date 2020/6/12 14:02
 */

@Data
public class UserBean {

    /**
     * id
     */
    private Long id;

    /**
     * 用户头像
     */
    private String icon;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 用户名
     */
    private String username;

    /**
     * 角色名称，多个“，”隔开
     */
    private String roleNames;

    /**
     * 权限范围 1-全部;2-本学院;3-本系;4-本专业;5-本班;6-本人
     */
    private Byte permissionRange;

    /**
     * 登录次数
     */
    private int loginCount;

    /**
     * 角色id集合
     */
    private List<Long> roleIds = new ArrayList<Long>();
}
