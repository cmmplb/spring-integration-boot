package com.cmmplb.excel.project.controller;

import com.cmmplb.core.constants.StringConstants;
import com.cmmplb.core.result.Result;
import com.cmmplb.core.result.ResultUtil;
import com.cmmplb.excel.project.beans.ExcelResult;
import com.cmmplb.excel.project.data.StaffExcelReadData;
import com.cmmplb.excel.project.utils.ExcelUtil;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author penglibo
 * @date 2021-08-24 15:54:41
 * @since jdk 1.8
 */

@Api(tags = "excel导入功能演示")
@ApiSupport(order = 1, author = StringConstants.AUTHOR)
@RestController
@RequestMapping("/excel/read")
public class ExcelReadController {

    @Autowired
    private ExcelUtil<StaffExcelReadData> excelUtil;

    @ApiOperation("单个sheet页导入")
    @ApiOperationSupport(order = 1)
    @PostMapping("/single")
    public Result<ExcelResult<StaffExcelReadData>> readSingleExcel(MultipartFile file) throws Exception {
        return ResultUtil.success(excelUtil.readSingleExcel(file, StaffExcelReadData.class));
    }

    @ApiOperation("读取指定行开始")
    @ApiOperationSupport(order = 2)
    @PostMapping("/head")
    public Result<ExcelResult<StaffExcelReadData>> readHeadRowNumberExcel(MultipartFile file, @RequestParam("headRowNumber") Integer headRowNumber) throws Exception {
        return ResultUtil.success(excelUtil.readHeadRowNumberExcel(file, StaffExcelReadData.class, headRowNumber));
    }

    @ApiOperation("读取指定sheet页")
    @ApiOperationSupport(order = 3)
    @PostMapping("/assign")
    public Result<ExcelResult<StaffExcelReadData>> readAssignExcel(MultipartFile file, @RequestParam("sheetNo") Integer sheetNo) throws Exception {
        return ResultUtil.success(excelUtil.readAssignExcel(file, StaffExcelReadData.class, sheetNo));
    }

    @ApiOperation("读取指定多sheet页")
    @ApiOperationSupport(order = 4)
    @PostMapping("/many")
    public Result<ExcelResult<StaffExcelReadData>> readAssignManyExcel(MultipartFile file) throws Exception {
        return ResultUtil.success(excelUtil.readAssignManyExcel(file, StaffExcelReadData.class));
    }

    @ApiOperation("读取全部sheet页")
    @ApiOperationSupport(order = 5)
    @PostMapping("/all")
    public Result<ExcelResult<StaffExcelReadData>> readAllExcel(MultipartFile file) throws Exception {
        return ResultUtil.success(excelUtil.readAllExcel(file, StaffExcelReadData.class));
    }
}
