package io.github.cmmplb.swagger.controller;

import io.github.cmmplb.core.result.Result;
import io.github.cmmplb.core.result.ResultUtil;
import io.github.cmmplb.swagger.dto.SwaggerDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
    @ApiOperation("one")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "id", value = "id", required = true, dataTypeClass = Integer.class)
    )
    public Result<String> one(@PathVariable(value = "id") String id) {
        return ResultUtil.success(id);
    }

    @PostMapping("/two")
    @ApiOperation("two")
    public Result<SwaggerDTO> one(@RequestBody SwaggerDTO dto) {
        return ResultUtil.success(dto);
    }
}
