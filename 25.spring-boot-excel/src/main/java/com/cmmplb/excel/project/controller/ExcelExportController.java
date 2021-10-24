package com.cmmplb.excel.project.controller;

import com.cmmplb.core.constants.StringConstants;
import com.cmmplb.excel.project.data.StaffExcelExportData;
import com.cmmplb.excel.project.utils.ExcelUtil;
import com.cmmplb.core.utils.RandomUtil;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @author penglibo
 * @date 2021-08-24 15:54:41
 * @since jdk 1.8
 */

@Api(tags = "excel导出功能演示")
@ApiSupport(order = 1, author = StringConstants.AUTHOR)
@RestController
@RequestMapping("/excel/export")
public class ExcelExportController {

    @Autowired
    private ExcelUtil<StaffExcelExportData> excelUtil;

    @ApiOperation("单sheet页导出")
    @ApiOperationSupport(order = 1)
    @GetMapping("/single")
    public void writeSingleExcel() {
        List<StaffExcelExportData> list = getList();
        String fileName = "一个Excel文件";
        String sheetName = "第一个sheet";
        excelUtil.writeSingleExcel(fileName, sheetName, new StaffExcelExportData(), list);
    }

    @ApiOperation("多sheet页导出")
    @ApiOperationSupport(order = 2)
    @GetMapping("/many")
    public void writeManyExcel() {
        List<StaffExcelExportData> list = getList();
        String fileName = "一个Excel文件";
        String sheetName = "第一个sheet";
        List<Integer> sheetNos = Arrays.asList(1, 2);
        excelUtil.writeManyExcel(fileName, sheetName, sheetNos, new StaffExcelExportData(), list);
    }

    private List<StaffExcelExportData> getList() {
        List<StaffExcelExportData> list = new ArrayList<>();
        StaffExcelExportData data;
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            data = new StaffExcelExportData();
            data.setNumber(++i + "");
            data.setPost(RandomUtil.getRandomPost());
            data.setDepartment(RandomUtil.getRandomDepartment());
            data.setName(RandomUtil.getRandomChineseName());
            data.setSex(RandomUtil.getSex());
            data.setEducation(RandomUtil.getRandomEducation());
            data.setAge(random.nextInt(50) + 20);
            data.setBirthday(RandomUtil.getRandomBirthday(data.getAge()));
            data.setIdCard(RandomUtil.getRandomCardID());
            data.setPhone(RandomUtil.getRandomPhone());
            data.setEntryYears(random.nextInt(15) + 1);
            data.setEntryDate(new Date(new Date().getTime() - (data.getEntryYears() * 31536000000L)));
            data.setRemark("");
            list.add(data);
        }
        return list;
    }
}
