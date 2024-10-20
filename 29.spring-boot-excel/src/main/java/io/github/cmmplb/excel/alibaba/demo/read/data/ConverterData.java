package io.github.cmmplb.excel.alibaba.demo.read.data;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.format.NumberFormat;
import io.github.cmmplb.excel.alibaba.demo.read.converter.CustomStringStringConverter;
import lombok.Data;

/**
 * @author penglibo
 * @date 2021-05-24 17:38:01
 * @since jdk 1.8
 */

@Data
public class ConverterData {

    /**
     * 我自定义 转换器, 不管数据库传过来什么 . 我给他加上“自定义：”
     */
    @ExcelProperty(converter = CustomStringStringConverter.class)
    private String string;

    /**
     * 这里用string 去接日期才能格式化. 我想接收年月日格式
     */
    @DateTimeFormat("yyyy年MM月dd日HH时mm分ss秒")
    private String date;

    /**
     * 我想接收百分比的数字
     */
    @NumberFormat("#.##%")
    private String doubleData;
}
