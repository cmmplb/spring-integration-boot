package io.github.cmmplb.cache.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.github.cmmplb.cache.domain.entity.User;
import io.github.cmmplb.cache.service.UserService;
import io.github.cmmplb.core.constants.StringConstant;
import io.github.cmmplb.core.result.Result;
import io.github.cmmplb.core.result.ResultUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author penglibo
 * @date 2021-09-13 11:50:10
 * @since jdk 1.8
 */

@Tag(name = "用户管理")
// @ApiSupport > @ApiSort > @Api  -  排序的规则是倒序
@ApiSort(2)
// 作者,方法名上ApiOperationSupport.author没有则取类名声明的作者
@ApiSupport(order = 2, author = StringConstant.AUTHOR)
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "新增用户", description = "新增用户")
    @ApiOperationSupport(order = 1)
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result<Boolean> save(@RequestBody User user) {
        return ResultUtil.success(userService.save(user));
    }

    @Operation(summary = "根据id删除用户")
    @ApiOperationSupport(order = 2)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result<Boolean> removeById(@PathVariable(value = "id") Long id) {
        return ResultUtil.success(userService.removeById(id));
    }

    @Operation(summary = "根据id修改用户信息")
    @ApiOperationSupport(order = 3)
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result<Boolean> updateById(@PathVariable(value = "id") Long id, @RequestBody User user) {
        user.setId(id);
        return ResultUtil.success(userService.updateById(user));
    }

    @Operation(summary = "根据id获取用户")
    @ApiOperationSupport(order = 4)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result<User> getById(@PathVariable(value = "id") Long id) {
        return ResultUtil.success(userService.getById(id));
    }

    @Operation(summary = "获取用户列表")
    @ApiOperationSupport(order = 5)
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result<List<User>> list() {
        return ResultUtil.success(userService.list());
    }
}

