package com.cmmplb.excel.alibaba.demo.fill.fill_05_horizontalFill;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.enums.WriteDirectionEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.cmmplb.core.utils.FileUtil;
import com.cmmplb.excel.alibaba.demo.fill.data.FillData;
import com.cmmplb.excel.alibaba.demo.util.TestFileUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author penglibo
 * @date 2023-08-14 10:05:08
 * @since jdk 1.8
 * 横向的填充
 */

@Slf4j
public class HorizontalFill {

    /**
     * 横向的填充
     * @since 2.1.1
     */
    public static void main(String[] args) {
        // 模板注意 用{} 来表示你要用的变量 如果本来就有"{","}" 特殊字符 用"\{","\}"代替
        // {} 代表普通变量 {.} 代表是list的变量
        InputStream inputStream = FileUtil.getInputStream("fill/horizontal.xlsx");

        String fileName = TestFileUtil.getPath() + "horizontalFill" + System.currentTimeMillis() + ".xlsx";
        // 方案1
        try (ExcelWriter excelWriter = EasyExcel.write(fileName).withTemplate(inputStream).build()) {
            WriteSheet writeSheet = EasyExcel.writerSheet().build();
            FillConfig fillConfig = FillConfig.builder().direction(WriteDirectionEnum.HORIZONTAL).build();
            excelWriter.fill(FillData::data, fillConfig, writeSheet);
            excelWriter.fill(FillData::data, fillConfig, writeSheet);

            Map<String, Object> map = new HashMap<>();
            map.put("date", "2019年10月9日13:28:28");
            excelWriter.fill(map, writeSheet);
        }
    }
}
