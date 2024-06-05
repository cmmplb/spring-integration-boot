package com.cmmplb.excel.alibaba.demo.fill.fill_04_complexFillWithTable;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.cmmplb.core.utils.FileUtil;
import com.cmmplb.excel.alibaba.demo.fill.data.FillData;
import com.cmmplb.excel.alibaba.demo.util.TestFileUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author penglibo
 * @date 2023-08-14 10:03:45
 * @since jdk 1.8
 * 数据量大的复杂填充
 */

@Slf4j
public class ComplexFillWithTable {

    /**
     * 数据量大的复杂填充
     * <p>
     * 这里的解决方案是 确保模板list为最后一行，然后再拼接table.还有03版没救，只能刚正面加内存。
     * @since 2.1.1
     */
    public static void main(String[] args) {
        // 模板注意 用{} 来表示你要用的变量 如果本来就有"{","}" 特殊字符 用"\{","\}"代替
        // {} 代表普通变量 {.} 代表是list的变量
        // 这里模板 删除了list以后的数据，也就是统计的这一行
        InputStream inputStream = FileUtil.getInputStream("fill/complexFillWithTable.xlsx");

        String fileName = TestFileUtil.getPath() + "complexFillWithTable" + System.currentTimeMillis() + ".xlsx";

        // 方案1
        try (ExcelWriter excelWriter = EasyExcel.write(fileName).withTemplate(inputStream).build()) {
            WriteSheet writeSheet = EasyExcel.writerSheet().build();
            // 直接写入数据
            excelWriter.fill(FillData::data, writeSheet);
            excelWriter.fill(FillData::data, writeSheet);

            // 写入list之前的数据
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("date", "2019年10月9日13:28:28");
            excelWriter.fill(map, writeSheet);

            // list 后面还有个统计 想办法手动写入
            // 这里偷懒直接用list 也可以用对象
            List<List<String>> totalListList = ListUtils.newArrayList();
            List<String> totalList = ListUtils.newArrayList();
            totalListList.add(totalList);
            totalList.add(null);
            totalList.add(null);
            totalList.add(null);
            // 第四列
            totalList.add("统计:1000");
            // 这里是write 别和fill 搞错了
            excelWriter.write(totalListList, writeSheet);
            // 总体上写法比较复杂 但是也没有想到好的版本 异步的去写入excel 不支持行的删除和移动，也不支持备注这种的写入，所以也排除了可以
            // 新建一个 然后一点点复制过来的方案，最后导致list需要新增行的时候，后面的列的数据没法后移，后续会继续想想解决方案
        }
    }
}
