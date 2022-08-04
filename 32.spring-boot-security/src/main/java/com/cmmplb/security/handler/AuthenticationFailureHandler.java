package com.cmmplb.security.handler;

import com.alibaba.fastjson.JSON;
import com.cmmplb.core.constants.StringConstants;
import com.cmmplb.core.exception.CustomException;
import com.cmmplb.core.result.HttpCodeEnum;
import com.cmmplb.core.result.ResultUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author penglibo
 * @date 2021-09-19 23:20:24
 * @since jdk 1.8
 * 登录失败处理
 */

public class AuthenticationFailureHandler implements org.springframework.security.web.authentication.AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
        response.setStatus(HttpCodeEnum.INVALID_REQUEST.getCode());
        response.setContentType(StringConstants.APPLICATION_JSON);
        response.getWriter().write(JSON.toJSONString(ResultUtil.custom(exception.getMessage())));
        // throw new CustomException(exception.getMessage()); // 打印异常
    }
}
