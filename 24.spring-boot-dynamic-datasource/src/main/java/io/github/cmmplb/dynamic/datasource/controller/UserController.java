package io.github.cmmplb.dynamic.datasource.controller;

import io.github.cmmplb.core.result.Result;
import io.github.cmmplb.core.result.ResultUtil;
import io.github.cmmplb.dynamic.datasource.entity.User;
import io.github.cmmplb.dynamic.datasource.service.UserService;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author penglibo
 * @date 2021-08-06 14:22:35
 * @since jdk 1.8
 */

@Api(tags = "用户")
@ApiSort(2)
@ApiSupport(order = 2, author = "张三")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    // 测试自定义切面方式切换数据源是否生效, 更新走master、查询走slave_1

    @PutMapping("/{id}")
    @ApiOperation("根据id更新")
    public Result<Object> updateById(User user) {
        return ResultUtil.success(userService.updateInfoById(user));
    }

    @GetMapping("/list")
    @ApiOperation("查询列表")
    public Result<List<User>> getList() {
        return ResultUtil.success(userService.getList());
    }
}
