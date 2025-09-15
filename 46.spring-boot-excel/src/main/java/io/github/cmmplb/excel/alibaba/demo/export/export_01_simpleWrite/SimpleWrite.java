package io.github.cmmplb.excel.alibaba.demo.export.export_01_simpleWrite;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import io.github.cmmplb.excel.alibaba.demo.export.data.DemoData;
import io.github.cmmplb.excel.alibaba.demo.util.TestFileUtil;

/**
 * @author penglibo
 * @date 2021-05-22 17:28:32
 * @since jdk 1.8
 * 学习
 */

public class SimpleWrite {

    // https://www.yuque.com/easyexcel/doc/write#34577a27

    /**
     * 最简单的写
     * <p>1. 创建excel对应的实体对象 参照{@link DemoData}
     * <p>2. 直接写即可
     */
    public static void main(String[] args) {
        // 写法1 JDK8+
        // since: 3.0.0-beta1
        String fileName = TestFileUtil.getPath() + "simpleWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写, 然后写到第一个sheet, 名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        EasyExcel.write(fileName, DemoData.class).sheet("模板").doWrite(DemoData::data);

        // 写法2
        fileName = TestFileUtil.getPath() + "simpleWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写, 然后写到第一个sheet, 名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileName, DemoData.class).sheet("模板").doWrite(DemoData.data());

        // 以response写
        // response.setContentType("application/vnd.ms-excel");
        // response.setCharacterEncoding("utf-8");
        // fileName = URLEncoder.encode(fileName, "UTF-8");// 这里URLEncoder.encode可以防止中文乱码
        // response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        // EasyExcel.write(response.getOutputStream(), SimpleExportData.class).sheet("模板").doWrite(data());

        // 写法3
        fileName = TestFileUtil.getPath() + "simpleWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写
        ExcelWriter excelWriter = null;
        try {
            excelWriter = EasyExcel.write(fileName, DemoData.class).build();
            WriteSheet writeSheet = EasyExcel.writerSheet("模板").build();
            excelWriter.write(DemoData.data(), writeSheet);
        } finally {
            // 千万别忘记finish 会帮忙关闭流
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }
    }
}
