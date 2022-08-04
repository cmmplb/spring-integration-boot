package com.cmmplb.shiro.general.controller;

import com.cmmplb.core.result.Result;
import com.cmmplb.core.result.ResultUtil;
import org.apache.shiro.authz.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author penglibo
 * @date 2021-09-20 20:10:46
 * @since jdk 1.8
 */

@RestController
@RequestMapping("/annotation")
public class AnnotationController {

    @GetMapping("/none")
    public Result<String> none() {
        System.out.println("none");
        return ResultUtil.success();
    }

    @GetMapping("/guest")
    @RequiresUser // 表示当前Subject没有身份验证或通过记住我登录过，即是游客身份。
    public Result<String> requiresGuest() {
        return ResultUtil.success();
    }

    @GetMapping("/authentication")
    @RequiresAuthentication // 表示当前Subject已经通过login进行了身份验证；即Subject.isAuthenticated()返回true。
    public Result<String> requiresAuthentication() {
        return ResultUtil.success();
    }

    @GetMapping("/user")
    @RequiresUser // 表示当前Subject已经身份验证或者通过记住我登录的。
    public Result<String> requiresUser() {
        return ResultUtil.success();
    }

    @GetMapping("/has/role")
    @RequiresRoles(value = "admin", logical = Logical.OR) // 表示当前Subject需要角色admin
    public Result<String> hasRole() {
        return ResultUtil.success();
    }

    @GetMapping("/has/permission")
    @RequiresPermissions(value = {"get_info", "delete_info"}, logical = Logical.AND) // 表示当前Subject需要权限get_info
    public Result<String> hasPermission() {
        return ResultUtil.success();
    }

    @GetMapping("/has/all")
    @RequiresRoles(value = {"admin", "user"}, logical = Logical.AND)
    @RequiresPermissions(value = {"get_info", "delete_info"}, logical = Logical.AND)
    public Result<String> getInfo() {
        return ResultUtil.success();
    }
}
