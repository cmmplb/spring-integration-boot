package com.cmmplb.shiro.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author penglibo
 * @date 2021-09-29 16:52:37
 * @since jdk 1.8
 * shiro自定义拦截器
 */

@Slf4j
public class AuthenticationFilter extends FormAuthenticationFilter {

    /**
     * 如果在这里返回了false，请求onAccessDenied（） 判断是否可以登录到系统
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        //getSubject(request, response).login(token);
        // 如果没有抛出异常则代表登入成功，返回true
        log.info("isAccessAllowed");
        return true;
    }

    /**
     * 当isAccessAllowed（）返回false时，登录被拒绝，进入此接口进行异常处理
     * @param request
     * @param response
     * @return
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) {
        log.info("onAccessDenied");
        return false;
    }
}
