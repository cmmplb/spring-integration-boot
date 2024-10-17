package io.github.cmmplb.security.handler;

import com.alibaba.fastjson.JSON;
import io.github.cmmplb.core.constants.StringConstant;
import io.github.cmmplb.core.result.HttpCodeEnum;
import io.github.cmmplb.core.result.Result;
import io.github.cmmplb.core.result.ResultUtil;
import io.github.cmmplb.core.utils.SpringApplicationUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author penglibo
 * @date 2021-10-19 14:12:51
 * @since jdk 1.8
 * 资源异常细节处理
 */

@Slf4j
public class ResourceAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setCharacterEncoding(StringConstant.UTF8);
        response.setContentType(StringConstant.APPLICATION_JSON);
        response.setStatus(HttpCodeEnum.UNAUTHORIZED.getCode());
        Result<String> result = ResultUtil.custom(HttpCodeEnum.UNAUTHORIZED);
        if (authException != null) {
            // 设置一个data显示无效的token
            result.setData(authException.getMessage());
        }
        // 针对令牌过期
        if (authException instanceof InsufficientAuthenticationException) {
            result.setMsg(HttpCodeEnum.UNAUTHORIZED.getMessage());
        }
        // 针对凭证错误过期
        if (authException instanceof BadCredentialsException) {
            result.setMsg(HttpCodeEnum.BAD_CREDENTIALS.getMessage());
        }
        PrintWriter printWriter = response.getWriter();
        printWriter.append(JSON.toJSONString(result));
        log.info("{},{}", request.getRequestURI(), result.getMsg());
        // 如果整合了登录页面, 跳转到登录页
        response.sendRedirect(SpringApplicationUtil.path + "/login");
    }
}
