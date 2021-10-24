package com.cmmplb.shiro.service;

import com.cmmplb.shiro.dto.LoginDTO;
import com.cmmplb.shiro.vo.LoginVO;

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
}
