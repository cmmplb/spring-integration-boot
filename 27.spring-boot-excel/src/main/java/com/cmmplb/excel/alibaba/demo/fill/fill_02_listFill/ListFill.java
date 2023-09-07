package com.cmmplb.excel.alibaba.demo.fill.fill_02_listFill;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.cmmplb.core.utils.FileUtil;
import com.cmmplb.excel.alibaba.demo.fill.data.FillData;
import com.cmmplb.excel.alibaba.demo.util.TestFileUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.InputStream;

/**
 * @author penglibo
 * @date 2023-08-14 10:00:15
 * @since jdk 1.8
 * 填充列表
 */

@Slf4j
public class ListFill {

    /**
     * 填充列表
     * @since 2.1.1
     */
    public static void main(String[] args) {
        // 模板注意 用{} 来表示你要用的变量 如果本来就有"{","}" 特殊字符 用"\{","\}"代替
        // 填充list 的时候还要注意 模板中{.} 多了个点 表示list
        // 如果填充list的对象是map,必须包涵所有list的key,哪怕数据为null，必须使用map.put(key,null)
        InputStream inputStream = FileUtil.getInputStream("fill/list.xlsx");

        // 方案1 一下子全部放到内存里面 并填充
        String fileName = TestFileUtil.getPath() + "listFill" + System.currentTimeMillis() + ".xlsx";
        // 这里 会填充到第一个sheet， 然后文件流会自动关闭
        EasyExcel.write(fileName).withTemplate(inputStream).sheet().doFill(FillData.data());

        // 方案2 分多次 填充 会使用文件缓存（省内存）
        fileName = TestFileUtil.getPath() + "listFill" + System.currentTimeMillis() + ".xlsx";
        inputStream = FileUtil.getInputStream("fill/list.xlsx");
        try (ExcelWriter excelWriter = EasyExcel.write(fileName).withTemplate(inputStream).build()) {
            WriteSheet writeSheet = EasyExcel.writerSheet().build();
            excelWriter.fill(FillData::data, writeSheet);
            excelWriter.fill(FillData::data, writeSheet);
        }
    }
}
