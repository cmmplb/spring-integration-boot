package com.cmmplb.canal.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cmmplb.core.beans.SelectVO;
import com.cmmplb.core.exception.CustomException;
import com.cmmplb.core.result.Result;
import com.cmmplb.core.result.ResultUtil;
import com.cmmplb.core.validator.group.Insert;
import com.cmmplb.core.validator.group.Update;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.cmmplb.canal.vo.TestVO;
import com.cmmplb.canal.dto.TestDTO;
import com.cmmplb.canal.dto.TestQueryDTO;
import com.cmmplb.canal.entity.Test;
import com.cmmplb.canal.service.TestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* @author momo
* @date 2021-12-02 11:50:46
* @since jdk 1.8
* 管理
*/

@Api(tags = "管理")
@ApiSupport(order = 1)
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestService testService;

    @PostMapping("/save")
    @ApiOperation("添加")
    @ApiOperationSupport(order = 1)
    public Result<Boolean> saveInfo(@RequestBody @Validated(Insert.class) TestDTO dto) {
        return ResultUtil.success(testService.saveInfo(dto));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除")
    @ApiOperationSupport(order = 2)
    public Result<Boolean> deleteById(@PathVariable(value = "id") Long id) {
        return ResultUtil.success(testService.deleteById(id));
    }

    @PutMapping("/{id}")
    @ApiOperation("修改")
    @ApiOperationSupport(order = 3)
    public Result<Boolean> updateInfoById(@PathVariable(value = "id") Long id, @RequestBody @Validated(Update.class) TestDTO dto) {
        dto.setId(id);
        return ResultUtil.success(testService.updateInfoById(dto));
    }

    @GetMapping("/list")
    @ApiOperation("列表")
    @ApiOperationSupport(order = 4)
    public Result<List<SelectVO>> getList() {
        return ResultUtil.success(testService.getList());
    }

    @GetMapping("/paged")
    @ApiOperation("分页条件获取列表")
    @ApiOperationSupport(order = 5)
    public Result<Page<TestVO>> getByPaged(TestQueryDTO dto) {
        return ResultUtil.success(testService.getByPaged(dto));
    }
}