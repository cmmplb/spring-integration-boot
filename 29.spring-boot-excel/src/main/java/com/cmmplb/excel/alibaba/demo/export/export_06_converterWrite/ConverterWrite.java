package com.cmmplb.excel.alibaba.demo.export.export_06_converterWrite;

import com.alibaba.excel.EasyExcel;
import com.cmmplb.excel.alibaba.demo.export.data.ConverterData;
import com.cmmplb.excel.alibaba.demo.util.TestFileUtil;

/**
 * @author penglibo
 * @date 2021-05-24 14:20:54
 * @since jdk 1.8
 * 日期、数字或者自定义格式转换
 */

public class ConverterWrite {

    /**
     * 日期、数字或者自定义格式转换
     * <p>1. 创建excel对应的实体对象 参照{@link ConverterData}
     * <p>2. 使用{@link @ExcelProperty}配合使用注解{@link @DateTimeFormat}、{@link @NumberFormat}或者自定义注解
     * <p>3. 直接写即可
     */
    public static void main(String[] args) {
        String fileName = TestFileUtil.getPath() + "converterWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写, 然后写到第一个sheet, 名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileName, ConverterData.class).sheet("模板").doWrite(ConverterData.data());
    }
}
