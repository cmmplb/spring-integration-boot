package com.cmmplb.mybatis.controller;

import com.cmmplb.core.constants.StringConstants;
import com.cmmplb.core.result.Result;
import com.cmmplb.core.result.ResultUtil;
import com.cmmplb.core.beans.PageResult;
import com.cmmplb.core.beans.QueryPageBean;
import com.cmmplb.mybatis.entity.UserInfo;
import com.cmmplb.mybatis.service.UserInfoService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author penglibo
 * @date 2021-04-02 00:03:50
 * 用户详情管理
 */

@Api(tags = "用户详情管理")
@ApiSupport(order = 3, author = StringConstants.AUTHOR)
@RestController
@RequestMapping("/user/info")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @ApiOperation("新增用户信息")
    @ApiOperationSupport(order = 1)
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result<Boolean> save(@RequestBody UserInfo userInfo) {
        return ResultUtil.success(userInfoService.save(userInfo));
    }

    @ApiOperation("根据id删除用户信息")
    @ApiOperationSupport(order = 2)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result<Boolean> removeById(@PathVariable(value = "id") Long id) {
        return ResultUtil.success(userInfoService.removeById(id));
    }

    @ApiOperation("根据id修改用户信息")
    @ApiOperationSupport(order = 3)
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result<Boolean> updateById(@PathVariable(value = "id") Long id, @RequestBody UserInfo userInfo) {
        userInfo.setId(id);
        return ResultUtil.success(userInfoService.updateById(userInfo));
    }

    @ApiOperation("获取用户信息列表")
    @ApiOperationSupport(order = 4)
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result<List<UserInfo>> list() {
        return ResultUtil.success(userInfoService.list());
    }

    @ApiOperation("根据id获取用户信息")
    @ApiOperationSupport(order = 5)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result<UserInfo> getById(@PathVariable(value = "id") Long id) {
        return ResultUtil.success(userInfoService.getById(id));
    }

    @ApiOperation("分页获取用户信息")
    @ApiOperationSupport(order = 6)
    @RequestMapping(value = "/paged", method = RequestMethod.GET)
    public Result<PageResult<UserInfo>> getByPaged(QueryPageBean pageBean) {
        return ResultUtil.success(userInfoService.getByPaged(pageBean));
    }
}
