package io.github.cmmplb.log.controller;

import io.github.cmmplb.core.result.Result;
import io.github.cmmplb.core.result.ResultUtil;
import io.github.cmmplb.log.annotations.Log;
import io.github.cmmplb.log.constants.LogConstant;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author penglibo
 * @date 2021-04-14 15:01:09
 */

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/one")
    @Log(type = LogConstant.LogOperationTypeEnum.ONE, content = "one", businessType = LogConstant.LogBusinessTypeEnum.SAVE)
    public Result<Boolean> methodOne(String name) {
        return ResultUtil.success(true);
    }

    @GetMapping("/two")
    @Log(type = LogConstant.LogOperationTypeEnum.TWO, content = "two", businessType = LogConstant.LogBusinessTypeEnum.SAVE)
    public Result<Boolean> methodTwo() throws InterruptedException {
        Thread.sleep(2000);
        return ResultUtil.success(true);
    }

    @GetMapping("/three")
    @Log(type = LogConstant.LogOperationTypeEnum.THREE, content = "three", businessType = LogConstant.LogBusinessTypeEnum.SAVE)
    public Result<Boolean> methodThree(String name, String age) {
        return ResultUtil.success(true);
    }


    @GetMapping("/ex")
    @Log(type = LogConstant.LogOperationTypeEnum.EX, content = "测试异常", businessType = LogConstant.LogBusinessTypeEnum.SAVE)
    public Result<Boolean> methodEx(String name, String age) {
        System.out.println("异常");
        System.out.println(1 / 0);
        return ResultUtil.success(true);
    }
}
