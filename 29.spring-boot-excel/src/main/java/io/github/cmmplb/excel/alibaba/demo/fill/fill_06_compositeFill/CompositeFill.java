package io.github.cmmplb.excel.alibaba.demo.fill.fill_06_compositeFill;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.enums.WriteDirectionEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.alibaba.excel.write.metadata.fill.FillWrapper;
import io.github.cmmplb.core.utils.FileUtil;
import io.github.cmmplb.excel.alibaba.demo.fill.data.FillData;
import io.github.cmmplb.excel.alibaba.demo.util.TestFileUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author penglibo
 * @date 2023-08-14 10:05:59
 * @since jdk 1.8
 */

@Slf4j
public class CompositeFill {

    /**
     * 多列表组合填充填充
     * @since 2.2.0-beta1
     */
    public static void main(String[] args) {
        // 模板注意 用{} 来表示你要用的变量 如果本来就有"{","}" 特殊字符 用"\{","\}"代替
        // {} 代表普通变量 {.} 代表是list的变量 {前缀.} 前缀可以区分不同的list
        InputStream inputStream = FileUtil.getInputStream("fill/composite.xlsx");

        String fileName = TestFileUtil.getPath() + "compositeFill" + System.currentTimeMillis() + ".xlsx";

        // 方案1
        try (ExcelWriter excelWriter = EasyExcel.write(fileName).withTemplate(inputStream).build()) {
            WriteSheet writeSheet = EasyExcel.writerSheet().build();
            FillConfig fillConfig = FillConfig.builder().direction(WriteDirectionEnum.HORIZONTAL).build();
            // 如果有多个list 模板上必须有{前缀.} 这里的前缀就是 data1, 然后多个list必须用 FillWrapper包裹
            excelWriter.fill(new FillWrapper("data1", FillData.data()), fillConfig, writeSheet);
            excelWriter.fill(new FillWrapper("data1", FillData.data()), fillConfig, writeSheet);
            excelWriter.fill(new FillWrapper("data2", FillData.data()), writeSheet);
            excelWriter.fill(new FillWrapper("data2", FillData.data()), writeSheet);
            excelWriter.fill(new FillWrapper("data3", FillData.data()), writeSheet);
            excelWriter.fill(new FillWrapper("data3", FillData.data()), writeSheet);

            Map<String, Object> map = new HashMap<String, Object>();
            // map.put("date", "2019年10月9日13:28:28");
            map.put("date", new Date());

            excelWriter.fill(map, writeSheet);
        }
    }
}
