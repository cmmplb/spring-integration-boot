package com.cmmplb.excel.alibaba.demo.export.export_03_indexWrite;

import com.alibaba.excel.EasyExcel;
import com.cmmplb.excel.alibaba.demo.export.data.IndexData;
import com.cmmplb.excel.alibaba.demo.util.TestFileUtil;

/**
 * @author penglibo
 * @date 2021-05-22 18:01:56
 * @since jdk 1.8
 * 学习指定写入的列
 */

public class IndexWrite {

    // https://www.yuque.com/easyexcel/doc/write#34577a27

    /**
     * 指定写入的列
     * <p>1. 创建excel对应的实体对象 参照{@link IndexData}
     * <p>2. 使用{@link @ExcelProperty}注解指定写入的列
     * <p>3. 直接写即可
     */
    public static void main(String[] args) {
        String fileName = TestFileUtil.getPath() + "indexWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileName, IndexData.class).sheet("模板").doWrite(IndexData.data());
    }

}
