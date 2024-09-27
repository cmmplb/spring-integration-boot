package com.cmmplb.security.handler;

import com.alibaba.fastjson.JSON;
import com.cmmplb.core.constants.StringConstant;
import com.cmmplb.core.result.HttpCodeEnum;
import com.cmmplb.core.result.ResultUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;

import java.io.IOException;

/**
 * @author penglibo
 * @date 2024-09-02 13:45:28
 * @since jdk 1.8
 */

@Slf4j
public class AccessDeniedHandler implements org.springframework.security.web.access.AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.info("禁止访问-未授权:{}", request.getRequestURI());
        response.setStatus(HttpCodeEnum.FORBIDDEN.getCode());
        response.setContentType(StringConstant.APPLICATION_JSON_UTF_8);
        response.getWriter().write(JSON.toJSONString(ResultUtil.custom(HttpCodeEnum.FORBIDDEN)));
    }
}
