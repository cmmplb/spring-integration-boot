package io.github.cmmplb.excel.project.data;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author penglibo
 * @date 2021-08-24 16:08:43
 * @since jdk 1.8
 * 员工信息
 */

@Data
public class ExportData {

    /**
     * 员工编号-用名字去匹配,这里需要注意,如果名字重复,会导致只有一个字段读取到数据
     */
    @ExcelProperty(value = "员工编号")
    private String number;

    /**
     * 部门-强制读取第二个(从0开始),这里不建议index和name同时用,要么一个对象只用index,要么一个对象只用name去匹配
     */
    @ExcelProperty(index = 1,value = "部门")
    private String post;

    /**
     * 职务
     */
    @ExcelProperty(value = "职务")
    private String department;

    /**
     * 姓名
     */
    @ExcelProperty(value = "姓名")
    private String name;

    /**
     * 性别
     */
    @ExcelProperty(value = "性别")
    private String sex;

    /**
     * 学历
     */
    @ExcelProperty(value = "学历")
    private String education;

    /**
     * 年龄
     */
    @ExcelProperty(value = "年龄")
    private Integer age;

    /**
     * 出生年月
     */
    @DateTimeFormat("yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GTM+8")
    @ExcelProperty(value = "出生年月")
    private Date birthday;

    /**
     * 身份证号
     */
    @ExcelProperty(value = "身份证号")
    private String idCard;

    /**
     * 联系电话
     */
    @ExcelProperty(value = "联系电话")
    private String phone;

    /**
     * 入职时间
     */
    @DateTimeFormat("yyyy-MM-dd")
    @ExcelProperty(value = "入职时间")
    private Date entryDate;

    /**
     * 工作年限
     */
    @ExcelProperty(value = "工作年限")
    private Integer entryYears;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;

}
