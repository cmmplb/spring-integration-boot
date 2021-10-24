package com.cmmplb.shiro.service.impl;

import com.alibaba.fastjson.JSON;
import com.cmmplb.common.redis.service.RedisService;
import com.cmmplb.shiro.constants.AuthorizationConstants;
import com.cmmplb.core.exception.CustomException;
import com.cmmplb.core.result.HttpCodeEnum;
import com.cmmplb.core.utils.SpringApplicationUtil;
import com.cmmplb.shiro.config.properties.ShiroProperties;
import com.cmmplb.shiro.constants.HttpConstants;
import com.cmmplb.shiro.dto.LoginDTO;
import com.cmmplb.shiro.entity.User;
import com.cmmplb.shiro.service.LoginService;
import com.cmmplb.shiro.utils.ShiroUtil;
import com.cmmplb.shiro.vo.LoginVO;
import com.cmmplb.web.utils.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        String username = dto.getUsername();
        String password = dto.getPassword();
        Boolean rememberMe = dto.getRememberMe();

        try {
            //验证身份和登陆
            UsernamePasswordToken upToken = new UsernamePasswordToken(username, password/*, rememberMe*/);
            //验证成功进行登录操作
            ShiroUtil.getSubject().login(upToken);
            String token = ShiroUtil.getSubject().getSession().getId().toString();
            // 缓存token信息
            redisService.set(AuthorizationConstants.AUTHORIZATION_UID_CACHE_PREFIX + token, token);
            User user = ShiroUtil.getUser();

            // 设置登录缓存信息
            redisService.set(AuthorizationConstants.AUTHORIZATION_UID_CACHE_PREFIX + token, user.getId(), AuthorizationConstants.AUTHORIZATION_UID_CACHE_EXPIRE_SECONDS);
            redisService.set(AuthorizationConstants.AUTHORIZATION_PREFIX + user.getId(), JSON.toJSONString(token));
            redisService.lpush(AuthorizationConstants.UID_TOKENS_CACHE_PREFIX + user.getId(), token);

            // 这里前后端未分离就重定向到首页
            if (!shiroProperties.getSplit()) {
                ServletUtils.getResponse().sendRedirect(SpringApplicationUtil.path);
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
}
