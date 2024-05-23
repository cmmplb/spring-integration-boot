package com.cmmplb.excel.handler.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;

import java.util.List;

/**
 * @author penglibo
 * @date 2023-11-29 09:20:08
 * @since jdk 1.8
 */

@Data
public class DataValida {

    /**
     * 校验类型，对应：{@link DataValidationConstraint.ValidationType}
     */
    private Integer validationType = DataValidationConstraint.ValidationType.ANY;

    /**
     * 操作类型，对应：{@link DataValidationConstraint.OperatorType}
     * List下拉类型为空
     */
    private Integer operatorType;

    /**
     * 规则值1-List下拉类型为空
     */
    private String formula1;

    /**
     * 规则值2-List下拉类型为空
     */
    private String formula2;

    /**
     * 校验类型DataValidationConstraint.ValidationType.LIST-对应的下拉框序列数据
     */
    private List<String> dataList;

    /**
     * 校验类型DataValidationConstraint.ValidationType.DATE-对应的校验日期格式
     */
    private String dateFormat = "yyyy-MM-dd HH:mm:ss";

    /**
     * 是否忽略空值-默认不忽略，为空就提示
     */
    private Boolean emptyCellAllowed = false;

    /**
     * 错误信息
     */
    private Error error;

    /**
     * 点击单元格时的提示弹窗
     */
    private Prompt prompt;
}
