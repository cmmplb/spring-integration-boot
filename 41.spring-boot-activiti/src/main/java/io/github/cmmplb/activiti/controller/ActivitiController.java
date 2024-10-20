package io.github.cmmplb.activiti.controller;

import io.github.cmmplb.activiti.service.ActivitiService;
import io.github.cmmplb.activiti.vo.ProcessDefinitionVO;
import io.github.cmmplb.activiti.vo.ProcessHistoricVO;
import io.github.cmmplb.activiti.vo.TaskVO;
import io.github.cmmplb.core.result.Result;
import io.github.cmmplb.core.result.ResultUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author penglibo
 * @date 2023-10-10 09:35:03
 * @since jdk 1.8
 */

@Schema(name = "activiti流程演示")
@RestController
@RequestMapping("/activiti")
public class ActivitiController {

    @Autowired
    ActivitiService activitiService;

    // 自动部署配置文件路径processes, 查询表：act_re_deployment和act_re_procdef
    @Operation(summary = "部署流程")
    @PostMapping(value = "/deploy")
    public Result<String> deployProcessDefinition() {
        return ResultUtil.success(activitiService.deployProcessDefinition());
    }

    @Operation(summary = "查询流程定义列表-已部署的工作流")
    @GetMapping(value = "/list")
    public Result<List<ProcessDefinitionVO>> getProcessDefinitionList() {
        return ResultUtil.success(activitiService.getProcessDefinitionList());
    }

    @Operation(summary = "启动流程")
    @PostMapping(value = "/start/{processDefinitionId}")
    @Parameter(name = "processDefinitionId", description = "流程定义id", required = true, in = ParameterIn.PATH, example = "1")
    public Result<String> startProcess(@PathVariable(name = "processDefinitionId") String processDefinitionId) {
        return ResultUtil.success(activitiService.startProcess(processDefinitionId));
    }

    @Operation(summary = "查询代办任务列表")
    @GetMapping(value = "/task/list")
    public Result<List<TaskVO>> getTaskList() {
        return ResultUtil.success(activitiService.getTaskList());
    }

    @Operation(summary = "办理任务")
    @PostMapping(value = "/task/handle/{taskId}")
    @Parameter(name = "taskId", description = "任务id", required = true, in = ParameterIn.PATH, example = "1")
    public Result<String> handleTask(@PathVariable(name = "taskId") String taskId) {
        return ResultUtil.success(activitiService.handleTask(taskId));
    }

    @Operation(summary = "查询任务节点")
    @GetMapping(value = "/task/node/{instanceId}")
    @Parameter(name = "instanceId", description = "流程实例id", required = true, in = ParameterIn.PATH, example = "1")
    public Result<TaskVO> queryTaskNode(@PathVariable(name = "instanceId") String instanceId) {
        return ResultUtil.success(activitiService.queryTaskNode(instanceId));
    }

    @Operation(summary = "暂停和激活流程定义")
    @PutMapping(value = "/suspend/process/{processDefinitionId}")
    @Parameter(name = "processDefinitionId", description = "流程定义id", required = true, in = ParameterIn.PATH, example = "1")
    public Result<Boolean> suspendProcessDefinition(@PathVariable(name = "processDefinitionId") String processDefinitionId) {
        return ResultUtil.success(activitiService.suspendProcessDefinition(processDefinitionId));
    }

    @Operation(summary = "API查询和native查询")
    @GetMapping(value = "/native/api")
    public Result<Boolean> nativeApi() {
        return ResultUtil.success(activitiService.nativeApi());
    }

    @Operation(summary = "查询历史流程列表")
    @GetMapping(value = "/history/list")
    public Result<List<ProcessHistoricVO>> getHistoryProcessList() {
        return ResultUtil.success(activitiService.getHistoryProcessList());
    }

    @Operation(summary = "流程删除")
    @DeleteMapping(value = "/deployment/{deploymentId}")
    @Parameter(name = "deploymentId", description = "部署id", required = true, in = ParameterIn.PATH, example = "1")
    public Result<Boolean> deleteDeployment(@PathVariable(value = "deploymentId") String deploymentId) {
        return ResultUtil.success(activitiService.deleteDeployment(deploymentId));
    }
}