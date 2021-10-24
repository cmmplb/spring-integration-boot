package com.cmmplb.log.controller;

import com.cmmplb.log.annotations.SysLog;
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

    @SysLog("执行方法一")
    @GetMapping("/one")
    public void methodOne(String name) {
    }

    @SysLog("执行方法二")
    @GetMapping("/two")
    public void methodTwo() throws InterruptedException {
        Thread.sleep(2000);
    }

    @SysLog("执行方法三")
    @GetMapping("/three")
    public void methodThree(String name, String age) {
    }


    @SysLog("异常")
    @GetMapping("/ex")
    public void methodEx(String name, String age) {
        System.out.println("异常");
        System.out.println(1 / 0);
    }
}
