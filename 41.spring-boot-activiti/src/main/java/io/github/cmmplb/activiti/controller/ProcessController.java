package com.cmmplb.activiti.controller;

import com.cmmplb.activiti.service.ProcessService;
import com.cmmplb.activiti.vo.ExecutionInstanceVO;
import com.cmmplb.activiti.vo.ProcessHistoricVO;
import com.cmmplb.activiti.vo.ProcessInstanceVO;
import com.cmmplb.activiti.vo.ProcessVariableVO;
import io.github.cmmplb.core.beans.PageResult;
import io.github.cmmplb.core.beans.QueryPageBean;
import io.github.cmmplb.core.result.Result;
import io.github.cmmplb.core.result.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author penglibo
 * @date 2023-11-17 15:27:45
 * @since jdk 1.8
 */

@Api(tags = "流程管理")
@Slf4j
@RestController
@RequestMapping("/process")
public class ProcessController {

    @Autowired
    private ProcessService processService;

    @ApiOperation("分页条件查询运行的流程实例列表")
    @PostMapping(value = "/paged")
    public Result<PageResult<ProcessInstanceVO>> getInstanceByPaged(@RequestBody QueryPageBean queryPageBean) {
        return ResultUtil.success(processService.getInstanceByPaged(queryPageBean));
    }

    @ApiOperation("查看流程实例进度流程图")
    @ApiImplicitParam(name = "id", paramType = "query", value = "流程实例id", required = true, dataType = "Long", dataTypeClass = Long.class, example = "1")
    @GetMapping(value = {"/show/{id}"})
    public void showProcessInstanceProgressChart(@PathVariable(value = "id") String id) {
        processService.showProcessInstanceProgressChart(id);
    }

    @ApiOperation("挂起流程实例")
    @PostMapping(value = "/suspend/{id}")
    @ApiImplicitParam(name = "id", paramType = "query", value = "流程实例id", required = true, dataType = "Long", dataTypeClass = Long.class, example = "1")
    public Result<Boolean> suspendProcessInstance(@PathVariable(value = "id") String id) {
        return ResultUtil.success(processService.suspendProcessInstance(id));
    }

    @ApiOperation("激活流程实例")
    @PostMapping(value = "/activate/{id}")
    @ApiImplicitParam(name = "id", paramType = "query", value = "流程实例id", required = true, dataType = "Long", dataTypeClass = Long.class, example = "1")
    public Result<Boolean> activateProcessInstance(@PathVariable(value = "id") String id) {
        return ResultUtil.success(processService.activateProcessInstance(id));
    }

    @ApiOperation("查询正在运行的执行实例列表")
    @GetMapping(value = "/execution/paged")
    public Result<List<ExecutionInstanceVO>> getExecutionInstanceList() {
        return ResultUtil.success(processService.getExecutionInstanceList());
    }

    @ApiOperation("分页条件查询所有流程实例列表")
    @PostMapping(value = "/all/paged")
    public Result<PageResult<ProcessInstanceVO>> getAllInstanceByPaged(@RequestBody QueryPageBean queryPageBean) {
        return ResultUtil.success(processService.getAllInstanceByPaged(queryPageBean));
    }

    @ApiOperation("根据流程实例id获取流程活动历史")
    @PostMapping(value = "/history/{id}")
    @ApiImplicitParam(name = "id", paramType = "query", value = "流程实例id", required = true, dataType = "Long", dataTypeClass = Long.class, example = "1")
    public Result<PageResult<ProcessHistoricVO>> getProcessHistory(@PathVariable(value = "id") String id,
                                                                   @RequestBody QueryPageBean queryPageBean) {
        return ResultUtil.success(processService.getProcessHistory(id, queryPageBean));
    }

    @ApiOperation("根据流程实例id获取流程变量")
    @PostMapping(value = "/variable/{id}")
    @ApiImplicitParam(name = "id", paramType = "query", value = "流程实例id", required = true, dataType = "Long", dataTypeClass = Long.class, example = "1")
    public Result<PageResult<ProcessVariableVO>> getProcessVariable(@PathVariable(value = "id") String id,
                                                                     @RequestBody QueryPageBean queryPageBean) {
        return ResultUtil.success(processService.getProcessVariable(id, queryPageBean));
    }
}
