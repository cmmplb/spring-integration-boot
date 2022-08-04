package com.cmmplb.security.handler;

import com.alibaba.fastjson.JSON;
import com.cmmplb.core.constants.StringConstants;
import com.cmmplb.core.exception.CustomException;
import com.cmmplb.core.result.HttpCodeEnum;
import com.cmmplb.core.result.ResultUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author penglibo
 * @date 2021-08-30 17:35:43
 * @since jdk 1.8
 * 权限不足处理
 */

public class AccessDeniedHandler implements org.springframework.security.web.access.AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        response.setStatus(HttpCodeEnum.FORBIDDEN.getCode());
        response.setContentType(StringConstants.APPLICATION_JSON);
        response.getWriter().write(JSON.toJSONString(ResultUtil.custom(HttpCodeEnum.FORBIDDEN)));
        // throw new CustomException(HttpCodeEnum.FORBIDDEN);
    }
}
