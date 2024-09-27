package com.cmmplb.security.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cmmplb.core.constants.GlobalConstant;
import com.cmmplb.security.dao.UserMapper;
import com.cmmplb.security.domain.convert.UserDetails;
import com.cmmplb.security.domain.entity.User;
import com.cmmplb.security.handler.exception.MobileNotFoundException;
import com.cmmplb.security.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author penglibo
 * @date 2024-09-02 11:29:13
 * @since jdk 1.8
 * 用户信息源，这个对象是spring security中用户信息源的核心对象，它负责用户信息的获取
 */

@Slf4j
@Service
public class UserDetailServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = baseMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        return getUserDetails(user);
    }

    @Override
    public UserDetails getUserByMobile(String mobile) {
        User user = baseMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getMobile, mobile));
        if (user == null) {
            throw new MobileNotFoundException("手机号不存在");
        }
        return getUserDetails(user);
    }

    private UserDetails getUserDetails(User user) {
        UserDetails userDetails = new UserDetails();
        userDetails.setUsername(user.getUsername());
        userDetails.setPassword(passwordEncoder.encode(user.getPassword()));
        userDetails.setEnabled(user.getStatus().equals(GlobalConstant.NUM_ZERO));
        // user.setPassword(MD5Util.encode("123456"));
        userDetails.setAuthorities(getGrantedAuthorities(user.getUsername()));

        // 输出加密后的密码
        log.info("输出加密后的密码:{}", userDetails.getPassword());
        return userDetails;
    }

    private static List<SimpleGrantedAuthority> getGrantedAuthorities(String username) {
        // 查询用户权限
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        switch (username) {
            case "admin":
                // 管理员拥有全部权限
                authorities = Stream.of("admin", "user", "resource", "ROLE_admin", "ROLE_user", "write", "read")
                        .map(SimpleGrantedAuthority::new).collect(Collectors.toList());
                break;
            case "user":
                authorities.add(new SimpleGrantedAuthority("user"));
                // 角色前缀，适配@Secured("RLOE_user")
                authorities.add(new SimpleGrantedAuthority("ROLE_user"));
                authorities.add(new SimpleGrantedAuthority("read"));
                break;
            case "resource":
                authorities.add(new SimpleGrantedAuthority("resource"));
                break;
            default:
                break;
        }
        return authorities;
    }
}
