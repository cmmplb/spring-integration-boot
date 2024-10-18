package io.github.cmmplb.shiro.custom.config;

import com.alibaba.fastjson.JSON;
import io.github.cmmplb.core.constants.GlobalConstant;
import io.github.cmmplb.core.utils.ThreadUtil;
import io.github.cmmplb.redis.service.RedisService;
import io.github.cmmplb.shiro.custom.config.core.AuthToken;
import io.github.cmmplb.shiro.general.beans.UserBean;
import io.github.cmmplb.shiro.general.constants.AuthorizationConstants;
import io.github.cmmplb.shiro.general.service.impl.LoginServiceImpl;
import io.github.cmmplb.shiro.general.utils.ShiroUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
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
@AllArgsConstructor
public class ShiroRealm extends AuthorizingRealm {

    private RedisService redisService;

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
        String token = authenticationToken.getCredentials().toString();
        log.info("认证:" + token);
        String tokenUidCacheKey = AuthorizationConstants.AUTH_UID_CACHE_PREFIX + token;
        Object uid = redisService.get(tokenUidCacheKey);
        if (null != uid) {
            // 异步读取用户多端token
            ThreadUtil.executeTask(() -> {
                // 取出用户最早存入的token
                String tokenOld = (String) redisService.rPop(AuthorizationConstants.UID_TOKENS_CACHE_PREFIX + uid);
                if (redisService.hasKey(AuthorizationConstants.AUTH_UID_CACHE_PREFIX + tokenOld)) {
                    redisService.lPush(AuthorizationConstants.UID_TOKENS_CACHE_PREFIX + uid, tokenOld);
                }
            });
            Object userInfo = redisService.get(AuthorizationConstants.AUTH_UID_INFO_PREFIX + uid.toString());
            if (null != userInfo) {
                // 解析用户信息
                UserBean userBean = JSON.parseObject(userInfo.toString(), UserBean.class);
                if (null != userBean) {
                    redisService.expire(tokenUidCacheKey, AuthorizationConstants.AUTH_CACHE_EXPIRE_SECONDS);
                    return new SimpleAuthenticationInfo(
                            // 缓存principal, 后续可通过SecurityUtils.getSubject().getPrincipal()获取
                            userBean,
                            token,
                            userBean.getUsername()
                    );
                }
            }
        }
        return null;
    }

    /**
     * 必须重写此方法, 不然Shiro会报错
     * org.apache.shiro.authc.pam.UnsupportedTokenException: Realm [io.github.cmmplb.shiro.config.ShiroRealm@83829d]
     * does not support authentication token [io.github.cmmplb.shiro.general.filter.AuthFilter$1@3b562275].
     * Please ensure that the appropriate Realm implementation is configured correctly or that the realm accepts
     * AuthenticationTokens of this type
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof AuthToken;
    }
}
