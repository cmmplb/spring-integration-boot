package io.github.cmmplb.security.handler;

import com.alibaba.fastjson.JSON;
import io.github.cmmplb.core.constants.StringConstant;
import io.github.cmmplb.core.result.HttpCodeEnum;
import io.github.cmmplb.core.result.ResultUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;

import java.io.IOException;

/**
 * @author penglibo
 * @date 2024-09-03 09:36:50
 * @since jdk 1.8
 * 登录失败处理
 */

@Slf4j
public class AuthenticationFailureHandler implements org.springframework.security.web.authentication.AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
        log.info("===登录失败处理===:{}", exception.getMessage());
        response.setStatus(HttpCodeEnum.UNAUTHORIZED.getCode());
        response.setContentType(StringConstant.APPLICATION_JSON_UTF_8);
        String result;
        if (exception instanceof SessionAuthenticationException) {
            // 限制登录异常
            result = JSON.toJSONString(ResultUtil.custom(HttpCodeEnum.UNAUTHORIZED.getCode(), "该账号已在其他地方登录"));
        } else {
            result = JSON.toJSONString(ResultUtil.custom(HttpCodeEnum.UNAUTHORIZED.getCode(), exception.getMessage()));
        }
        response.getWriter().write(result);
    }
}
