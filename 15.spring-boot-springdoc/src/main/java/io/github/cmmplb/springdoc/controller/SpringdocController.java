package io.github.cmmplb.springdoc.controller;

import io.github.cmmplb.core.result.Result;
import io.github.cmmplb.core.result.ResultUtil;
import io.github.cmmplb.springdoc.dto.SpringdocDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

/**
 * @author penglibo
 * @date 2021-08-02 17:55:35
 * @since jdk 1.8
 */

@RestController
@RequestMapping("/springdoc")
@Tag(name = "请求演示")
public class SpringdocController {

    @Operation(tags = "第一个接口")
    @GetMapping("/one/{id}")
    @Parameters(
            @Parameter(name = "id", required = true)
    )
    public Result<String> one(@PathVariable(value = "id") String id) {
        return ResultUtil.success(id);
    }


    @Operation(tags = "第二个接口")
    @ApiResponses(
            @ApiResponse(responseCode = "200", description = "成功")
    )
    @PostMapping("/two")
    // @Operation(summary = "two")
    public Result<SpringdocDTO> one(@RequestBody SpringdocDTO dto) {
        return ResultUtil.success(dto);
    }
}
