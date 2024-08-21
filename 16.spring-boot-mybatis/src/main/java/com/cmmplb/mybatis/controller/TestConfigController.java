package com.cmmplb.mybatis.controller;

import com.cmmplb.core.constants.StringConstants;
import com.cmmplb.core.result.Result;
import com.cmmplb.core.result.ResultUtil;
import com.cmmplb.mybatis.entity.User;
import com.cmmplb.mybatis.service.UserService;
import com.cmmplb.mybatis.vo.UserInfoVO;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author penglibo
 * @date 2021-08-17 16:06:21
 * @since jdk 1.8
 * 测试相关配置功能
 */

@Api(tags = "测试相关配置功能")
@ApiSupport(order = 1, author = StringConstants.AUTHOR)
@RestController
@RequestMapping("/test/config")
public class TestConfigController {

    @Autowired
    private UserService userService;

    @ApiOperation("测试二级缓存")
    @ApiOperationSupport(order = 1)
    @RequestMapping(value = "/two/cache", method = RequestMethod.GET)
    public Result<Boolean> twoCache() {
        for (int i = 0; i < 3; i++) {
            System.out.println(userService.getById(1L));
        }
        return ResultUtil.success(true);
    }

    @ApiOperation("测试当前对象id是否注入")
    @ApiOperationSupport(order = 2)
    @RequestMapping(value = "/current", method = RequestMethod.GET)
    public Result<List<User>> testCurrentUserId() {
        return ResultUtil.success(userService.testCurrentUserId());
    }

    // @Retry // 失败重试机制
    @ApiOperation("测试乐观锁插件")
    @ApiOperationSupport(order = 3)
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result<Boolean> updateById(@PathVariable(value = "id") Long id, @RequestBody User user) {
        user.setId(id);
        return ResultUtil.success(userService.updateById(user));
    }

    @ApiOperation("测试一对多映射-子查询方式") // 可用于查询分页列表
    @ApiOperationSupport(order = 4)
    @RequestMapping(value = "/one/many/sub/query/{id}", method = RequestMethod.GET)
    public Result<UserInfoVO> getTestOneMany2SubQuery(@PathVariable(value = "id") Long id) {
        return ResultUtil.success(userService.getTestOneMany2SubQuery(id));
    }

    @ApiOperation("测试一对多映射-字段映射方式") // 分页不可用-会导致总条数不对应
    @ApiOperationSupport(order = 5)
    @RequestMapping(value = "/one/many/field/mapping/{id}", method = RequestMethod.GET)
    public Result<UserInfoVO> getTestOneMany2FieldMapping(@PathVariable(value = "id") Long id) {
        return ResultUtil.success(userService.getTestOneMany2FieldMapping(id));
    }


}
