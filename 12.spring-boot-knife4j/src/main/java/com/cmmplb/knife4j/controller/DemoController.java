package com.cmmplb.knife4j.controller;

import com.cmmplb.core.result.Result;
import com.cmmplb.core.result.ResultUtil;
import com.cmmplb.knife4j.dto.DemoDTO;
import com.github.xiaoymin.knife4j.annotations.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping("/demo")
@Api(tags = "demo")
@ApiSort(1) // @ApiSupport > @ApiSort > @Api  -  排序的规则是倒序
@ApiSupport(order = 1, author = "张三")
public class DemoController {

    @PostMapping("/demo1")
    @ApiOperation("demo1")
    @ApiOperationSupport(order = 4, author = "李四", ignoreParameters = {"id", "orderDate.id"}) // 忽略参数-表单类型的请求是不需要添加参数名的
    public Result<String> demo(DemoDTO dto) {
        return ResultUtil.success("success");
    }

    @PostMapping("/demo2")
    @ApiOperation("demo2")
    @ApiOperationSupport(order = 2, author = "李四2", ignoreParameters = {"dto.id", "dto.name", "dto.orderDate.id"})
    // JSON的方式
    public Result<String> demo2(@RequestBody DemoDTO dto) {
        return ResultUtil.success("success");
    }

    @PostMapping("/demo3")
    @ApiOperation("demo3")
    @ApiOperationSupport(order = 3, author = "李四3", includeParameters = {"id", "orderDate.id"}) // 和忽略取反，只显示的参数-json方式
    public Result<String> demo3(DemoDTO dto) {
        return ResultUtil.success("success");
    }

    @PostMapping("/demo4")
    @ApiOperation("demo4")
    @ApiOperationSupport(order = 3, author = "李四4", includeParameters = {"id", "orderDate.id"})
    public Result<String> demo4(@RequestBody DemoDTO dto) {
        return ResultUtil.success("success");
    }

    @PostMapping("/demo5")
    @ApiOperation(value = "jdk-HashMap-动态创建显示参数-无@RequestBody")
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
    @ApiOperation(value = "jdk-HashMap-动态创建响应参数-无@RequestBody")
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
