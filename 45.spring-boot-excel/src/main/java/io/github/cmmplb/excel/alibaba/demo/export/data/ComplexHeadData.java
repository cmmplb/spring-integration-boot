package io.github.cmmplb.excel.alibaba.demo.export.data;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author penglibo
 * @date 2021-05-24 13:54:56
 * @since jdk 1.8
 */

@Data
public class ComplexHeadData {

    @ExcelProperty({"主标题", "字符串标题"})
    private String string;

    @ExcelProperty({"主标题", "日期标题"})
    private Date date;

    @ExcelProperty({"主标题", "数字标题"})
    private Double doubleData;

    /**
     * 元数据
     * @return
     */
    public static List<ComplexHeadData> data() {
        List<ComplexHeadData> list = new ArrayList<ComplexHeadData>();
        for (int i = 0; i < 10; i++) {
            ComplexHeadData data = new ComplexHeadData();
            data.setString("字符串" + i);
            data.setDate(new Date());
            data.setDoubleData(0.56);
            list.add(data);
        }
        return list;
    }
}
