package io.github.cmmplb.activiti.controller;

import io.github.cmmplb.activiti.dto.HandleTaskDTO;
import io.github.cmmplb.activiti.dto.TaskQueryDTO;
import io.github.cmmplb.activiti.service.TaskService;
import io.github.cmmplb.activiti.vo.CompletedTaskVO;
import io.github.cmmplb.activiti.vo.IncompleteTaskVO;
import io.github.cmmplb.core.beans.PageResult;
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

/**
 * @author penglibo
 * @date 2023-11-16 10:41:33
 * @since jdk 1.8
 */

@Schema(name = "任务管理")
@Slf4j
@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Operation(summary = "分页条件查询代办列表")
    @PostMapping(value = "/incomplete/paged")
    public Result<PageResult<IncompleteTaskVO>> getIncompleteByPaged(@RequestBody TaskQueryDTO dto) {
        return ResultUtil.success(taskService.getByPaged(dto));
    }

    @Operation(summary = "分页条件查询已办列表")
    @PostMapping(value = "/completed/paged")
    public Result<PageResult<CompletedTaskVO>> getCompletedByPaged(@RequestBody TaskQueryDTO dto) {
        return ResultUtil.success(taskService.getCompletedByPaged(dto));
    }

    @Operation(summary = "办理任务")
    @PostMapping(value = "/handle/{id}")
    @Parameter(name = "id", description = "任务id", required = true, in = ParameterIn.PATH, example = "1")
    public Result<Boolean> handleTask(@PathVariable(value = "id") String id, @RequestBody HandleTaskDTO dto) {
        dto.setId(id);
        return ResultUtil.success(taskService.handleTask(dto));
    }

    @Operation(summary = "委托他人办理")
    @PostMapping(value = "/entrust/{id}/{userId}")
    @Parameters({
            @Parameter(name = "id", description = "任务id", required = true, in = ParameterIn.PATH, example = "1"),
            @Parameter(name = "userId", description = "用户id", required = true, in = ParameterIn.PATH, example = "1")
    })
    public Result<Boolean> entrustTask(@PathVariable(value = "id") String id, @PathVariable(value = "userId") String userId) {
        return ResultUtil.success(taskService.entrustTask(id, userId));
    }
}
