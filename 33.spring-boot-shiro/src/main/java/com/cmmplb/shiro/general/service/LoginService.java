package com.cmmplb.shiro.general.service;

import com.cmmplb.shiro.general.dto.LoginDTO;
import com.cmmplb.shiro.general.entity.User;
import com.cmmplb.shiro.general.vo.LoginVO;

import java.util.Set;

/**
 * @author penglibo
 * @date 2021-09-27 16:55:07
 * @since jdk 1.8
 */
public interface LoginService {

    /**
     * 登录
     * @param dto
     * @return
     */
    LoginVO doLogin(LoginDTO dto);

    /**
     * 退出登录
     * @return
     */
    boolean doLogout();

    /**
     * 根据用户名查询
     * @param username
     * @return
     */
    User loadByUsername(String username);

    /**
     * 获取权限
     * @param username 用户名
     * @param type     0-角色;1-权限
     * @return
     */
    Set<String> getAuthorization(String username, Byte type);
}
