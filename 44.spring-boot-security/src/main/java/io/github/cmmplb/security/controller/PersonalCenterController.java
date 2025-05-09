package io.github.cmmplb.security.controller;

import io.github.cmmplb.core.constants.StringConstant;
import io.github.cmmplb.core.result.Result;
import io.github.cmmplb.core.result.ResultUtil;
import io.github.cmmplb.core.utils.ObjectUtil;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * @author penglibo
 * @date 2021-09-20 00:16:57
 * @since jdk 1.8
 */

@Api(tags = "个人中心管理")
@ApiSupport(order = 2, author = StringConstant.AUTHOR)
@RestController
@RequestMapping("/personal/center")
public class PersonalCenterController {

    @ApiOperation("查询principal")
    @ApiOperationSupport(order = 1)
    @GetMapping("/principal")
    public Result<Principal> getPrincipal(Principal principal) {
        return ResultUtil.success(principal);
    }

    @ApiOperation("查询authentication")
    @ApiOperationSupport(order = 2)
    @GetMapping("/authentication")
    public Result<Authentication> getAuthentication(Authentication authentication) {
        return ResultUtil.success(authentication);
    }

    @ApiOperation("查询request里的principal")
    @ApiOperationSupport(order = 3)
    @GetMapping("/request/principal")
    public Result<Principal> getUserPrincipal(HttpServletRequest request) {
        return ResultUtil.success(request.getUserPrincipal());
    }

    @ApiOperation("查询security上下文中的principal")
    @ApiOperationSupport(order = 4)
    @GetMapping("/context/principal")
    public Result<User> getPrincipal() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResultUtil.success(ObjectUtil.cast(principal));
    }
}
