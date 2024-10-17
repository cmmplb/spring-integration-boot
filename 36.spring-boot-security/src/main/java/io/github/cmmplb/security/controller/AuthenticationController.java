package io.github.cmmplb.security.controller;

import com.alibaba.fastjson.JSON;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.github.cmmplb.core.constants.StringConstant;
import io.github.cmmplb.core.result.Result;
import io.github.cmmplb.core.result.ResultUtil;
import io.github.cmmplb.core.utils.ObjectUtil;
import io.github.cmmplb.core.utils.UUIDUtil;
import io.github.cmmplb.redis.service.RedisService;
import io.github.cmmplb.security.constants.RedisConstant;
import io.github.cmmplb.security.domain.convert.UserDetails;
import io.github.cmmplb.security.domain.dto.LoginDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author penglibo
 * @date 2021-09-19 22:43:56
 * @since jdk 1.8
 */

@Slf4j
@Tag(name = "认证管理")
@ApiSupport(order = 3, author = StringConstant.AUTHOR)
@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisService redisService;

    /**
     * 自定义登录，逻辑参照
     * {@link org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter}
     * 也可以基于过滤器来实现自定义登录逻辑
     * {@link io.github.cmmplb.security.filter.AuthenticationLoginFilter}
     */
    @Operation(summary = "自定义登录")
    @ApiOperationSupport(order = 1)
    @PostMapping("/login")
    public Result<String> login(@RequestBody LoginDTO dto) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword());
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        log.info("authenticate:{}", authenticate);
        if (null == authenticate) {
            return ResultUtil.custom(401, "登录失败");
        }
        Object principal = authenticate.getPrincipal();
        UserDetails userDetails = ObjectUtil.cast(principal);
        String token = UUIDUtil.uuidTrim();
        redisService.set(RedisConstant.TOKEN_PREFIX + token, JSON.toJSONString(userDetails), 600);
        return ResultUtil.success(token);
    }
}