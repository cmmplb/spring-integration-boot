package io.github.cmmplb.excel.alibaba.demo.export.data;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.*;
import com.alibaba.excel.enums.poi.FillPatternTypeEnum;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author penglibo
 * @date 2021-05-22 17:31:23
 * @since jdk 1.8
 * 默认是全部实体字段都会参与读写, 不管你是否加了@ExcelProperty注解
 * 或者在类的最上面加入@ExcelIgnoreUnannotated注解, 加入这个注解后只有加了@ExcelProperty才会参与读写. 
 */

@Data
// 头背景设置色
@HeadStyle(fillPatternType = FillPatternTypeEnum.NO_FILL, fillForegroundColor = 3)
// 头字体设置成20
@HeadFontStyle(fontHeightInPoints = 20)
// 内容的背景设置成绿色 IndexedColors.GREEN.getIndex()
// @ContentStyle(fillPatternType = FillPatternType.SOLID_FOREGROUND, fillForegroundColor = 17)
// 内容字体设置成20
// @ContentFontStyle(fontHeightInPoints = 20)
// 内容行高
@ContentRowHeight(15)
// 头部内容
@HeadRowHeight(30)
// 列宽
@ColumnWidth(25)
public class DemoData {

    @ExcelProperty("字符串标题")
    // @ContentStyle(leftBorderColor = 1, rightBorderColor = 2, topBorderColor = 0)
    private String string;

    @ExcelProperty("日期标题")
    // 字符串的头背景设置成粉红 IndexedColors.PINK.getIndex()
    // @HeadStyle(fillPatternType = FillPatternTypeEnum.SOLID_FOREGROUND, fillForegroundColor = 14)
    // 字符串的头字体设置成20
    // @HeadFontStyle(fontHeightInPoints = 30)
    // 字符串的内容的背景设置成天蓝 IndexedColors.SKY_BLUE.getIndex()
    // @ContentStyle(fillPatternType = FillPatternTypeEnum.SOLID_FOREGROUND, fillForegroundColor = 40)
    // 字符串的内容字体设置成20
    // @ContentFontStyle(fontHeightInPoints = 30)
    private Date date;

    @ExcelProperty("数字标题")
    private Double doubleData;

    @ExcelIgnore // 忽略这个字段
    private String ignore;

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
