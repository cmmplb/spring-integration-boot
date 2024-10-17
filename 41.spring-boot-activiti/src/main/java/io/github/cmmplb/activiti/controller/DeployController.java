package io.github.cmmplb.activiti.controller;

import io.github.cmmplb.activiti.dto.SuspendActivateProcessDefinitionDTO;
import io.github.cmmplb.activiti.service.DeployService;
import io.github.cmmplb.activiti.vo.ProcessDefinitionVO;
import io.github.cmmplb.core.beans.PageResult;
import io.github.cmmplb.core.beans.QueryPageBean;
import io.github.cmmplb.core.result.Result;
import io.github.cmmplb.core.result.ResultUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author penglibo
 * @date 2023-11-09 11:42:38
 * @since jdk 1.8
 */

@Schema(name = "部署管理")
@Slf4j
@RestController
@RequestMapping("/deploy")
public class DeployController {

    @Autowired
    private DeployService deployService;

    @Operation(summary = "分页条件查询列表")
    @PostMapping(value = "/paged")
    public Result<PageResult<ProcessDefinitionVO>> getByPaged(@RequestBody QueryPageBean queryPageBean) {
        return ResultUtil.success(deployService.getByPaged(queryPageBean));
    }

    @Operation(summary = "上传部署流程文件")
    @PostMapping(value = "/upload")
    public Result<Boolean> upload(@RequestPart(value = "files") MultipartFile[] files) {
        return ResultUtil.success(deployService.upload(files));
    }

    @Operation(summary = "删除")
    @DeleteMapping(value = "/{id}")
    @Parameter(name = "id", description = "部署id", required = true, in = ParameterIn.PATH, example = "1")
    public Result<Boolean> removeById(@PathVariable(value = "id") String id) {
        return ResultUtil.success(deployService.removeById(id));
    }

    @Operation(summary = "查看工作流定义")
    @GetMapping(value = "/show/process/{id}/{resource}")
    @Parameters({
            @Parameter(name = "id", description = "部署id", required = true, in = ParameterIn.PATH, example = "1"),
            @Parameter(name = "resource", description = "资源路径", required = true, in = ParameterIn.PATH, example = "1")
    })
    public void showProcessDefinition(@PathVariable("id") String id, @PathVariable(value = "resource") String resource) {
        deployService.showProcessDefinition(id, resource);
    }

    @Operation(summary = "查看流程图")
    @GetMapping(value = "/show/model/{id}")
    @Parameter(name = "id", description = "流程定义id", required = true, in = ParameterIn.PATH, example = "1")
    public void showFlowChart(@PathVariable(value = "id") String id) {
        deployService.showProcessChart(id);
    }

    @Operation(summary = "将流程定义转为模型")
    @PostMapping(value = "/exchange/{id}")
    @Parameter(name = "id", description = "流程定义id", required = true, in = ParameterIn.PATH, example = "1")
    public Result<Boolean> exchangeProcessToModel(@PathVariable(value = "id") String id) {
        return ResultUtil.success(deployService.exchangeProcessToModel(id));
    }

    @Operation(summary = "挂起流程定义")
    @PostMapping(value = "/suspend/{id}")
    @Parameter(name = "id", description = "流程定义id", required = true, in = ParameterIn.PATH, example = "1")
    public Result<Boolean> suspendProcessDefinition(@PathVariable(value = "id") String id, @RequestBody SuspendActivateProcessDefinitionDTO dto) {
        return ResultUtil.success(deployService.suspendProcessDefinition(id, dto));
    }

    @Operation(summary = "激活流程定义")
    @PostMapping(value = "/activate/{id}")
    @Parameter(name = "id", description = "流程定义id", required = true, in = ParameterIn.PATH, example = "1")
    public Result<Boolean> activateProcessDefinition(@PathVariable(value = "id") String id, @RequestBody SuspendActivateProcessDefinitionDTO dto) {
        return ResultUtil.success(deployService.activateProcessDefinition(id, dto));
    }
}
