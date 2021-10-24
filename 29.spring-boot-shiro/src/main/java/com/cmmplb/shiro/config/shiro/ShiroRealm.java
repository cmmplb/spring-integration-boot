package com.cmmplb.shiro.config.shiro;

import com.cmmplb.core.constants.GlobalConstants;
import com.cmmplb.shiro.entity.User;
import com.cmmplb.shiro.utils.MD5Util;
import com.cmmplb.shiro.utils.ShiroUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashSet;
import java.util.Set;

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
        info.addRoles(getAuthorization(username, GlobalConstants.NUM_ZERO));
        info.addStringPermissions(getAuthorization(username, GlobalConstants.NUM_ONE));
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
        User user = loadByUserName(userName);
        if (user == null) {
            throw new UnknownAccountException("用户名或密码错误！");
        }
        if (!password.equals(user.getPassword())) {
            throw new IncorrectCredentialsException("用户名或密码错误！"); // 返回模糊错误保证安全性
        }
        if (user.getStatus().equals(GlobalConstants.NUM_ONE)) {
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

    /**
     * 模拟用户名到数据库查询用户信息
     * @param username 用户名
     * @return user
     */
    private User loadByUserName(String username) {
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
    private Set<String> getAuthorization(String username, Byte type) {
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
            }
        }
        return authorizations;
    }
}
