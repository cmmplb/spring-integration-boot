package com.cmmplb.security.service.impl;

import com.cmmplb.security.entity.User;
import com.cmmplb.security.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author penglibo
 * @date 2021-08-30 11:47:06
 * @since jdk 1.8
 */

@Slf4j
@Service
public class UserDetailServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 模拟一个用户，替代数据库获取逻辑
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode("123456"));
        // user.setPassword(MD5Util.encode("123456"));

        // 输出加密后的密码
        log.info("输出加密后的密码:{}", user.getPassword());

        // 查询用户权限
        List<GrantedAuthority> authorities = new ArrayList<>();
        switch (username) {
            case "admin":
                // 管理员拥有全部权限
                authorities = Stream.of("resource", "user").map(SimpleGrantedAuthority::new).collect(Collectors.toList());
                break;
            case "user":
                authorities.add(new SimpleGrantedAuthority("user"));
                authorities.add(new SimpleGrantedAuthority("ROLE_user"));
                break;
            case "resource":
                authorities.add(new SimpleGrantedAuthority("resource"));
                break;
            default:
                break;
        }
        user.setAuthorities(authorities);
        return user;
    }

    @Override
    public UserDetails getUserByMobile(String mobile) {
        if (!mobile.equalsIgnoreCase("19999999999")) {
            throw new UsernameNotFoundException("手机号不存在");
        }
        // 模拟一个用户，替代数据库获取逻辑
        User user = new User();
        user.setUsername("admin");
        user.setMobile(mobile);
        user.setPassword(passwordEncoder.encode("123456"));
        // user.setPassword(MD5Util.encode("123456"));

        // 输出加密后的密码
        log.info("输出加密后的密码:{}", user.getPassword());

        // 查询用户权限
        List<GrantedAuthority> authorities = new ArrayList<>();
        switch (user.getUsername()) {
            case "admin":
                // 管理员拥有全部权限
                authorities = Stream.of("resource", "user").map(SimpleGrantedAuthority::new).collect(Collectors.toList());
                break;
            case "user":
                authorities.add(new SimpleGrantedAuthority("user"));
                authorities.add(new SimpleGrantedAuthority("ROLE_user"));
                break;
            case "resource":
                authorities.add(new SimpleGrantedAuthority("resource"));
                break;
            default:
                break;
        }
        user.setAuthorities(authorities);
        return user;
    }
}
