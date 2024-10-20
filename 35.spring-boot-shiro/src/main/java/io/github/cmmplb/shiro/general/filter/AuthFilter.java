package io.github.cmmplb.shiro.general.filter;

import com.alibaba.fastjson.JSON;
import io.github.cmmplb.core.constants.StringConstant;
import io.github.cmmplb.core.result.HttpCodeEnum;
import io.github.cmmplb.core.result.ResultUtil;
import io.github.cmmplb.core.utils.ServletUtil;
import io.github.cmmplb.shiro.custom.config.core.AuthToken;
import io.github.cmmplb.shiro.general.constants.AuthorizationConstants;
import io.github.cmmplb.shiro.general.properties.ShiroProperties;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;

/**
 * @author penglibo
 * @date 2021-09-29 16:52:37
 * @since jdk 1.8
 */

@Slf4j
@AllArgsConstructor
public class AuthFilter extends BasicHttpAuthenticationFilter {

    private ShiroProperties shiroProperties;

    public static final String AUTH = "auth";


    public static final String PATTEN = "/**";

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) {
        log.info("onAccessDenied");
        String contextPath = WebUtils.getPathWithinApplication(WebUtils.toHttp(request));
        // 白名单
        if (shiroProperties.getWhitelist().contains(contextPath)) {
            return true;
        }
        AuthenticationToken authenticationToken = this.createToken(request, response);
        if (null == authenticationToken.getPrincipal()) {
            unauthorized();
            return false;
        }
        try {
            this.getSubject(request, response).login(authenticationToken);
        } catch (AuthenticationException e) {
            unauthorized();
            return false;
        }
        return true;
    }

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse servletResponse) {
        return new AuthToken(getToken());
    }

    /**
     * 获取token
     */
    private String getToken() {
        return ServletUtil.getRequest().getHeader(AuthorizationConstants.AUTHORIZATION);
    }

    /**
     * 未登录
     */
    private void unauthorized() {
        try {
            HttpServletResponse response = ServletUtil.getResponse();
            response.setStatus(HttpStatus.OK.value());
            response.setContentType(StringConstant.APPLICATION_JSON);
            response.getWriter().write(JSON.toJSONString(ResultUtil.custom(HttpCodeEnum.UNAUTHORIZED)));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 支持跨域
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader(StringConstant.ACCESS_CONTROL_ALLOW_ORIGIN, StringConstant.ASTERISK);
        httpServletResponse.setHeader(StringConstant.ACCESS_CONTROL_ALLOW_METHODS, StringConstant.ALLOW_METHODS);
        httpServletResponse.setHeader(StringConstant.ACCESS_CONTROL_ALLOW_HEADERS, StringConstant.ALLOW_HEADERS);
        // 跨域时会首先发送一个option请求, 这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(httpServletRequest, httpServletResponse);
    }

}
