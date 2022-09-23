package com.cmmplb.excel.alibaba.demo.export.data;

/**
 * @author penglibo
 * @date 2021-05-24 15:12:34
 * @since jdk 1.8
 */

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.*;
import lombok.Data;
import org.apache.poi.ss.usermodel.FillPatternType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 样式的数据类
 *
 * @author Jiaju Zhuang
 **/
@Data
// 头背景设置成红色 IndexedColors.RED.getIndex()
@HeadStyle(fillPatternType = FillPatternType.NO_FILL, fillForegroundColor = 3)
// 头字体设置成20
// @HeadFontStyle(fontHeightInPoints = 20)
// 内容的背景设置成绿色 IndexedColors.GREEN.getIndex()
// @ContentStyle(fillPatternType = FillPatternType.SOLID_FOREGROUND, fillForegroundColor = 17)
// 内容字体设置成20
// @ContentFontStyle(fontHeightInPoints = 20)
// 内容行高
@ContentRowHeight(15)
// 头部内容
@HeadRowHeight(30)
// 列宽
@ColumnWidth(50)
public class DemoStyleData {

    // 字符串的头背景设置成粉红 IndexedColors.PINK.getIndex()
    // @HeadStyle(fillPatternType = FillPatternType.SOLID_FOREGROUND, fillForegroundColor = 14)
    // 字符串的头字体设置成20
    // @HeadFontStyle(fontHeightInPoints = 30)
    // 字符串的内容的背景设置成天蓝 IndexedColors.SKY_BLUE.getIndex()
    // @ContentStyle(fillPatternType = FillPatternType.SOLID_FOREGROUND, fillForegroundColor = 40)
    // 字符串的内容字体设置成20
    // @ContentFontStyle(fontHeightInPoints = 30)
    @ExcelProperty("字符串标题")
    private String string;

    @ExcelProperty("日期标题")
    private Date date;

    @ExcelProperty("数字标题")
    private Double doubleData;

    /**
     * 元数据
     * @return
     */
    public static List<DemoData> data() {
        List<DemoData> list = new ArrayList<DemoData>();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setString("字符串" + i);
            data.setDate(new Date());
            data.setDoubleData(0.56);
            list.add(data);
        }
        return list;
    }
}
