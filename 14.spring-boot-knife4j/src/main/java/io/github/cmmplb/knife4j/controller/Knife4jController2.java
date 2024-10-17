package io.github.cmmplb.knife4j.controller;

import com.github.xiaoymin.knife4j.annotations.*;
import io.github.cmmplb.core.result.Result;
import io.github.cmmplb.core.result.ResultUtil;
import io.github.cmmplb.knife4j.dto.Knife4jDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author penglibo
 * @date 2021-08-02 17:55:35
 * @since jdk 1.8
 */

@RestController
@RequestMapping("/demo2")
@Tag(name = "测试接口",description = "测试接口",extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "3", parseValue = true)})})
// @Tag(name = "测试接口")
@ApiSort(3) // @ApiSupport > @ApiSort > @Api  -  排序的规则是倒序
@ApiSupport(order = 3, author = "张三")
public class Knife4jController2 {

    @PostMapping("/demo1")
    @Operation(
            // 这个会新添加一个一级菜单, 名称DemoTag
            tags = "DemoTag",
            // 接口名称
            summary = "demo1",
            // 接口描述
            description = "demo1",
            parameters = {
                    @Parameter(name = "id", description = "订单编号", required = true, in = ParameterIn.QUERY, schema = @Schema(implementation = Long.class)),
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "成功", content = @Content(schema = @Schema(implementation = Result.class)))
            },
            hidden = false
    )
    @ApiOperationSupport(order = 4, author = "李四", ignoreParameters = {"id", "orderDate.id"})
    // 忽略参数-表单类型的请求是不需要添加参数名的
    public Result<String> demo(Knife4jDTO dto) {
        return ResultUtil.success("success");
    }

    @PostMapping("/demo2")
    @Operation(
            // 这个会单独显示在接口文档中
            tags = "DemoTag",
            description = "demo2")
    @ApiOperationSupport(order = 2, author = "李四2", ignoreParameters = {"dto.id", "dto.name", "dto.orderDate.id"})
    // JSON的方式
    public Result<String> demo2(@RequestBody Knife4jDTO dto) {
        return ResultUtil.success("success");
    }

    @PostMapping("/demo3")
    @Operation(description = "demo3")
    @ApiOperationSupport(order = 3, author = "李四3", includeParameters = {"id", "orderDate.id"}) // 和忽略取反, 只显示的参数-json方式
    public Result<String> demo3(Knife4jDTO dto) {
        return ResultUtil.success("success");
    }

    @PostMapping("/demo4")
    @Operation(description = "demo4")
    @ApiOperationSupport(order = 3, author = "李四4", includeParameters = {"id", "orderDate.id"})
    public Result<String> demo4(@RequestBody Knife4jDTO dto) {
        return ResultUtil.success("success");
    }

    @PostMapping("/demo5")
    @Operation(description = "jdk-HashMap-动态创建显示参数-无@RequestBody")
    @DynamicParameters(name = "CreateOrderHashMapModel", properties = {
            @DynamicParameter(name = "", value = "注解id", example = "X000111", required = true, dataTypeClass = Integer.class),
            @DynamicParameter(name = "name3", value = "订单编号-gson"),
            @DynamicParameter(name = "name1", value = "订单编号1-gson"),
    })
    public Result<Map<String, Object>> demo5(@RequestBody Map<String, Object> map) {
        Result<Map<String, Object>> r = new Result<>();
        r.setData(map);
        return r;
    }

    @PostMapping("/demo6")
    @Operation(description = "jdk-HashMap-动态创建响应参数-无@RequestBody")
    @DynamicResponseParameters(name = "CreateOrderHashMapModel", properties = {
            @DynamicParameter(name = "", value = "注解id", example = "X000111", required = true, dataTypeClass = Integer.class),
            @DynamicParameter(name = "name3", value = "订单编号-gson"),
            @DynamicParameter(name = "name1", value = "订单编号1-gson"),
    })
    public Result<Map<String, Object>> demo6(@RequestBody Map<String, Object> map) {
        Result<Map<String, Object>> r = new Result<>();
        r.setData(map);
        return r;
    }
}
