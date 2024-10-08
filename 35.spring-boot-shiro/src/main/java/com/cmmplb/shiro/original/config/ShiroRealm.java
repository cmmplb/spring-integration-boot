package com.cmmplb.shiro.original.config;

import com.cmmplb.shiro.general.entity.User;
import com.cmmplb.shiro.general.service.impl.LoginServiceImpl;
import com.cmmplb.shiro.general.utils.ShiroUtil;
import io.github.cmmplb.core.constants.GlobalConstant;
import io.github.cmmplb.core.utils.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * @author penglibo
 * @date 2021-09-18 15:18:42
 * @since jdk 1.8
 */

@Slf4j
public class ShiroRealm extends AuthorizingRealm {

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("授权");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        String username = ShiroUtil.getUser().getUsername();
        LoginServiceImpl loginService = new LoginServiceImpl();
        info.addRoles(loginService.getAuthorization(username, GlobalConstant.NUM_ZERO));
        info.addStringPermissions(loginService.getAuthorization(username, GlobalConstant.NUM_ONE));
        return info;
    }

    /**
     * 认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info("认证");
        // 获取用户输入的用户名和密码
        String userName = (String) authenticationToken.getPrincipal();
        String password = new String((char[]) authenticationToken.getCredentials());
        password = MD5Util.encode(password); // 加密输入的密码
        User user = new LoginServiceImpl().loadByUsername(userName);
        if (user == null) {
            throw new UnknownAccountException("用户名或密码错误！");
        }
        if (!password.equals(user.getPassword())) {
            throw new IncorrectCredentialsException("用户名或密码错误！"); // 返回模糊错误保证安全性
        }
        if (user.getStatus().equals(GlobalConstant.NUM_ONE)) {
            throw new LockedAccountException("账号已被锁定,请联系管理员！");
        }

        //验证成功开始踢人(清除缓存和Session)
        //ShiroUtils.deleteCache(account,true);
        // 权限缓存
        // redisUtil.set(RedisConstant.AUTHOR_KEY + user.getUserId(), JSONArray.toJSON(user).toString(), RedisConstant.USER_EXPIRE);

        return new SimpleAuthenticationInfo(
                user,  // 缓存principal，后续可通过SecurityUtils.getSubject().getPrincipal()获取
                user.getPassword(),
                // ByteSource.Util.bytes("abc"), // 加盐
                getName()
        );
    }
}
