package io.github.cmmplb.mail.controller;

import io.github.cmmplb.core.result.Result;
import io.github.cmmplb.core.result.ResultUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author penglibo
 * @date 2021-11-17 15:32:33
 * @since jdk 1.8
 */

@RestController
public class HealthController {

    @RequestMapping("/health")
    public Result<String> health() {
        return ResultUtil.success("OK");
    }

}
