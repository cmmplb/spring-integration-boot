package com.cmmplb.sharding.sphere.controller;

import com.cmmplb.core.beans.PageResult;
import com.cmmplb.core.beans.QueryPageBean;
import com.cmmplb.core.result.Result;
import com.cmmplb.core.result.ResultUtil;
import com.cmmplb.sharding.sphere.entity.User;
import com.cmmplb.sharding.sphere.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author penglibo
 * @date 2021-04-02 00:03:50
 */

@Tag(name = "用户管理")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @Operation(summary = "获取用户列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result<List<User>> list() {
        return ResultUtil.success(userService.list());
    }

    @Operation(summary = "根据id获取用户信息")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result<User> getById(@PathVariable(value = "id") Long id) {
        return ResultUtil.success(userService.getById(id));
    }

    @Operation(summary = "分页获取用户信息")
    @RequestMapping(value = "/paged", method = RequestMethod.GET)
    public Result<PageResult<User>> getByPaged(QueryPageBean pageBean) {
        return ResultUtil.success(userService.getByPaged(pageBean));
    }
}
