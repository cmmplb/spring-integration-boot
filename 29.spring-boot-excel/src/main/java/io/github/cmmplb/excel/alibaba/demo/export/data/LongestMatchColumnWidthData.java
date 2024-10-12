package io.github.cmmplb.excel.alibaba.demo.export.data;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author penglibo
 * @date 2021-05-24 16:14:59
 * @since jdk 1.8
 */

@Data
public class LongestMatchColumnWidthData {

    @ExcelProperty("字符串标题")
    private String string;

    @ExcelProperty("日期标题很长日期标题很长日期标题很长很长")
    private Date date;

    @ExcelProperty("数字")
    private Double doubleData;
}
