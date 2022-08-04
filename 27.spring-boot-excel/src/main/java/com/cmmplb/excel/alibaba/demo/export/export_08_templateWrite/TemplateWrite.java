package com.cmmplb.excel.alibaba.demo.export.export_08_templateWrite;

import com.alibaba.excel.EasyExcel;
import com.cmmplb.excel.alibaba.demo.export.data.DemoData;
import com.cmmplb.excel.alibaba.demo.export.data.IndexData;
import com.cmmplb.excel.alibaba.demo.util.TestFileUtil;

/**
 * @author penglibo
 * @date 2021-05-24 14:54:56
 * @since jdk 1.8
 */

public class TemplateWrite {

    /**
     * 根据模板写入
     * <p>1. 创建excel对应的实体对象 参照{@link IndexData}
     * <p>2. 使用{@link @ExcelProperty}注解指定写入的列
     * <p>3. 使用withTemplate 写取模板
     * <p>4. 直接写即可
     */
    public static void main(String[] args) {
        String fileName = TestFileUtil.getPath() + "templateWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileName, DemoData.class).withTemplate("src/main/resources/export/templateWrite.xlsx").sheet().doWrite(DemoData.data());
    }
}
