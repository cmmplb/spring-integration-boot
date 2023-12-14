package com.cmmplb.activiti.controller;

import com.cmmplb.activiti.service.ActivitiService;
import com.cmmplb.activiti.vo.ProcessDefinitionVO;
import com.cmmplb.activiti.vo.ProcessHistoricVO;
import com.cmmplb.activiti.vo.TaskVO;
import com.cmmplb.core.result.Result;
import com.cmmplb.core.result.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author penglibo
 * @date 2023-10-10 09:35:03
 * @since jdk 1.8
 */

@Api(tags = "activiti流程演示")
@RestController
@RequestMapping("/activiti")
public class ActivitiController {

    @Autowired
    ActivitiService activitiService;

    // 自动部署配置文件路径processes，查询表：act_re_deployment和act_re_procdef
    @ApiOperation("部署流程")
    @PostMapping(value = "/deploy")
    public Result<String> deployProcessDefinition() {
        return ResultUtil.success(activitiService.deployProcessDefinition());
    }

    @ApiOperation("查询流程定义列表-已部署的工作流")
    @GetMapping(value = "/list")
    public Result<List<ProcessDefinitionVO>> getProcessDefinitionList() {
        return ResultUtil.success(activitiService.getProcessDefinitionList());
    }

    @ApiOperation("启动流程")
    @PostMapping(value = "/start/{processDefinitionId}")
    @ApiImplicitParam(name = "processDefinitionId", paramType = "query", value = "流程定义id", required = true, dataType = "Long", dataTypeClass = Long.class, example = "1")
    public Result<String> startProcess(@PathVariable(name = "processDefinitionId") String processDefinitionId) {
        return ResultUtil.success(activitiService.startProcess(processDefinitionId));
    }

    @ApiOperation("查询代办任务列表")
    @GetMapping(value = "/task/list")
    public Result<List<TaskVO>> getTaskList() {
        return ResultUtil.success(activitiService.getTaskList());
    }

    @ApiOperation("办理任务")
    @PostMapping(value = "/task/handle/{taskId}")
    @ApiImplicitParam(name = "taskId", paramType = "query", value = "任务id", required = true, dataType = "Long", dataTypeClass = Long.class, example = "1")
    public Result<String> handleTask(@PathVariable(name = "taskId") String taskId) {
        return ResultUtil.success(activitiService.handleTask(taskId));
    }

    @ApiOperation("查询任务节点")
    @GetMapping(value = "/task/node/{instanceId}")
    @ApiImplicitParam(name = "instanceId", paramType = "query", value = "流程实例id", required = true, dataType = "Long", dataTypeClass = Long.class, example = "1")
    public Result<TaskVO> queryTaskNode(@PathVariable(name = "instanceId") String instanceId) {
        return ResultUtil.success(activitiService.queryTaskNode(instanceId));
    }

    @ApiOperation("暂停和激活流程定义")
    @PutMapping(value = "/suspend/process/{processDefinitionId}")
    @ApiImplicitParam(name = "processDefinitionId", paramType = "query", value = "流程定义id", required = true, dataType = "Long", dataTypeClass = Long.class, example = "1")
    public Result<Boolean> suspendProcessDefinition(@PathVariable(name = "processDefinitionId") String processDefinitionId) {
        return ResultUtil.success(activitiService.suspendProcessDefinition(processDefinitionId));
    }

    @ApiOperation("API查询和native查询")
    @GetMapping(value = "/native/api")
    public Result<Boolean> nativeApi() {
        return ResultUtil.success(activitiService.nativeApi());
    }

    @ApiOperation("查询历史流程列表")
    @GetMapping(value = "/history/list")
    public Result<List<ProcessHistoricVO>> getHistoryProcessList() {
        return ResultUtil.success(activitiService.getHistoryProcessList());
    }

    @ApiOperation("流程删除")
    @DeleteMapping(value = "/deployment/{deploymentId}")
    @ApiImplicitParam(name = "deploymentId", paramType = "query", value = "部署id", required = true, dataType = "Long", dataTypeClass = Long.class, example = "1")
    public Result<Boolean> deleteDeployment(@PathVariable(value = "deploymentId") String deploymentId) {
        return ResultUtil.success(activitiService.deleteDeployment(deploymentId));
    }
}