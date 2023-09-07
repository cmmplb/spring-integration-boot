package com.cmmplb.excel.alibaba.demo.export.data;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.*;
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
@ContentStyle(fillPatternType = FillPatternTypeEnum.NO_FILL, horizontalAlignment = HorizontalAlignmentEnum.CENTER, verticalAlignment = VerticalAlignmentEnum.CENTER)
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
     * 一级分类id
     */
    @ExcelIgnore
    private String oneCategoryId;

    /**
     * 一级分类
     */
    @ExcelProperty(value = "一级分类")
    private String oneCategoryName;

    /**
     * 二级分类id
     */
    @ExcelIgnore
    private String twoCategoryId;

    /**
     * 二级分类
     */
    @ExcelProperty(value = "二级分类")
    private String twoCategoryName;

    /**
     * 单价
     */
    @ExcelProperty(value = "单价")
    private BigDecimal price;

    /**
     * 价格有效期
     */
    @DateTimeFormat("yyyy-MM-dd")
    @ExcelProperty(value = "价格有效期")
    private String priceDateName;

    /**
     * 价格有效期
     */
    @DateTimeFormat("yyyy-MM-dd")
    @ExcelIgnore
    private Date priceDate;

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
     * 起订量
     */
    @ExcelProperty(value = "起订量")
    private Integer minimumQuantity;

    /**
     * 单点起运量
     */
    @ExcelProperty(value = "单点起运量")
    private Integer inducement;

    /**
     * 交付周期
     */
    @ExcelProperty(value = "交付周期")
    private Integer cycle;

    /**
     * 税率
     */
    @ExcelProperty(value = "税率")
    private String tax;

    /**
     * 品牌id
     */
    @ExcelIgnore
    private String brandId;

    /**
     * 品牌
     */
    @ExcelProperty(value = "品牌")
    private String brandName;

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
        data.setPrice(new BigDecimal("2000"));
        data.setPriceDateName(DateUtil.formatDate(new Date(), DateUtil.FORMAT_DATE_PATTERN_1));
        data.setSpec("1套/箱");
        data.setScene("奖品");
        data.setMinimumQuantity(2000);
        data.setInducement(3000);
        data.setCycle(1000);
        data.setTax("30");
        data.setBrandName("仓库");
        list.add(data);
        return list;
    }
}
