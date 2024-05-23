package com.cmmplb.cache.controller;

import com.cmmplb.cache.domain.entity.User;
import com.cmmplb.cache.service.UserService;
import com.cmmplb.core.constants.StringConstants;
import com.cmmplb.core.result.Result;
import com.cmmplb.core.result.ResultUtil;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author penglibo
 * @date 2021-09-13 11:50:10
 * @since jdk 1.8
 */

@Api(tags = "用户管理")
@ApiSupport(order = 2, author = StringConstants.AUTHOR)
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation("新增用户")
    @ApiOperationSupport(order = 1)
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result<Boolean> save(@RequestBody User user) {
        return ResultUtil.success(userService.save(user));
    }

    @ApiOperation("根据id删除用户")
    @ApiOperationSupport(order = 2)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result<Boolean> removeById(@PathVariable(value = "id") Long id) {
        return ResultUtil.success(userService.removeById(id));
    }

    @ApiOperation("根据id修改用户信息")
    @ApiOperationSupport(order = 3)
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result<Boolean> updateById(@PathVariable(value = "id") Long id, @RequestBody User user) {
        user.setId(id);
        return ResultUtil.success(userService.updateById(user));
    }

    @ApiOperation("根据id获取用户")
    @ApiOperationSupport(order = 4)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result<User> getById(@PathVariable(value = "id") Long id) {
        return ResultUtil.success(userService.getById(id));
    }

    @ApiOperation("获取用户列表")
    @ApiOperationSupport(order = 5)
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result<List<User>> list() {
        return ResultUtil.success(userService.list());
    }
}
