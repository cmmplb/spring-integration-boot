package com.cmmplb.excel.handler.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.DataValidation;

import java.util.List;
import java.util.Map;

/**
 * @author penglibo
 * @date 2023-11-29 10:24:06
 * @since jdk 1.8
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DropDown {

    /**
     * 是否是级联下拉框序列数据
     */
    private boolean cascade = false;

    /**
     * 单下拉框序列数据
     */
    private List<String> dataList;

    /**
     * 单下拉框列索引-从0开始
     */
    private Integer dataIndex;

    /**
     * 下拉框添加的开始行
     */
    private Integer firstRow = 1;

    /**
     * 下拉框添加的结束行
     */
    private Integer lastRow = 65535;

    /**
     * 级联父级下拉框序列数据-这里单独拿出来没用childrenMap里面的key是为了有排序,keySet的话无序
     */
    private List<String> parentList;

    /**
     * 级联子级下拉框序列数据
     * key-父级序列数据
     * value-子级下拉框序列数据
     */
    private Map<String, List<String>> childrenMap;

    /**
     * 级联父级下拉框索引-从0开始
     */
    private Integer parentIndex;

    /**
     * 级联子级下拉框索引-从0开始
     */
    private Integer childrenIndex;

    /**
     * 是否忽略空值-默认不忽略，为空就提示
     */
    private Boolean emptyCellAllowed = false;

    /**
     * 单元格右侧是否显示下拉框，默认为true
     */
    private Boolean suppressDropDownArrow = true;

    /**
     * 错误信息
     */
    private Error error;

    /**
     * 点击单元格时的提示弹窗
     */
    private Prompt prompt;
}
