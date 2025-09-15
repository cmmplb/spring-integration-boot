package io.github.cmmplb.mybatis.plus.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.cmmplb.core.constants.StringConstant;
import io.github.cmmplb.core.result.Result;
import io.github.cmmplb.core.result.ResultUtil;
import io.github.cmmplb.mybatis.plus.entity.User;
import io.github.cmmplb.mybatis.plus.service.UserService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author penglibo
 * @date 2021-04-12 14:36:09
 */

@Api(tags = "用户管理-lambda查询方式")
@Slf4j
@ApiSupport(order = 1, author = StringConstant.AUTHOR)
@RestController
@RequestMapping("/lambda/user")
public class LambdaUserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "lambda查询列表")
    @ApiOperationSupport(order = 1)
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result<Map<String, Object>> list() {
        Map<String, Object> res = new HashMap<>();
        log.info("--------------------------lambda查询列表---------------------------------");
        res.put("男生列表", userService.lambdaQuery().eq(User::getSex, 1).list());
        res.put("女生列表-LambdaQueryWrapper", userService.list(new LambdaQueryWrapper<User>().eq(User::getSex, 0)));
        // res.put("女生列表-LambdaQueryChainWrapper", userService.list(new LambdaQueryChainWrapper<User>(userService.getBaseMapper()).eq(User::getSex, 0)));
        res.put("女生列表-启用", userService.list(Wrappers.<User>lambdaQuery().eq(User::getSex, 0).eq(User::getStatus, 0)));
        return ResultUtil.success(res);
    }

    @ApiOperation(value = "lambda更新")
    @ApiOperationSupport(order = 2)
    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public Result<Boolean> update() {
        log.info("--------------------------lambda更新---------------------------------");
        userService.lambdaUpdate().eq(User::getId, 1).set(User::getName, "lambdaUpdate更新").update();
        userService.update(new LambdaUpdateWrapper<User>().eq(User::getId, 2).set(User::getName, "LambdaUpdateWrapper更新"));
        // 这种方式3.4无法使用了-[com.baomidou.mybatisplus.core.exceptions.MybatisPlusException: can not use this method for "getSqlSet"]
        // userService.update(new LambdaUpdateChainWrapper<User>(userService.getBaseMapper()).eq(User::getId, 3).set(User::getName, "LambdaUpdateChainWrapper更新"));
        userService.update(Wrappers.<User>lambdaUpdate().eq(User::getId, 4).set(User::getName, "Wrappers.lambdaUpdate更新"));
        return ResultUtil.success(true);
    }
}
