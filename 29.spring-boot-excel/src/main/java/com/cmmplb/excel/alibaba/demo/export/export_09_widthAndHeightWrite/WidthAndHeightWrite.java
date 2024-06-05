package com.cmmplb.excel.alibaba.demo.export.export_09_widthAndHeightWrite;

import com.alibaba.excel.EasyExcel;
import com.cmmplb.excel.alibaba.demo.export.data.WidthAndHeightData;
import com.cmmplb.excel.alibaba.demo.util.TestFileUtil;

/**
 * @author penglibo
 * @date 2021-05-24 15:09:33
 * @since jdk 1.8
 * 列宽、行高
 */

public class WidthAndHeightWrite {

    /**
     * 列宽、行高
     * <p>1. 创建excel对应的实体对象 参照{@link WidthAndHeightData}
     * <p>2. 使用注解{@link @ColumnWidth}、{@link @HeadRowHeight}、{@link @ContentRowHeight}指定宽度或高度
     * <p>3. 直接写即可
     */
    public static void main(String[] args) {
        String fileName = TestFileUtil.getPath() + "widthAndHeightWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileName, WidthAndHeightData.class).sheet("模板").doWrite(WidthAndHeightData.data());

    }
}
