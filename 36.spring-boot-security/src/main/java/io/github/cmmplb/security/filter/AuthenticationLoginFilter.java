/*
 * Copyright 2004, 2005, 2006 Acegi Technology Pty Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.cmmplb.security.filter;

import com.alibaba.fastjson.JSON;
import io.github.cmmplb.core.constants.StringConstant;
import io.github.cmmplb.core.result.HttpCodeEnum;
import io.github.cmmplb.core.result.ResultUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;

/**
 * @author penglibo
 * @date 2024-09-03 16:32:22
 * @since jdk 1.8
 * 参照
 * {@link org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter}
 * 基于过滤器认证
 */
public class AuthenticationLoginFilter extends AbstractAuthenticationProcessingFilter {

    public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "username";

    public static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "password";

    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher(
            "/login", HttpMethod.POST.name());

    public AuthenticationLoginFilter() {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException {
        // 这里使用json参数登录，要求使用post请求
        if (checkMethod(request, response)) {
            return null;
        }
        // 获取请求参数
        Object username = request.getParameter(SPRING_SECURITY_FORM_USERNAME_KEY);
        username = (username != null) ? username.toString().trim() : "";
        Object password = request.getParameter(SPRING_SECURITY_FORM_PASSWORD_KEY);
        password = (password != null) ? password : "";
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    private static boolean checkMethod(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (!HttpMethod.POST.name().equals(request.getMethod())) {
            response.setStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
            response.setContentType(StringConstant.APPLICATION_JSON_UTF_8);
            response.getWriter().write(JSON.toJSONString(ResultUtil.custom(
                    HttpCodeEnum.METHOD_NOT_ALLOWED.getCode(),
                    HttpCodeEnum.METHOD_NOT_ALLOWED.getMessage() + ", 请使用POST请求"
            )));
            return true;
        }
        return false;
    }
}
