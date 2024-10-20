package io.github.cmmplb.excel.project.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.github.cmmplb.core.constants.StringConstant;
import io.github.cmmplb.core.result.Result;
import io.github.cmmplb.core.result.ResultUtil;
import io.github.cmmplb.core.utils.ObjectUtil;
import io.github.cmmplb.excel.project.data.ReadData;
import io.github.cmmplb.report.excel.beans.ExcelResult;
import io.github.cmmplb.report.excel.entity.ImportManySheet;
import io.github.cmmplb.report.excel.utils.ExcelUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * @author penglibo
 * @date 2021-08-24 15:54:41
 * @since jdk 1.8
 */

@Tag(name = "excel导入功能演示")
// @ApiSupport > @ApiSort > @Api  -  排序的规则是倒序
@ApiSort(1)
// 作者,方法名上ApiOperationSupport.author没有则取类名声明的作者
@ApiSupport(order = 1, author = StringConstant.AUTHOR)
@RestController
@RequestMapping("/excel/read")
public class ExcelReadController {

    @Operation(summary = "单个sheet页导入")
    @ApiOperationSupport(order = 1)
    @PostMapping("/single")
    public Result<ExcelResult<ReadData>> readSingleExcel(MultipartFile file) {
        return ResultUtil.success(ExcelUtil.readSingle(file, ReadData.class));
    }

    @Operation(summary = "读取指定行开始")
    @ApiOperationSupport(order = 2)
    @PostMapping("/head")
    public Result<ExcelResult<ReadData>> readHeadRowNumberExcel(MultipartFile file, @RequestParam("headRowNumber") Integer headRowNumber) {
        return ResultUtil.success(ExcelUtil.readAssignRow(file, ReadData.class, headRowNumber));
    }

    @Operation(summary = "读取指定sheet页")
    @ApiOperationSupport(order = 3)
    @PostMapping("/assign")
    public Result<ExcelResult<ReadData>> readAssignExcel(MultipartFile file, @RequestParam("sheetNo") Integer sheetNo) {
        return ResultUtil.success(ExcelUtil.readAssignSheetNo(file, ReadData.class, sheetNo));
    }

    @Operation(summary = "读取指定多sheet页")
    @ApiOperationSupport(order = 4)
    @PostMapping("/many")
    public Result<ExcelResult<ReadData>> readAssignManyExcel(MultipartFile file) {
        List<ImportManySheet<?>> sheetList = new ArrayList<>();
        ImportManySheet<ReadData> importManySheet = new ImportManySheet<>();
        sheetList.add(importManySheet);

        ImportManySheet<ReadData> importManySheet1 = new ImportManySheet<>();
        sheetList.add(importManySheet1);
        return ResultUtil.success(ObjectUtil.cast(ExcelUtil.readAssignMany(file, sheetList)));
    }

    @Operation(summary = "读取全部sheet页")
    @ApiOperationSupport(order = 5)
    @PostMapping("/all")
    public Result<ExcelResult<ReadData>> readAllExcel(MultipartFile file) {
        return ResultUtil.success(ExcelUtil.readAll(file, ReadData.class));
    }
}