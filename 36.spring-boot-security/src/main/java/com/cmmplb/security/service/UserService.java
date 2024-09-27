package com.cmmplb.security.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cmmplb.security.domain.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author penglibo
 * @date 2024-09-02 11:28:39
 * @since jdk 1.8
 */
public interface UserService extends UserDetailsService, IService<User> {

    UserDetails getUserByMobile(String mobile);
}
