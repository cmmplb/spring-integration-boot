package io.github.cmmplb.security.handler;

import com.alibaba.fastjson.JSON;
import io.github.cmmplb.core.constants.StringConstant;
import io.github.cmmplb.core.result.ResultUtil;
import io.github.cmmplb.core.utils.SpringApplicationUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;

import java.io.IOException;

/**
 * @author penglibo
 * @date 2024-09-03 09:49:10
 * @since jdk 1.8
 * 退出登录处理
 */

@Slf4j
public class LogoutSuccessHandler implements org.springframework.security.web.authentication.logout.LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("===退出登录处理===:{}", request.getRequestURI());
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(StringConstant.APPLICATION_JSON_UTF_8);
        response.getWriter().write(JSON.toJSONString(ResultUtil.success()));
        // 退出之后跳转到登录页
        response.sendRedirect(SpringApplicationUtil.path + "/login");
    }
}

