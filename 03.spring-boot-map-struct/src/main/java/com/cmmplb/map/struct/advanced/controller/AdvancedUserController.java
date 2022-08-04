package com.cmmplb.map.struct.advanced.controller;

import com.cmmplb.core.result.Result;
import com.cmmplb.core.result.ResultUtil;
import com.cmmplb.map.struct.advanced.service.AdvancedUserService;
import com.cmmplb.map.struct.advanced.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author penglibo
 * @date 2021-09-10 17:38:33
 * @since jdk 1.8
 */

@RestController
@RequestMapping("/advanced/user")
public class AdvancedUserController {

    @Autowired
    private AdvancedUserService userService;

    @GetMapping("/one")
    public Result<UserVO> getOne() {
        return ResultUtil.success(userService.getOne());
    }

    @GetMapping("/list")
    public Result<List<UserVO>> getList() {
        return ResultUtil.success(userService.getList());
    }
}
