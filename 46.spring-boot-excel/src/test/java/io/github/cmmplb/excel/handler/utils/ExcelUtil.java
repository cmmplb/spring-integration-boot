package io.github.cmmplb.excel.handler.utils;

import cn.hutool.core.util.StrUtil;
import io.github.cmmplb.excel.handler.data.DataValida;
import io.github.cmmplb.excel.handler.data.DropDown;
import io.github.cmmplb.excel.handler.data.Error;
import io.github.cmmplb.excel.handler.data.Prompt;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author penglibo
 * @date 2023-11-29 17:14:05
 * @since jdk 1.8
 */
public class ExcelUtil {

    public static Sheet createTmpSheet(Workbook book, Sheet tmpSheet, String sheetName) {
        String actualName = sheetName + book.getNumberOfSheets();
        if (null == tmpSheet) {
            tmpSheet = book.createSheet(actualName);
        }
        return tmpSheet;
    }

    public static String calculateColumnName(int columnCount) {
        final int minimumExponent = minimumExponent(columnCount);
        final int base = 26, layers = (minimumExponent == 0 ? 1 : minimumExponent);
        final List<Character> sequence = Lists.newArrayList();
        int remain = columnCount;
        for (int i = 0; i < layers; i++) {
            int step = (int) (remain / Math.pow(base, i) % base);
            step = step == 0 ? base : step;
            buildColumnNameSequence(sequence, step);
            remain = remain - step;
        }
        return sequence.stream()
                .map(Object::toString)
                .collect(Collectors.joining());
    }

    public static String createFormulaForDropdown(Sheet tmpSheet, int size, String columnName) {
        final String format = "=%s!$%s$%s:$%s$%s";
        return String.format(format, tmpSheet.getSheetName(), columnName, "1", columnName, size);
    }

    public static void createDropdownElement(Sheet tmpSheet, List<String> childrenList, int columnIndex) {
        int rowIndex = 0;
        for (String children : childrenList) {
            Row row = tmpSheet.getRow(rowIndex);
            if (null == row) {
                row = tmpSheet.createRow(rowIndex);
            }
            // 每一行创建子集列
            row.createCell(columnIndex).setCellValue(children);
            rowIndex++;
        }
    }

    public static String formatNameManager(String name) {
        //  拼接允许的"_"字符, 以绕过“非法”字符开头的情况
        Preconditions.checkArgument(StrUtil.isNotEmpty(name));
        return "_" + name;
    }


    public static String createFormulaForNameManger(Sheet tmpSheet, int size, String columnName) {
        final String format = "%s!$%s$%s:$%s$%s";
        return String.format(format, tmpSheet.getSheetName(), columnName, "1", columnName, size);
    }

    public static void createNameManager(Workbook workbook, String nameName, String formula) {
        // 处理存在名称管理器复用的情况
        Name name = workbook.getName(nameName);
        if (name != null) {
            return;
        }
        name = workbook.createName();
        name.setNameName(nameName);
        name.setRefersToFormula(formula);
    }

    public static String createIndirectFormula(String columnName, int startRow) {
        // 在通过INDIRECT引用前, 将父级的值拼接上"_"以对应名称管理器创建的mapping关系
        final String format = "INDIRECT(CONCATENATE(\"_\",$%s%s))";
        return String.format(format, columnName, startRow);
    }

    private static void buildColumnNameSequence(List<Character> sequence, int columnIndex) {
        final int capitalAAsIndex = 64;
        sequence.add(0, (char) (capitalAAsIndex + columnIndex));
    }

    private static int minimumExponent(int source) {
        final int base = 26;
        int exponent = 0;
        while (Math.pow(base, exponent) < source) {
            exponent++;
        }
        return exponent;
    }

    public static void setDataValidation(Sheet sheet, DataValidationHelper helper, DropDown dropDown, CellRangeAddressList addressList, DataValidationConstraint constraint) {
        DataValidation dataValidation = helper.createValidation(constraint, addressList);

        dataValidation.setSuppressDropDownArrow(dropDown.getSuppressDropDownArrow());
        dataValidation.setEmptyCellAllowed(dropDown.getEmptyCellAllowed());
        if (null != dropDown.getError()) {
            dataValidation.setShowErrorBox(true);
            Error error = dropDown.getError();
            dataValidation.createErrorBox(error.getTitle(), error.getContent());
            dataValidation.setErrorStyle(error.getErrorStyle());
        }
        if (null != dropDown.getPrompt()) {
            dataValidation.setShowPromptBox(true);
            Prompt prompt = dropDown.getPrompt();
            dataValidation.createPromptBox(prompt.getTitle(), prompt.getContent());
        }
        sheet.addValidationData(dataValidation);
    }

