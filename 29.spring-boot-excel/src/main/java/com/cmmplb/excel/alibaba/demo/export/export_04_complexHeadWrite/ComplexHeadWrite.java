package com.cmmplb.excel.alibaba.demo.export.export_04_complexHeadWrite;

import com.alibaba.excel.EasyExcel;
import com.cmmplb.excel.alibaba.demo.export.data.ComplexHeadData;
import com.cmmplb.excel.alibaba.demo.util.TestFileUtil;

/**
 * @author penglibo
 * @date 2021-05-24 13:53:24
 * @since jdk 1.8
 * 复杂头写入
 */

public class ComplexHeadWrite {

    /**
     * 复杂头写入
     * <p>1. 创建excel对应的实体对象 参照{@link ComplexHeadData}
     * <p>2. 使用{@link @ExcelProperty}注解指定复杂的头
     * <p>3. 直接写即可
     */
    public static void main(String[] args) {
        String fileName = TestFileUtil.getPath() + "complexHeadWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写, 然后写到第一个sheet, 名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileName, ComplexHeadData.class).sheet("模板").doWrite(ComplexHeadData.data());
    }
}
