package io.github.cmmplb.activiti.controller;

import io.github.cmmplb.activiti.service.ManagementService;
import io.github.cmmplb.activiti.vo.JobVO;
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

/**
 * @author penglibo
 * @date 2023-11-20 15:59:05
 * @since jdk 1.8
 */

@Api(tags = "调度管理")
@Slf4j
@RestController
@RequestMapping("/management")
public class ManagementController {

    @Autowired
    private ManagementService managementService;

    @ApiOperation("分页条件查询管理任务")
    @PostMapping(value = "/job/paged/{type}")
    @ApiImplicitParam(name = "type", paramType = "query", value = "类型:1-定时;2-异步;3-挂起;4-死亡;", required = true, dataType = "Byte", dataTypeClass = Byte.class, example = "1")
    public Result<PageResult<JobVO>> getJobByPaged(@PathVariable(value = "type") Byte type,
                                                   @RequestBody QueryPageBean queryPageBean) {
        return ResultUtil.success(managementService.getJobByPaged(type, queryPageBean));
    }

}
