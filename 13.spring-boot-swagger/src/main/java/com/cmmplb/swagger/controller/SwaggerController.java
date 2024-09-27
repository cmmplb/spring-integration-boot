package com.cmmplb.swagger.controller;

import com.cmmplb.core.result.Result;
import com.cmmplb.core.result.ResultUtil;
import com.cmmplb.swagger.dto.SwaggerDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

/**
 * @author penglibo
 * @date 2021-08-02 17:55:35
 * @since jdk 1.8
 */

@RestController
@RequestMapping("/swagger")
@Api(tags = "请求演示")
public class SwaggerController {

    @GetMapping("/one/{id}")
    @Operation(summary = "one")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "id", value = "id", required = true, dataTypeClass = Integer.class)
    )
    public Result<String> one(@PathVariable(value = "id") String id) {
        return ResultUtil.success(id);
    }

    @PostMapping("/two")
    @Operation(summary = "two")
    public Result<SwaggerDTO> one(@RequestBody SwaggerDTO dto) {
        return ResultUtil.success(dto);
    }
}
