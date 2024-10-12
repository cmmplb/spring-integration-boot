package com.cmmplb.map.struct.basic.controller;

import io.github.cmmplb.core.result.Result;
import io.github.cmmplb.core.result.ResultUtil;
import com.cmmplb.map.struct.basic.service.UserService;
import com.cmmplb.map.struct.basic.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author penglibo
 * @date 2021-09-10 17:38:33
 * @since jdk 1.8
 * 基础使用
 */

@RestController
@RequestMapping("/basic/user")
public class BasicUserController {

    @Autowired
    private UserService userService;

    @GetMapping("/one")
    public Result<UserVO> getOne() {
        return ResultUtil.success(userService.getOne());
    }

    @GetMapping("/list")
    public Result<List<UserVO>> getList() {
        return ResultUtil.success(userService.getList());
    }

    @GetMapping("/one/mapping")
    public Result<UserVO> getOneMapping() {
        return ResultUtil.success(userService.getOneMapping());
    }

    @GetMapping("/many/mapping")
    public Result<UserVO> getManyMapping() {
        return ResultUtil.success(userService.getManyMapping());
    }
}
