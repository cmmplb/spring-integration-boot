package io.github.cmmplb.activiti.controller;

import io.github.cmmplb.activiti.service.HomeService;
import io.github.cmmplb.activiti.vo.ApplyStatisticsVO;
import io.github.cmmplb.activiti.vo.ItemCountVO;
import io.github.cmmplb.core.result.Result;
import io.github.cmmplb.core.result.ResultUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author penglibo
 * @date 2023-12-06 13:43:12
 * @since jdk 1.8
 */

@Schema(name = "出差管理")
@Slf4j
@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private HomeService homeService;

    @Operation(summary = "获取事项数量")
    @GetMapping(value = "/item/count")
    public Result<ItemCountVO> getItemCount() {
        return ResultUtil.success(homeService.getItemCount());
    }

    @Operation(summary = "获取申请统计信息")
    @GetMapping(value = "/apply/statistics/{type}")
    @Parameter(name = "type", description = "统计类型:1-24小时;2-近30天;", required = true, in = ParameterIn.PATH, example = "1")
    public Result<ApplyStatisticsVO> getApplyStatistics(@PathVariable(value = "type") Integer type) {
        return ResultUtil.success(homeService.getApplyStatistics(type));
    }
}
