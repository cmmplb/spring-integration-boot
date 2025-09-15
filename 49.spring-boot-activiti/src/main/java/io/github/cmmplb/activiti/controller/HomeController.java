package io.github.cmmplb.activiti.controller;

import io.github.cmmplb.activiti.service.HomeService;
import io.github.cmmplb.activiti.vo.ApplyStatisticsVO;
import io.github.cmmplb.activiti.vo.ItemCountVO;
import io.github.cmmplb.core.result.Result;
import io.github.cmmplb.core.result.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author penglibo
 * @date 2023-12-06 13:43:12
 * @since jdk 1.8
 */

@Api(tags = "出差管理")
@Slf4j
@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private HomeService homeService;

    @ApiOperation("获取事项数量")
    @GetMapping(value = "/item/count")
    public Result<ItemCountVO> getItemCount() {
        return ResultUtil.success(homeService.getItemCount());
    }

    @ApiOperation("获取申请统计信息")
    @GetMapping(value = "/apply/statistics/{type}")
    @ApiImplicitParam(name = "type", paramType = "query", value = "统计类型:1-24小时;2-近30天;", required = true, dataType = "Integer", dataTypeClass = Integer.class, example = "1")
    public Result<ApplyStatisticsVO> getApplyStatistics(@PathVariable(value = "type") Integer type) {
        return ResultUtil.success(homeService.getApplyStatistics(type));
    }
}
