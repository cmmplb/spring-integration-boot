package com.cmmplb.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.cmmplb.excel.alibaba.demo.export.data.DemoData;
import com.cmmplb.excel.alibaba.demo.export.data.TestData;
import com.cmmplb.excel.alibaba.demo.util.TestFileUtil;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class ExcelTests {

    public static void main(String[] args) {
        String fileName = TestFileUtil.getPath() + "simpleWrite" + System.currentTimeMillis() + ".xlsx";
        EasyExcel.write(fileName, TestData.class).excelType(ExcelTypeEnum.XLS).sheet("模板").doWrite(TestData::data);
    }

}