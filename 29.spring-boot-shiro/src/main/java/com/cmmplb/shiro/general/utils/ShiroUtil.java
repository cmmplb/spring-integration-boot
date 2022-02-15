package com.cmmplb.shiro.general.utils;

import com.cmmplb.core.utils.ObjectUtil;
import com.cmmplb.shiro.general.beans.UserBean;
import com.cmmplb.shiro.general.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;

/**
 * @author penglibo
 * @date 2021-09-27 11:55:50
 * @since jdk 1.8
 */

public class ShiroUtil {

    /**
     * 私有构造器
     **/
    private ShiroUtil() {
    }

    /**
     * 获取当前 Subject
     * @return Subject
     */
    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    /**
     * 切换身份登录后动态更改subject的用户属性
     */
    public static void updateCacheUser(User user) {
        Subject subject = getSubject();
        subject.runAs(new SimplePrincipalCollection(user, subject.getPrincipals().getRealmNames().iterator().next()));
    }

    /**
     * 获取当前用户Session
     */
    public static Session getSession() {
        return getSubject().getSession();
    }

    /**
     * 用户登出
     */
    public static void logout() {
        getSubject().logout();
    }

    /**
     * 获取当前用户信息
     */
    public static UserBean getUser() {
        return ObjectUtil.cast(getSubject().getPrincipal());
    }
}