    public static void setDataValidation(Workbook workbook, DropDown dropDown, Sheet sheet, Sheet tmpSheet, String formula, int selfCol, int startRow, int endRow) {
        DataValidationHelper helper = sheet.getDataValidationHelper();
        final DataValidationConstraint constraint = helper.createFormulaListConstraint(formula);
        CellRangeAddressList addressList = new CellRangeAddressList(startRow, endRow, selfCol, selfCol);
        final DataValidation dataValidation = helper.createValidation(constraint, addressList);

        dataValidation.setSuppressDropDownArrow(dropDown.getSuppressDropDownArrow());
        dataValidation.setEmptyCellAllowed(dropDown.getEmptyCellAllowed());
        if (null != dropDown.getError()) {
            dataValidation.setShowErrorBox(true);
            Error error = dropDown.getError();
            dataValidation.createErrorBox(error.getTitle(), error.getContent());
            dataValidation.setErrorStyle(error.getErrorStyle());
        }
        if (null != dropDown.getPrompt()) {
            dataValidation.setShowPromptBox(true);
            Prompt prompt = dropDown.getPrompt();
            dataValidation.createPromptBox(prompt.getTitle(), prompt.getContent());
        }
        sheet.addValidationData(dataValidation);
        hideSheet(workbook, tmpSheet);
    }

    private static void hideSheet(Workbook workbook, Sheet sheet) {
        final int sheetIndex = workbook.getSheetIndex(sheet);
        if (sheetIndex > -1) {
            workbook.setSheetHidden(sheetIndex, true);
        }
    }

    /**
     * 字符串校验规则
     */
    public static DataValidationConstraint getFormulaAny(DataValidationHelper helper) {
        return helper.createCustomConstraint("@");
    }

    /**
     * 自定义校验规则
     */
    public static DataValidationConstraint getFormulaConstraint(DataValidationHelper helper, DataValida dataValida) {
        return helper.createCustomConstraint(dataValida.getFormula1());
    }

    /**
     * 长度校验
     */
    public static DataValidationConstraint getTextLengthConstraint(DataValidationHelper helper, DataValida dataValida) {
        return helper.createTextLengthConstraint(
                dataValida.getOperatorType(),
                dataValida.getFormula1(),
                dataValida.getFormula2()
        );
    }

    /**
     * 时间校验
     */
    public static DataValidationConstraint getTimeConstraint(DataValidationHelper helper, DataValida dataValida) {
        return helper.createTimeConstraint(
                dataValida.getOperatorType(),
                dataValida.getFormula1(),
                dataValida.getFormula2()
        );
    }

    /**
     * 日期校验
     */
    public static DataValidationConstraint getDateConstraint(DataValidationHelper helper, DataValida dataValida) {
        return helper.createDateConstraint(
                dataValida.getOperatorType(),
                dataValida.getFormula1(),
                dataValida.getFormula2(),
                dataValida.getDateFormat()
        );
    }

    /**
     * 列表序列下拉校验
     */
    public static DataValidationConstraint getListConstraint(DataValidationHelper helper, DataValida dataValida) {
        return helper.createExplicitListConstraint(dataValida.getDataList().toArray(new String[0]));
    }

    /**
     * 小数校验
     */
    public static DataValidationConstraint getDecimalConstraint(DataValidationHelper helper, DataValida dataValida) {
        return helper.createDecimalConstraint(
                dataValida.getOperatorType(),
                dataValida.getFormula1(),
                dataValida.getFormula2()
        );
    }

    /**
     * 整数校验
     */
    public static DataValidationConstraint getIntegerConstraint(DataValidationHelper helper, DataValida dataValida) {
        return helper.createIntegerConstraint(
                dataValida.getOperatorType(),
                dataValida.getFormula1(),
                dataValida.getFormula2()
        );
    }
}
