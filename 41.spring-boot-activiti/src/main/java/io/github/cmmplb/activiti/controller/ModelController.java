package io.github.cmmplb.activiti.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.cmmplb.activiti.dto.ModelDTO;
import io.github.cmmplb.activiti.service.ModelService;
import io.github.cmmplb.activiti.vo.ModelVO;
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

/**
 * @author penglibo
 * @date 2023-11-03 11:44:08
 * @since jdk 1.8
 */

@Schema(name = "模型管理")
@Slf4j
@RestController
@RequestMapping("/model")
public class ModelController {

    @Autowired
    private ModelService modelService;

    @Operation(summary = "新增/编辑")
    @PostMapping(value = "/save")
    public Result<Boolean> save(@RequestBody ModelDTO dto) {
        return ResultUtil.success(modelService.save(dto));
    }

    @Operation(summary = "保存流程设计")
    @PutMapping(value = "/save/{id}")
    @Parameter(name = "id", description = "模型id", required = true, in = ParameterIn.PATH, example = "1")
    public Result<Boolean> saveDesign(@PathVariable(value = "id") String id, ModelDTO dto) {
        return ResultUtil.success(modelService.saveDesign(id, dto));
    }

    @Operation(summary = "获取模型流程设计")
    @GetMapping(value = "/json/{id}")
    @Parameter(name = "id", description = "模型id", required = true, in = ParameterIn.PATH, example = "1")
    public Result<ObjectNode> getEditorJson(@PathVariable(value = "id") String id) {
        return ResultUtil.success(modelService.getEditorJson(id));
    }


    @Operation(summary = "部署模型")
    @PostMapping("/deploy/{id}")
    @Parameter(name = "id", description = "模型id", required = true, in = ParameterIn.PATH, example = "1")
    public Result<Boolean> deployment(@PathVariable(value = "id") String id) {
        return ResultUtil.success(modelService.deployment(id));
    }

    @Operation(summary = "导出模型")
    @GetMapping("/export/{id}")
    @Parameter(name = "id", description = "模型id", required = true, in = ParameterIn.PATH, example = "1")
    public void export(@PathVariable(value = "id") String id) {
        modelService.export(id);
    }

    @Operation(summary = "根据id删除")
    @DeleteMapping(value = "/{id}")
    @Parameter(name = "id", description = "模型id", required = true, in = ParameterIn.PATH, example = "1")
    public Result<Boolean> removeById(@PathVariable(value = "id") String id) {
        return ResultUtil.success(modelService.removeById(id));
    }

    @Operation(summary = "分页条件查询列表")
    @PostMapping(value = "/paged")
    public Result<PageResult<ModelVO>> getByPaged(@RequestBody QueryPageBean dto) {
        return ResultUtil.success(modelService.getByPaged(dto));
    }

    @Operation(summary = "根据id获取详情信息")
    @GetMapping(value = "/{id}")
    @Parameter(name = "id", description = "模型id", required = true, in = ParameterIn.PATH, example = "1")
    public Result<ModelVO> getInfoById(@PathVariable(value = "id") String id) {
        return ResultUtil.success(modelService.getInfoById(id));
    }
}
