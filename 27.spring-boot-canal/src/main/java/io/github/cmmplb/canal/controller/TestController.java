package io.github.cmmplb.canal.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.github.cmmplb.canal.dto.TestDTO;
import io.github.cmmplb.canal.dto.TestQueryDTO;
import io.github.cmmplb.canal.service.TestService;
import io.github.cmmplb.canal.vo.TestVO;
import io.github.cmmplb.core.beans.SelectVO;
import io.github.cmmplb.core.constants.StringConstant;
import io.github.cmmplb.core.result.Result;
import io.github.cmmplb.core.result.ResultUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "管理")
// @ApiSupport > @ApiSort > @Api  -  排序的规则是倒序
@ApiSort(1)
// 作者,方法名上ApiOperationSupport.author没有则取类名声明的作者
@ApiSupport(order = 1, author = StringConstant.AUTHOR)
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestService testService;

    @PostMapping("/save")
    @Operation(summary = "添加")
    @ApiOperationSupport(order = 1)
    public Result<Boolean> saveInfo(@RequestBody @Validated TestDTO dto) {
        return ResultUtil.success(testService.saveInfo(dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除")
    @ApiOperationSupport(order = 2)
    public Result<Boolean> deleteById(@PathVariable(value = "id") Long id) {
        return ResultUtil.success(testService.deleteById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "修改")
    @ApiOperationSupport(order = 3)
    public Result<Boolean> updateInfoById(@PathVariable(value = "id") Long id, @RequestBody @Validated TestDTO dto) {
        dto.setId(id);
        return ResultUtil.success(testService.updateInfoById(dto));
    }

    @GetMapping("/list")
    @Operation(summary = "列表")
    @ApiOperationSupport(order = 4)
    public Result<List<SelectVO>> getList() {
        return ResultUtil.success(testService.getList());
    }

    @GetMapping("/paged")
    @Operation(summary = "分页条件获取列表")
    @ApiOperationSupport(order = 5)
    public Result<Page<TestVO>> getByPaged(TestQueryDTO dto) {
        return ResultUtil.success(testService.getByPaged(dto));
    }
}