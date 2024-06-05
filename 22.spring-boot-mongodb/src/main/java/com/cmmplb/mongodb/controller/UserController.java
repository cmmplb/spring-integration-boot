package com.cmmplb.mongodb.controller;

import com.cmmplb.core.beans.PageResult;
import com.cmmplb.core.result.Result;
import com.cmmplb.core.result.ResultUtil;
import com.cmmplb.mongodb.dto.UserPageQueryDTO;
import com.cmmplb.mongodb.entity.User;
import com.cmmplb.mongodb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author penglibo
 * @date 2021-09-14 10:06:58
 * @since jdk 1.8
 */

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result<Boolean> save(@RequestBody User user) {
        return ResultUtil.success(userService.save(user));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result<Boolean> removeById(@PathVariable(value = "id") String id) {
        return ResultUtil.success(userService.removeById(id));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result<Boolean> updateById(@PathVariable(value = "id") String id, @RequestBody User user) {
        user.set_id(id);
        return ResultUtil.success(userService.updateById(user));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result<User> getById(@PathVariable(value = "id") String id) {
        return ResultUtil.success(userService.getById(id));
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result<List<User>> list() {
        return ResultUtil.success(userService.list());
    }

    @RequestMapping(value = "/condition", method = RequestMethod.GET)
    public Result<List<User>> condition(UserPageQueryDTO dto) {
        return ResultUtil.success(userService.condition(dto));
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public Result<PageResult<User>> page(UserPageQueryDTO dto) {
        return ResultUtil.success(userService.page(dto));
    }
}
