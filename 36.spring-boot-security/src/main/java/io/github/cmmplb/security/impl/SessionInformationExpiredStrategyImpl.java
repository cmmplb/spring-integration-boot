package io.github.cmmplb.security.impl;

import com.alibaba.fastjson.JSON;
import io.github.cmmplb.core.constants.StringConstant;
import io.github.cmmplb.core.result.HttpCodeEnum;
import io.github.cmmplb.core.result.ResultUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import java.io.IOException;

/**
 * @author penglibo
 * @date 2024-09-03 10:01:38
 * @since jdk 1.8
 * session失效处理
 */

@Slf4j
public class SessionInformationExpiredStrategyImpl implements SessionInformationExpiredStrategy {

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent sessionInformationExpiredEvent) throws IOException, ServletException {
        HttpServletRequest request = sessionInformationExpiredEvent.getRequest();
        HttpServletResponse response = sessionInformationExpiredEvent.getResponse();
        log.info("===并发登录处理===:{}", request.getRequestURI());
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(StringConstant.APPLICATION_JSON_UTF_8);
        response.getWriter().write(JSON.toJSONString(ResultUtil.custom(HttpCodeEnum.UNAUTHORIZED.getCode(), "您的账号已在别处登录")));
        // 跳转到登录页，由于前端使用axios，捕获了异常信息，这里返回上面的具体错误信息，由前端跳转
        // response.sendRedirect(SpringApplicationUtil.path + "/login");
    }
}
