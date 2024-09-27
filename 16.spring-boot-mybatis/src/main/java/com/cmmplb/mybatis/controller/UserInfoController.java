package com.cmmplb.mybatis.controller;

import com.cmmplb.core.beans.PageResult;
import com.cmmplb.core.beans.QueryPageBean;
import com.cmmplb.core.constants.StringConstant;
import com.cmmplb.core.result.Result;
import com.cmmplb.core.result.ResultUtil;
import com.cmmplb.mybatis.entity.UserInfo;
import com.cmmplb.mybatis.service.UserInfoService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author penglibo
 * @date 2021-04-02 00:03:50
 * 用户详情管理
 */

@Tag(name = "用户详情管理")
// @ApiSupport > @ApiSort > @Api  -  排序的规则是倒序
@ApiSort(3)
// 作者,方法名上ApiOperationSupport.author没有则取类名声明的作者
@ApiSupport(order = 3, author = StringConstant.AUTHOR)
@RestController
@RequestMapping("/user/info")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @Operation(summary = "新增用户信息", description = "新增用户信息")
    @ApiOperationSupport(order = 1)
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result<Boolean> save(@RequestBody UserInfo userInfo) {
        return ResultUtil.success(userInfoService.save(userInfo));
    }

    @Operation(summary = "根据id删除用户信息")
    @ApiOperationSupport(order = 2)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result<Boolean> removeById(@PathVariable(value = "id") Long id) {
        return ResultUtil.success(userInfoService.removeById(id));
    }

    @Operation(summary = "根据id修改用户信息")
    @ApiOperationSupport(order = 3)
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result<Boolean> updateById(@PathVariable(value = "id") Long id, @RequestBody UserInfo userInfo) {
        userInfo.setId(id);
        return ResultUtil.success(userInfoService.updateById(userInfo));
    }

    @Operation(summary = "获取用户信息列表")
    @ApiOperationSupport(order = 4)
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result<List<UserInfo>> list() {
        return ResultUtil.success(userInfoService.list());
    }

    @Operation(summary = "根据id获取用户信息")
    @ApiOperationSupport(order = 5)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result<UserInfo> getById(@PathVariable(value = "id") Long id) {
        return ResultUtil.success(userInfoService.getById(id));
    }

    @Operation(summary = "分页获取用户信息")
    @ApiOperationSupport(order = 6)
    @RequestMapping(value = "/paged", method = RequestMethod.GET)
    public Result<PageResult<UserInfo>> getByPaged(QueryPageBean pageBean) {
        return ResultUtil.success(userInfoService.getByPaged(pageBean));
    }
}
