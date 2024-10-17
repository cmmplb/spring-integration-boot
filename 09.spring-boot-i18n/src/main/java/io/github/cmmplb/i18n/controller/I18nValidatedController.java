package io.github.cmmplb.i18n.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.github.cmmplb.core.constants.StringConstant;
import io.github.cmmplb.core.result.Result;
import io.github.cmmplb.core.result.ResultUtil;
import io.github.cmmplb.i18n.dto.DemoDTO;
import io.github.cmmplb.i18n.utils.MessageUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author penglibo
 * @date 2021-09-14 12:00:49
 * @since jdk 1.8
 */

@Tag(name = "国际化校验演示", description = "国际化校验演示"
        , extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "3", parseValue = true)})}
)
@ApiSort(1)
@ApiSupport(order = 1, author = StringConstant.AUTHOR)
@RestController
@RequestMapping("/i18n/validated")
public class I18nValidatedController {

    @PostMapping("/params")
    @Operation(summary = "参数国际化校验1", description = "参数国际化校验2")
    @ApiOperationSupport(order = 2, author = "李四")
    public Result<DemoDTO>  demoOne(@RequestBody @Validated DemoDTO dto) {
        return ResultUtil.success(dto);
    }

    @GetMapping("/response")
    @Operation(summary = "响应国际化校验")
    @ApiOperationSupport(order = 1, author = "李四")
    public Result<String> demoTwo() {
        return ResultUtil.success(MessageUtils.getMessage(200));
    }
}
