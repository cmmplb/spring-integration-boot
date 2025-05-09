package io.github.cmmplb.excel.alibaba.demo.export.data;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author penglibo
 * @date 2021-05-22 18:06:11
 * @since jdk 1.8
 */

@Data
public class IndexData {

    @ExcelProperty(value = "字符串标题", index = 0) // 列

    private String string;
    @ExcelProperty(value = "日期标题", index = 1)

    private Date date;

    /**
     * 这里设置3 会导致第二列空的
     */
    @ExcelProperty(value = "数字标题", index = 3)
    private Double doubleData;

    /**
     * 元数据
     * @return
     */
    public static List<IndexData> data() {
        List<IndexData> list = new ArrayList<IndexData>();
        for (int i = 0; i < 10; i++) {
            IndexData data = new IndexData();
            data.setString("字符串" + i);
            data.setDate(new Date());
            data.setDoubleData(0.56);
            list.add(data);
        }
        return list;
    }
}
