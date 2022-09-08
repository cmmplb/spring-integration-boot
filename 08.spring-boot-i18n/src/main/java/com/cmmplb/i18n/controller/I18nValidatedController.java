package com.cmmplb.i18n.controller;

import com.cmmplb.core.constants.StringConstants;
import com.cmmplb.core.result.Result;
import com.cmmplb.core.result.ResultUtil;
import com.cmmplb.i18n.dto.DemoDTO;
import com.cmmplb.i18n.utils.MessageUtils;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author penglibo
 * @date 2021-09-14 12:00:49
 * @since jdk 1.8
 */

@Api(tags = "国际化校验演示")
@ApiSort(1)
@ApiSupport(order = 1, author = StringConstants.AUTHOR)
@RestController
@RequestMapping("/i18n/validated")
public class I18nValidatedController {

    @PostMapping("/params")
    @ApiOperation("参数国际化校验")
    public Result<DemoDTO>  demoOne(@RequestBody @Validated DemoDTO dto) {
        return ResultUtil.success(dto);
    }

    @GetMapping("/response")
    @ApiOperation("响应国际化校验")
    public Result<String> demoTwo() {
        return ResultUtil.success(MessageUtils.getMessage(200));
    }
}
