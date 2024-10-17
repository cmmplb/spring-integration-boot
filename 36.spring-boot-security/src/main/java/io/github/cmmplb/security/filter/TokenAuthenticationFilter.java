package io.github.cmmplb.security.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import io.github.cmmplb.core.utils.StringUtil;
import io.github.cmmplb.redis.service.RedisService;
import io.github.cmmplb.security.constants.RedisConstant;
import io.github.cmmplb.security.domain.convert.UserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

/**
 * @author penglibo
 * @date 2024-09-03 16:32:22
 * @since jdk 1.8
 * 自定义token验证过滤器，根据token获取用户信息后放入到SecurityContext上下文中
 */

@Slf4j
public class TokenAuthenticationFilter extends BasicAuthenticationFilter {

    private final RedisService redisService;

    public TokenAuthenticationFilter(AuthenticationManager authenticationManager, RedisService redisService) {
        super(authenticationManager);
        this.redisService = redisService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 获取请求头中的token
        String token = request.getHeader("token");
        if (StringUtil.isEmpty(token)) {
            // 交给其他的过滤器处理
            chain.doFilter(request, response);
            return;
        }
        // 提取token
        Object o = redisService.get(RedisConstant.TOKEN_PREFIX + token);
        if (null != o) {
            UserDetails userDetails = JSON.parseObject(o.toString(), new TypeReference<UserDetails>() {
            });
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            // 将用户信息放入到SecurityContext中
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
        chain.doFilter(request, response);
    }
}
