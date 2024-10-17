package io.github.cmmplb.activiti.controller;

import io.github.cmmplb.activiti.service.ProcessService;
import io.github.cmmplb.activiti.vo.ExecutionInstanceVO;
import io.github.cmmplb.activiti.vo.ProcessHistoricVO;
import io.github.cmmplb.activiti.vo.ProcessInstanceVO;
import io.github.cmmplb.activiti.vo.ProcessVariableVO;
import io.github.cmmplb.core.beans.PageResult;
import io.github.cmmplb.core.beans.QueryPageBean;
import io.github.cmmplb.core.result.Result;
import io.github.cmmplb.core.result.ResultUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author penglibo
 * @date 2023-11-17 15:27:45
 * @since jdk 1.8
 */

@Schema(name = "流程管理")
@Slf4j
@RestController
@RequestMapping("/process")
public class ProcessController {

    @Autowired
    private ProcessService processService;

    @Operation(summary = "分页条件查询运行的流程实例列表")
    @PostMapping(value = "/paged")
    public Result<PageResult<ProcessInstanceVO>> getInstanceByPaged(@RequestBody QueryPageBean queryPageBean) {
        return ResultUtil.success(processService.getInstanceByPaged(queryPageBean));
    }

    @Operation(summary = "查看流程实例进度流程图")
    @Parameter(name = "id", description = "流程实例id", required = true, in = ParameterIn.PATH, example = "1")
    @GetMapping(value = {"/show/{id}"})
    public void showProcessInstanceProgressChart(@PathVariable(value = "id") String id) {
        processService.showProcessInstanceProgressChart(id);
    }

    @Operation(summary = "挂起流程实例")
    @PostMapping(value = "/suspend/{id}")
    @Parameter(name = "id", description = "流程实例id", required = true, in = ParameterIn.PATH, example = "1")
    public Result<Boolean> suspendProcessInstance(@PathVariable(value = "id") String id) {
        return ResultUtil.success(processService.suspendProcessInstance(id));
    }

    @Operation(summary = "激活流程实例")
    @PostMapping(value = "/activate/{id}")
    @Parameter(name = "id", description = "流程实例id", required = true, in = ParameterIn.PATH, example = "1")
    public Result<Boolean> activateProcessInstance(@PathVariable(value = "id") String id) {
        return ResultUtil.success(processService.activateProcessInstance(id));
    }

    @Operation(summary = "查询正在运行的执行实例列表")
    @GetMapping(value = "/execution/paged")
    public Result<List<ExecutionInstanceVO>> getExecutionInstanceList() {
        return ResultUtil.success(processService.getExecutionInstanceList());
    }

    @Operation(summary = "分页条件查询所有流程实例列表")
    @PostMapping(value = "/all/paged")
    public Result<PageResult<ProcessInstanceVO>> getAllInstanceByPaged(@RequestBody QueryPageBean queryPageBean) {
        return ResultUtil.success(processService.getAllInstanceByPaged(queryPageBean));
    }

    @Operation(summary = "根据流程实例id获取流程活动历史")
    @PostMapping(value = "/history/{id}")
    @Parameter(name = "id", description = "流程实例id", required = true, in = ParameterIn.PATH, example = "1")
    public Result<PageResult<ProcessHistoricVO>> getProcessHistory(@PathVariable(value = "id") String id,
                                                                   @RequestBody QueryPageBean queryPageBean) {
        return ResultUtil.success(processService.getProcessHistory(id, queryPageBean));
    }

    @Operation(summary = "根据流程实例id获取流程变量")
    @PostMapping(value = "/variable/{id}")
    @Parameter(name = "id", description = "流程实例id", required = true, in = ParameterIn.PATH, example = "1")
    public Result<PageResult<ProcessVariableVO>> getProcessVariable(@PathVariable(value = "id") String id,
                                                                    @RequestBody QueryPageBean queryPageBean) {
        return ResultUtil.success(processService.getProcessVariable(id, queryPageBean));
    }
}
