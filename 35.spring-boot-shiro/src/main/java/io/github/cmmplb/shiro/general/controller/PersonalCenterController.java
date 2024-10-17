package io.github.cmmplb.shiro.general.controller;

import io.github.cmmplb.core.result.Result;
import io.github.cmmplb.core.result.ResultUtil;
import io.github.cmmplb.shiro.general.utils.ShiroUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author penglibo
 * @date 2021-09-20 00:16:57
 * @since jdk 1.8
 */

@RestController
@RequestMapping("/personal/center")
public class PersonalCenterController {

    @GetMapping("/principal")
    public Result<Principal> getPrincipal(Principal principal) {
        return ResultUtil.success(principal);
    }

    @GetMapping("/request/principal")
    public Result<Principal> getUserPrincipal(HttpServletRequest request) {
        return ResultUtil.success(request.getUserPrincipal());
    }

    @GetMapping("/shiro/principal")
    public Result<Object> getPrincipal() {
        return ResultUtil.success(ShiroUtil.getSubject().getPrincipal());
    }
}
