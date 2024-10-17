package io.github.cmmplb.sa.token.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.github.cmmplb.core.constants.StringConstant;
import io.github.cmmplb.core.result.Result;
import io.github.cmmplb.core.result.ResultUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author penglibo
 * @date 2021-09-19 22:43:56
 * @since jdk 1.8
 */

@Schema(name = "用户管理")
@ApiSupport(order = 1, author = StringConstant.AUTHOR)
@RestController
@RequestMapping("/user")
public class UserController {

    // 测试登录, 浏览器访问： http://localhost:port/user/doLogin?username=zhang&password=123456
    @RequestMapping("doLogin")
    public String doLogin(String username, String password) {
        // 此处仅作模拟示例, 真实项目需要从数据库中查询数据进行比对
        if ("zhang".equals(username) && "123456".equals(password)) {
            StpUtil.login(10001);
            return "登录成功";
        }
        return "登录失败";
    }

    // 查询登录状态, 浏览器访问： http://localhost:port/user/isLogin
    @RequestMapping("isLogin")
    public String isLogin() {
        return "当前会话是否登录：" + StpUtil.isLogin();
    }

    @Operation(summary = "查询")
    @ApiOperationSupport(order = 1)
    @GetMapping("/info")
    public Result<String> getInfo() {
        return ResultUtil.success("张三");
    }
}
