package com.cmmplb.shiro.general.service.impl;

import com.alibaba.fastjson.JSON;
import com.cmmplb.core.constants.GlobalConstants;
import com.cmmplb.core.exception.CustomException;
import com.cmmplb.core.result.HttpCodeEnum;
import com.cmmplb.core.utils.MD5Util;
import com.cmmplb.core.utils.ServletUtil;
import com.cmmplb.core.utils.SpringApplicationUtil;
import com.cmmplb.core.utils.UUIDUtil;
import com.cmmplb.redis.service.RedisService;
import com.cmmplb.shiro.general.beans.UserBean;
import com.cmmplb.shiro.general.constants.AuthorizationConstants;
import com.cmmplb.shiro.general.constants.HttpConstants;
import com.cmmplb.shiro.general.dto.LoginDTO;
import com.cmmplb.shiro.general.entity.User;
import com.cmmplb.shiro.general.properties.ShiroProperties;
import com.cmmplb.shiro.general.service.LoginService;
import com.cmmplb.shiro.general.utils.ShiroUtil;
import com.cmmplb.shiro.general.vo.LoginVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author penglibo
 * @date 2021-09-27 16:55:14
 * @since jdk 1.8
 */

@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private RedisService redisService;

    @Autowired
    private ShiroProperties shiroProperties;

    @Override
    public LoginVO doLogin(LoginDTO dto) {
        // 自定义
        // custom(dto);
        // 默认
        return general(dto);
    }

    private LoginVO custom(LoginDTO dto) {
        String username = dto.getUsername();
        String password = dto.getPassword();

        String token = UUIDUtil.uuidTrim();
        // 设置头
        ServletUtil.getResponse().setHeader(AuthorizationConstants.AUTHORIZATION, token);

        User user = loadByUsername(username);
        if (!MD5Util.encode(password).equals(user.getPassword())) {
            throw new CustomException(HttpConstants.INCORRECT_CREDENTIALS);
        }
        if (user.getStatus().equals(GlobalConstants.NUM_ONE)) {
            throw new CustomException(HttpConstants.ACCOUNT_LOCKED);
        }

        // 设置登录缓存信息
        redisService.set(AuthorizationConstants.AUTH_UID_CACHE_PREFIX + token, user.getId(), AuthorizationConstants.AUTH_CACHE_EXPIRE_SECONDS);
        redisService.set(AuthorizationConstants.AUTH_UID_INFO_PREFIX + user.getId(), JSON.toJSONString(user));
        redisService.lpush(AuthorizationConstants.UID_TOKENS_CACHE_PREFIX + user.getId(), token);

        // 这里前后端未分离就重定向到首页
        if (!shiroProperties.getSplit()) {
            try {
                ServletUtil.getResponse().sendRedirect(SpringApplicationUtil.path);
            } catch (IOException e) {
                log.error("异常信息：", e);
                throw new CustomException(HttpCodeEnum.INTERNAL_SERVER_ERROR);
            }
        }
        // 如果前后端分离就返回登录信息
        return new LoginVO(token);
    }

    private LoginVO general(LoginDTO dto) {
        String username = dto.getUsername();
        String password = dto.getPassword();

        try {
            //验证身份和登陆
            UsernamePasswordToken upToken = new UsernamePasswordToken(username, password/*, rememberMe*/);
            //验证成功进行登录操作
            ShiroUtil.getSubject().login(upToken);
            String token = ShiroUtil.getSubject().getSession().getId().toString();
            // 缓存token信息
            redisService.set(AuthorizationConstants.AUTH_UID_CACHE_PREFIX + token, token);
            UserBean user = ShiroUtil.getUser();

            // 设置登录缓存信息
            redisService.set(AuthorizationConstants.AUTH_UID_CACHE_PREFIX + token, user.getId(), AuthorizationConstants.AUTH_CACHE_EXPIRE_SECONDS);
            redisService.set(AuthorizationConstants.AUTH_UID_INFO_PREFIX + user.getId(), JSON.toJSONString(token));
            redisService.lpush(AuthorizationConstants.UID_TOKENS_CACHE_PREFIX + user.getId(), token);

            // 这里前后端未分离就重定向到首页
            if (!shiroProperties.getSplit()) {
                ServletUtil.getResponse().sendRedirect(SpringApplicationUtil.path);
            }
            // 如果前后端分离就返回登录信息
            return new LoginVO(token);

        } catch (IncorrectCredentialsException e) {
            throw new CustomException(HttpConstants.INCORRECT_CREDENTIALS);

        } catch (LockedAccountException e) {
            throw new CustomException(HttpConstants.ACCOUNT_LOCKED);

        } catch (AuthenticationException e) {
            throw new CustomException(HttpConstants.AUTHENTICATION);

        } catch (Exception e) {
            log.error("异常信息：", e);
            throw new CustomException(HttpCodeEnum.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public boolean doLogout() {
        // todo:
        return false;
    }

    /**
     * 模拟用户名到数据库查询用户信息
     * @param username 用户名
     * @return user
     */
    @Override
    public User loadByUsername(String username) {
        switch (username) {
            case "admin":
                return new User(1L, username, "19999999999", MD5Util.encode("123456"), GlobalConstants.NUM_ZERO);
            case "user":
                return new User(2L, username, "18888888888", MD5Util.encode("123456"), GlobalConstants.NUM_ZERO);
            default:
                return null;
        }
    }


    /**
     * 模拟用户到数据库获取权限信息
     * @param username 用户名
     * @param type     0-角色;1-权限
     * @return
     */
    @Override
    public Set<String> getAuthorization(String username, Byte type) {
        Set<String> authorizations = new HashSet<>();
        if (type.equals(GlobalConstants.NUM_ZERO)) {
            // 添加角色
            switch (username) {
                case "admin":
                    authorizations.add("admin");
                    authorizations.add("user");
                    break;
                case "user":
                    authorizations.add("user");
                    break;
                default:
                    return null;
            }
        } else {
            // 添加权限
            switch (username) {
                case "admin":
                    authorizations.add("get_info");
                    authorizations.add("delete_info");
                    break;
                case "user":
                    authorizations.add("get_info");
                    break;
                default:
                    return null;
            }
        }
        return authorizations;
    }
}
