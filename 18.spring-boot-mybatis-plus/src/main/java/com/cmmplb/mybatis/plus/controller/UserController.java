package com.cmmplb.mybatis.plus.controller;


import io.github.cmmplb.core.constants.StringConstant;
import io.github.cmmplb.core.result.Result;
import io.github.cmmplb.core.result.ResultUtil;
import io.github.cmmplb.core.beans.PageResult;
import io.github.cmmplb.core.beans.QueryPageBean;
import com.cmmplb.mybatis.plus.entity.User;
import com.cmmplb.mybatis.plus.service.UserService;
import com.cmmplb.mybatis.plus.vo.UserInfoVO;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author penglibo
 * @date 2021-04-02 00:03:50
 */

@Api(tags = "用户管理")
@Slf4j
@ApiSupport(order = 2, author = StringConstant.AUTHOR)
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "新增用户")
    @ApiOperationSupport(order = 1)
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result<Boolean> save(@RequestBody User user) {
        log.info("--------------------------新增用户---------------------------------");
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        return ResultUtil.success(userService.save(user));
    }

    @ApiOperation(value = "批量新增用户")
    @ApiOperationSupport(order = 2)
    @RequestMapping(value = "/save/batch", method = RequestMethod.POST)
    public Result<Boolean> save(@RequestBody List<User> users) {
        log.info("--------------------------批量新增用户---------------------------------");
        if (!CollectionUtils.isEmpty(users)) {
            users.forEach(user -> {
                user.setCreateTime(new Date());
                user.setUpdateTime(new Date());
            });
            return ResultUtil.success(userService.saveBatch(users));
        }
        return ResultUtil.success();
    }

    @ApiOperation(value = "根据id删除用户")
    @ApiOperationSupport(order = 3)
    @ApiImplicitParam(name = "id", paramType = "query", value = "用户id", required = true, dataType = "Long", dataTypeClass = Long.class, example = "1")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result<Boolean> removeById(@PathVariable(value = "id") Long id) {
        log.info("--------------------------根据id删除用户---------------------------------");
        return ResultUtil.success(userService.removeById(id));
    }

    @ApiOperation(value = "根据id修改用户信息")
    @ApiOperationSupport(order = 4)
    @ApiImplicitParam(name = "id", paramType = "query", value = "用户id", required = true, dataType = "Long", dataTypeClass = Long.class, example = "1")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result<Boolean> updateById(@PathVariable(value = "id") Long id, @RequestBody User user) {
        log.info("--------------------------根据id修改用户信息---------------------------------");
        user.setId(id);
        user.setUpdateTime(new Date());
        return ResultUtil.success(userService.updateById(user));
    }

    @ApiOperation(value = "获取用户列表")
    @ApiOperationSupport(order = 5)
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result<List<User>> list() {
        log.info("--------------------------获取用户列表---------------------------------");
        return ResultUtil.success(userService.list());
    }

    @ApiOperation(value = "根据id获取用户信息")
    @ApiOperationSupport(order = 6)
    @ApiImplicitParam(name = "id", paramType = "query", value = "用户id", required = true, dataType = "Long", dataTypeClass = Long.class, example = "1")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result<User> getById(@PathVariable(value = "id") Long id) {
        log.info("--------------------------根据id获取用户信息---------------------------------");
        return ResultUtil.success(userService.getById(id));
    }

    @ApiOperation(value = "分页获取用户信息")
    @ApiOperationSupport(order = 7)
    @RequestMapping(value = "/paged", method = RequestMethod.GET)
    public Result<PageResult<User>> getByPaged(QueryPageBean pageBean) {
        log.info("--------------------------分页获取用户信息---------------------------------");
        for (int i = 0; i < 3; i++) {
            userService.getById(1);
        }
        return ResultUtil.success(userService.getByPaged(pageBean));
    }

    @ApiOperation("测试一对多映射-子查询方式") // 可用于查询分页列表
    @ApiOperationSupport(order = 8)
    @RequestMapping(value = "/one/many/sub/query/{id}", method = RequestMethod.GET)
    public Result<UserInfoVO> getTestOneMany2SubQuery(@PathVariable(value = "id") Long id) {
        return ResultUtil.success(userService.getTestOneMany2SubQuery(id));
    }

    @ApiOperation("测试一对多映射-字段映射方式") // 分页不可用-会导致总条数不对应
    @ApiOperationSupport(order = 9)
    @RequestMapping(value = "/one/many/field/mapping/{id}", method = RequestMethod.GET)
    public Result<UserInfoVO> getTestOneMany2FieldMapping(@PathVariable(value = "id") Long id) {
        return ResultUtil.success(userService.getTestOneMany2FieldMapping(id));
    }
}
