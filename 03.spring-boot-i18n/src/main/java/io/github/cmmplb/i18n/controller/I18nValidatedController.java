package io.github.cmmplb.i18n.controller;

import io.github.cmmplb.core.result.Result;
import io.github.cmmplb.core.result.ResultUtil;
import io.github.cmmplb.i18n.dto.DemoDTO;
import io.github.cmmplb.i18n.utils.MessageUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author penglibo
 * @date 2021-09-14 12:00:49
 * @since jdk 1.8
 * 国际化校验演示
 */

@RestController
@RequestMapping("/i18n/validated")
public class I18nValidatedController {

    // 参数国际化校验
    @PostMapping("/params")
    public Result<DemoDTO> demoOne(@RequestBody @Validated DemoDTO dto) {
        return ResultUtil.success(dto);
    }

    // 响应国际化校验
    @GetMapping("/response")
    public Result<String> demoTwo() {
        return ResultUtil.success(MessageUtils.getMessage(200));
    }
}
