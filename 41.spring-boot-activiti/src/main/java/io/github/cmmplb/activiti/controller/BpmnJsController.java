package io.github.cmmplb.activiti.controller;

import io.github.cmmplb.activiti.dto.ModelBpmnDTO;
import io.github.cmmplb.activiti.service.BpmnJsService;
import io.github.cmmplb.activiti.vo.BpmnInfoVO;
import io.github.cmmplb.activiti.vo.BpmnProgressVO;
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
 * @date 2023-12-13 16:14:55
 * @since jdk 1.8
 */
@Schema(name = "处理BpmnJs")
@Slf4j
@RestController
@RequestMapping("/bpmn/js")
public class BpmnJsController {

    @Autowired
    private BpmnJsService bpmnJsService;

    @Operation(summary = "获取模型流程设计")
    @GetMapping(value = "/{id}")
    @Parameter(name = "id", description = "模型id", required = true, in = ParameterIn.PATH, example = "1")
    public Result<BpmnInfoVO> getBpmnInfo(@PathVariable(value = "id") String id) {
        return ResultUtil.success(bpmnJsService.getBpmnInfo(id));
    }

    @Operation(summary = "保存流程设计")
    @PutMapping(value = "/save/{id}")
    @Parameter(name = "id", description = "模型id", required = true, in = ParameterIn.PATH, example = "1")
    public Result<Boolean> saveDesign(@PathVariable(value = "id") String id, ModelBpmnDTO dto) {
        return ResultUtil.success(bpmnJsService.saveDesign(id, dto));
    }

    @Operation(summary = "查看流程图")
    @GetMapping(value = "/show/model/{id}")
    @Parameter(name = "id", description = "流程定义id", required = true, in = ParameterIn.PATH, example = "1")
    public Result<String> showFlowChart(@PathVariable(value = "id") String id) {
        return ResultUtil.success(bpmnJsService.showFlowChart(id));
    }

    @Operation(summary = "查看申请进度流程图")
    @Parameter(name = "applyId", description = "申请id", required = true, in = ParameterIn.PATH, example = "1")
    @GetMapping(value = {"/show/{applyId}"})
    public Result<BpmnProgressVO> showProgressChart(@PathVariable(value = "applyId") Long applyId) {
        return ResultUtil.success(bpmnJsService.showProgressChart(applyId));
    }

}
