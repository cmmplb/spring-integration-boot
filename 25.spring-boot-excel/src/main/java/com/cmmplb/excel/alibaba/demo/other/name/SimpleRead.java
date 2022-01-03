package com.cmmplb.excel.alibaba.demo.other.name;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;

/**
 * @author penglibo
 * @date 2021-05-24 17:03:35
 * @since jdk 1.8
 */

public class SimpleRead {

    public static void main(String[] args) {
        String fileName = "src/main/resources/read/name.xlsx";
        ExcelReader excelReader = null;
        try {
            excelReader = EasyExcel.read("25.spring-boot-excel/" + fileName, NameData.class, new NameDataListener()).build();
            ReadSheet readSheet = EasyExcel.readSheet(0).build();
            excelReader.read(readSheet);
        } finally {
            if (excelReader != null) {
                // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
                excelReader.finish();
            }
        }

    }
}
