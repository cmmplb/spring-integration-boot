package io.github.cmmplb.activiti.controller;

import io.github.cmmplb.activiti.dto.LeaveApplyDTO;
import io.github.cmmplb.activiti.service.LeaveApplyService;
import io.github.cmmplb.core.exception.CustomException;
import io.github.cmmplb.core.result.Result;
import io.github.cmmplb.core.result.ResultUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.ActivitiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author penglibo
 * @date 2023-11-15 09:09:58
 * @since jdk 1.8
 */

@Schema(name = "请假管理")
@Slf4j
@RestController
@RequestMapping("/leave")
public class LeaveApplyController {

    @Autowired
    private LeaveApplyService leaveApplyService;

    @Operation(summary = "添加请假申请")
    @PostMapping(value = "/save")
    public Result<Boolean> save(@RequestBody LeaveApplyDTO dto) {
        boolean save;
        try {
            save = leaveApplyService.save(dto);
        } catch (ActivitiException e) {
            throw new CustomException(e.getMessage());
        }
        return ResultUtil.success(save);
    }
}
