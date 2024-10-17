package io.github.cmmplb.activiti.controller;

import io.github.cmmplb.activiti.dto.ApplyDTO;
import io.github.cmmplb.activiti.service.ApplyService;
import io.github.cmmplb.activiti.vo.ApplyDetailsVO;
import io.github.cmmplb.activiti.vo.ApplyVO;
import io.github.cmmplb.core.beans.PageResult;
import io.github.cmmplb.core.result.Result;
import io.github.cmmplb.core.result.ResultUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author penglibo
 * @date 2023-11-15 09:09:58
 * @since jdk 1.8
 */

@Schema(name = "事项申请管理")
@Slf4j
@RestController
@RequestMapping("/apply")
public class ApplyController {

    @Autowired
    private ApplyService applyService;

    @Operation(summary = "分页条件查询列表")
    @PostMapping(value = "/paged")
    public Result<PageResult<ApplyVO>> getByPaged(@RequestBody ApplyDTO dto) {
        return ResultUtil.success(applyService.getByPaged(dto));
    }

    @Operation(summary = "详情")
    @GetMapping(value = "/{id}")
    @Parameter(name = "id", description = "申请id", required = true, in = ParameterIn.PATH, example = "1")
    public Result<ApplyDetailsVO> getApplyDetailsById(@PathVariable(value = "id") Long id) {
        return ResultUtil.success(applyService.getApplyDetailsById(id));
    }

    @Operation(summary = "查看申请进度流程图")
    @Parameter(name = "id", description = "申请id", required = true, in = ParameterIn.PATH, example = "1")
    @GetMapping(value = {"/show/{id}"})
    public void showProgressChart(@PathVariable(value = "id") Long id) {
        applyService.showProgressChart(id);
    }

    @Operation(summary = "撤销申请")
    @DeleteMapping(value = "/cancel/{id}")
    @Parameter(name = "id", description = "申请id", required = true, in = ParameterIn.PATH, example = "1")
    public Result<Boolean> cancelApply(@PathVariable(value = "id") Long id) {
        return ResultUtil.success(applyService.cancelApply(id));
    }

    @Operation(summary = "根据id删除")
    @DeleteMapping(value = "/{id}")
    @Parameter(name = "id", description = "申请id", required = true, in = ParameterIn.PATH, example = "1")
    public Result<Boolean> deleteById(@PathVariable(value = "id") Long id) {
        return ResultUtil.success(applyService.deleteById(id));
    }


}
