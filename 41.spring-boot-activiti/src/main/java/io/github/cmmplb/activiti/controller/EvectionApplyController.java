package io.github.cmmplb.activiti.controller;

import io.github.cmmplb.activiti.dto.EvectionApplyDTO;
import io.github.cmmplb.activiti.service.EvectionApplyService;
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

@Schema(name = "出差管理")
@Slf4j
@RestController
@RequestMapping("/evection")
public class EvectionApplyController {

    @Autowired
    private EvectionApplyService evectionApplyService;

    @Operation(summary = "添加出差申请")
    @PostMapping(value = "/save")
    public Result<Boolean> save(@RequestBody EvectionApplyDTO dto) {
        boolean save;
        try {
            save = evectionApplyService.save(dto);
        } catch (ActivitiException e) {
            throw new CustomException(e.getMessage());
        }
        return ResultUtil.success(save);
    }
}
