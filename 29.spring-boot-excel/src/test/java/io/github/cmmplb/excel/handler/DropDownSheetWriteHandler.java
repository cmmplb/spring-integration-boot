package io.github.cmmplb.excel.handler;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import io.github.cmmplb.excel.handler.data.DropDown;
import io.github.cmmplb.excel.handler.utils.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * @author penglibo
 * @date 2023-11-28 11:01:33
 * @since jdk 1.8
 * 动态填充下拉框
 * <a><a href="https://blog.csdn.net/weixin_43558927/article/details/127617224">EasyExcel生成带下拉列表或二级级联列表</a></a>
 */

@Slf4j
public class DropDownSheetWriteHandler implements SheetWriteHandler {

    /**
     * 父级sheet页签名称
     */
    private static final String PARENT_SHEET = "parent_sheet";

    /**
     * 子集sheet页签名称
     */
    private static final String CHILDREN_SHEET = "children_sheet";

    /**
     * 每个sheet页临时存储的下拉数据条数
     */
    private static final int LIMITATION = 1000;

    /**
     * 下拉框数据
     */
    private final List<DropDown> dropDownList;

    public DropDownSheetWriteHandler(List<DropDown> dropDownList) {
        this.dropDownList = dropDownList;
    }

    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
        if (!CollectionUtils.isEmpty(dropDownList)) {
            // 获取工作簿
            Workbook book = writeWorkbookHolder.getWorkbook();
            // 父级sheet页、子集sheet页
            Sheet tmpParentSheet = null, tmpChildrenSheet = null;
            // 父级临时表开始的列索引、子集临时表开始的列索引
            int parentColumnIndex = 0, tmpChildrenColumnIndex = 0;

            for (DropDown dropDown : this.dropDownList) {
                // 是否是级联下拉框序列数据
                if (!dropDown.isCascade()) {
                    // 独立下拉直接写入文件sheet页
                    if (!CollectionUtils.isEmpty(dropDown.getDataList())) {
                        Sheet sheet = writeSheetHolder.getSheet();
                        DataValidationHelper helper = sheet.getDataValidationHelper();
                        // 起始行、终止行、起始列、终止列
                        CellRangeAddressList addressList = new CellRangeAddressList(
                                dropDown.getFirstRow(),
                                dropDown.getLastRow(),
                                dropDown.getDataIndex(),
                                dropDown.getDataIndex()
                        );
                        DataValidationConstraint constraint = helper.createExplicitListConstraint(dropDown.getDataList().toArray(new String[0]));
                        ExcelUtil.setDataValidation(sheet, helper, dropDown, addressList, constraint);
                    }
                } else {
                    // 级联下拉
                    List<String> parentList = dropDown.getParentList();
                    // 处理父级
                    if (!CollectionUtils.isEmpty(parentList)) {
                        // 创建隐藏的sheet页
                        tmpParentSheet = ExcelUtil.createTmpSheet(book, tmpParentSheet, PARENT_SHEET);
                        String columnName = ExcelUtil.calculateColumnName(parentColumnIndex + 1);
                        final String formula = ExcelUtil.createFormulaForDropdown(tmpParentSheet, parentList.size(), columnName);
                        ExcelUtil.createDropdownElement(tmpParentSheet, parentList, parentColumnIndex);
                        parentColumnIndex++;
                        ExcelUtil.setDataValidation(
                                book,
                                dropDown,
                                writeSheetHolder.getSheet(),
                                tmpParentSheet,
                                formula, dropDown.getParentIndex(),
                                dropDown.getFirstRow(),
                                dropDown.getLastRow()
                        );
                        tmpParentSheet = parentList.size() >= LIMITATION ? null : tmpParentSheet;
                    }
                    // 处理子集
                    tmpChildrenSheet = ExcelUtil.createTmpSheet(book, tmpChildrenSheet, CHILDREN_SHEET);
                    Map<String, List<String>> childrenMap = dropDown.getChildrenMap();
                    for (Map.Entry<String, List<String>> en : childrenMap.entrySet()) {
                        String parentVal = ExcelUtil.formatNameManager(en.getKey());
                        List<String> childrenList = en.getValue();
                        if (CollUtil.isEmpty(childrenList)) {
                            continue;
                        }
                        // 从第一行第一列开始写入, 每一列写入子集数据
                        ExcelUtil.createDropdownElement(tmpChildrenSheet, childrenList, tmpChildrenColumnIndex);
                        if (childrenList.size() >= LIMITATION) {
                            tmpChildrenSheet = ExcelUtil.createTmpSheet(book, null, CHILDREN_SHEET);
                        }
                        final String columnName = ExcelUtil.calculateColumnName(tmpChildrenColumnIndex + 1);
                        tmpChildrenColumnIndex++;
                        final String formula = ExcelUtil.createFormulaForNameManger(tmpChildrenSheet, childrenList.size(), columnName);
                        ExcelUtil.createNameManager(book, parentVal, formula);
                    }
                    final String parentColumnName = ExcelUtil.calculateColumnName(dropDown.getParentIndex() + 1);
                    final String indirectFormula = ExcelUtil.createIndirectFormula(parentColumnName, dropDown.getFirstRow() + 1);
                    ExcelUtil.setDataValidation(book,
                            dropDown,
                            writeSheetHolder.getSheet(),
                            tmpChildrenSheet,
                            indirectFormula,
                            dropDown.getChildrenIndex(),
                            dropDown.getFirstRow(),
                            dropDown.getLastRow()
                    );
                }
            }
        }
    }


}
