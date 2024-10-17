package io.github.cmmplb.dynamic.datasource.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.github.cmmplb.core.constants.StringConstant;
import io.github.cmmplb.core.result.Result;
import io.github.cmmplb.core.result.ResultUtil;
import io.github.cmmplb.dynamic.datasource.entity.User;
import io.github.cmmplb.dynamic.datasource.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author penglibo
 * @date 2021-08-06 14:22:35
 * @since jdk 1.8
 */

@Tag(name = "用户")
// @ApiSupport > @ApiSort > @Api  -  排序的规则是倒序
@ApiSort(2)
// 作者,方法名上ApiOperationSupport.author没有则取类名声明的作者
@ApiSupport(order = 2, author = StringConstant.AUTHOR)
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    // 测试自定义切面方式切换数据源是否生效, 更新走master、查询走slave_1

    @PutMapping("/{id}")
    @Operation(summary = "根据id更新", description = "根据id更新")
    @ApiOperationSupport(order = 1)
    public Result<Object> updateById(@RequestBody User user) {
        return ResultUtil.success(userService.updateInfoById(user));
    }

    @GetMapping("/list")
    @Operation(summary = "查询列表")
    @ApiOperationSupport(order = 2)
    public Result<List<User>> getList() {
        return ResultUtil.success(userService.getList());
    }
}