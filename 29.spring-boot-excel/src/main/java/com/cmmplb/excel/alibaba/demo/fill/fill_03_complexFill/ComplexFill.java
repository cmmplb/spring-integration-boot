package com.cmmplb.excel.alibaba.demo.fill.fill_03_complexFill;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.util.MapUtils;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import io.github.cmmplb.core.utils.FileUtil;
import com.cmmplb.excel.alibaba.demo.fill.data.FillData;
import com.cmmplb.excel.alibaba.demo.util.TestFileUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.InputStream;
import java.util.Map;

/**
 * @author penglibo
 * @date 2023-08-14 10:02:53
 * @since jdk 1.8
 * 复杂的填充
 */

@Slf4j
public class ComplexFill {

    /**
     * 复杂的填充
     * @since 2.1.1
     */
    public static void main(String[] args) {
        // 模板注意 用{} 来表示你要用的变量 如果本来就有"{","}" 特殊字符 用"\{","\}"代替
        // {} 代表普通变量 {.} 代表是list的变量
        InputStream inputStream = FileUtil.getInputStream("fill/complex.xlsx");

        String fileName = TestFileUtil.getPath() + "complexFill" + System.currentTimeMillis() + ".xlsx";
        // 方案1
        try (ExcelWriter excelWriter = EasyExcel.write(fileName).withTemplate(inputStream).build()) {
            WriteSheet writeSheet = EasyExcel.writerSheet().build();
            // 这里注意 入参用了forceNewRow 代表在写入list的时候不管list下面有没有空行 都会创建一行，然后下面的数据往后移动。默认 是false，会直接使用下一行，如果没有则创建。
            // forceNewRow 如果设置了true,有个缺点 就是他会把所有的数据都放到内存了，所以慎用
            // 简单的说 如果你的模板有list,且list不是最后一行，下面还有数据需要填充 就必须设置 forceNewRow=true 但是这个就会把所有数据放到内存 会很耗内存
            // 如果数据量大 list不是最后一行 参照下一个
            FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.TRUE).build();
            excelWriter.fill(FillData::data, fillConfig, writeSheet);
            excelWriter.fill(FillData::data, fillConfig, writeSheet);
            Map<String, Object> map = MapUtils.newHashMap();
            map.put("date", "2019年10月9日13:28:28");
            map.put("total", 1000);
            excelWriter.fill(map, writeSheet);
        }
    }
}