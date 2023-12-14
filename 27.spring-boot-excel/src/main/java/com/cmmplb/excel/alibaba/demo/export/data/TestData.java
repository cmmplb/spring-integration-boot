package com.cmmplb.excel.alibaba.demo.export.data;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.format.NumberFormat;
import com.alibaba.excel.annotation.write.style.*;
import com.alibaba.excel.enums.poi.BorderStyleEnum;
import com.alibaba.excel.enums.poi.FillPatternTypeEnum;
import com.alibaba.excel.enums.poi.HorizontalAlignmentEnum;
import com.alibaba.excel.enums.poi.VerticalAlignmentEnum;
import com.cmmplb.core.utils.DateUtil;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author penglibo
 * @date 2021-05-22 17:31:23
 * @since jdk 1.8
 * 物料模版
 * 默认是全部实体字段都会参与读写，不管你是否加了@ExcelProperty注解
 * 或者在类的最上面加入@ExcelIgnoreUnannotated注解，加入这个注解后只有加了@ExcelProperty才会参与读写。
 */

@Data
// 头背景设置色
@HeadStyle(fillPatternType = FillPatternTypeEnum.SOLID_FOREGROUND, fillForegroundColor = 27) // 26 浅黄 // 28 浅蓝
// 头字体
@HeadFontStyle(fontHeightInPoints = 14)
// 头部行高
@HeadRowHeight(21)
// 列宽
@ColumnWidth(18)
// 内容行高
@ContentRowHeight(20)
// 内容的背景色，居中
@ContentStyle(
        fillPatternType = FillPatternTypeEnum.NO_FILL, // 不填充
        horizontalAlignment = HorizontalAlignmentEnum.CENTER, // 水平居中
        verticalAlignment = VerticalAlignmentEnum.CENTER, // 垂直居中
        // 边框线
        borderLeft = BorderStyleEnum.THIN,
        borderRight = BorderStyleEnum.THIN,
        borderTop = BorderStyleEnum.THIN,
        borderBottom = BorderStyleEnum.THIN
)
// 内容字体
@ContentFontStyle(fontHeightInPoints = 11)
public class TestData {

    /**
     * 物料名称
     */
    @ExcelProperty(value = "物料名称")
    private String name;

    /**
     * 物料编码
     */
    @ExcelProperty(value = "物料编码")
    private String code;

    /**
     * 一级分类
     */
    @ExcelProperty(value = "一级分类名称")
    private String oneCategoryName;

    /**
     * 二级分类
     */
    @ExcelProperty(value = "二级分类名称")
    private String twoCategoryName;

    /**
     * 品牌名称
     */
    @ExcelProperty(value = "品牌名称")
    private String brandName;

    /**
     * 单位名称
     */
    @ExcelProperty(value = "单位名称")
    private String unitName;

    /**
     * 规格与包装
     */
    @ExcelProperty(value = "规格与包装")
    private String spec;

    /**
     * 适用场景
     */
    @ExcelProperty(value = "适用场景")
    private String scene;

    /**
     * 技术参数
     */
    @ExcelProperty(value = "技术参数")
    private String reference;

    /**
     * 起订量
     */
    @ExcelProperty(value = "起订量")
    private BigDecimal minimumQuantity;

    /**
     * 单点起运量
     */
    @ExcelProperty(value = "单点起运量")
    private BigDecimal inducement;

    /**
     * 交付周期
     */
    @ExcelProperty(value = "交付周期/天")
    private BigDecimal cycle;

    /**
     * 重量
     */
    @ExcelProperty(value = "重量/千克")
    private BigDecimal weight;

    /**
     * 体积
     */
    @ExcelProperty(value = "体积/立方米")
    /**
     * 对应文本内容格式索引:{@link com.alibaba.excel.constant.BuiltinFormats}
     */
    // @ContentStyle(dataFormat = 4)
    // 加下横线并且加多个空格,保留几位小数就在后面加0
    // @NumberFormat("0.000_ ")
    private BigDecimal volume;

    /**
     * 对应日期格式索引:{@link com.alibaba.excel.constant.BuiltinFormats}
     */
    @ContentStyle(dataFormat = 27)
    // ContentStyle优先级比DateTimeFormat高
    // @DateTimeFormat("yyyy年MM月dd HH时mm分ss秒")
    @ExcelProperty(value = "时间")
    private Date date;

    /**
     * 元数据
     * @return
     */
    public static List<TestData> data() {
        List<TestData> list = new ArrayList<TestData>();
        TestData data = new TestData();
        data.setName("三层正方型陈列架");
        data.setCode("100001");
        data.setOneCategoryName("终端物料");
        data.setTwoCategoryName("水果品");
        data.setBrandName("怡宝");
        data.setSpec("1套/箱");
        data.setScene("奖品");
        data.setMinimumQuantity(new BigDecimal("2000"));
        data.setInducement(new BigDecimal("3000"));
        data.setCycle(new BigDecimal("1000"));
        data.setUnitName("套");
        data.setScene("仓库");
        data.setReference("42cm*42cm*90cm，货盘（HIPS）/工艺（注塑）/立柱（PVC挤塑）单色丝印货盘印刷，广告画面为胶印附3MM雪弗板");
        data.setWeight(new BigDecimal("200"));
        data.setVolume(new BigDecimal("100"));
        data.setDate(new Date());
        list.add(data);
        return list;
    }
}
