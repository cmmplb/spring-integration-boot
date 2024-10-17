package io.github.cmmplb.activiti.controller;

import io.github.cmmplb.activiti.service.UserService;
import io.github.cmmplb.activiti.vo.UserInfoVO;
import io.github.cmmplb.core.result.Result;
import io.github.cmmplb.core.result.ResultUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author penglibo
 * @date 2023-11-21 14:24:00
 * @since jdk 1.8
 */

@Schema(name = "用户管理")
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "获取用户列表")
    @GetMapping(value = "/list")
    public Result<List<UserInfoVO>> getList() {
        return ResultUtil.success(userService.getList());
    }
}
