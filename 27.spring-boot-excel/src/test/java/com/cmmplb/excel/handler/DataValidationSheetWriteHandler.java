package com.cmmplb.excel.handler;

import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import com.cmmplb.excel.handler.data.DataValida;
import com.cmmplb.excel.handler.data.Error;
import com.cmmplb.excel.handler.data.Prompt;
import com.cmmplb.excel.handler.utils.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * @author penglibo
 * @date 2023-11-28 11:01:33
 * @since jdk 1.8
 * 填充字段规则校验
 */

@Slf4j
public class DataValidationSheetWriteHandler implements SheetWriteHandler {

    /**
     * key-对应表头列索引，从0开始
     * value-具体下拉列表
     */
    private final Map<Integer, List<DataValida>> dataValidaMap;

    public DataValidationSheetWriteHandler(Map<Integer, List<DataValida>> dataValidaMap) {
        this.dataValidaMap = dataValidaMap;
    }

    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
        Sheet sheet = writeSheetHolder.getSheet();
        DataValidationHelper helper = sheet.getDataValidationHelper();
        if (!CollectionUtils.isEmpty(this.dataValidaMap)) {
            for (Map.Entry<Integer, List<DataValida>> entry : this.dataValidaMap.entrySet()) {
                Integer key = entry.getKey();
                List<DataValida> dataValidaList = entry.getValue();
                if (!CollectionUtils.isEmpty(dataValidaList)) {
                    // 创建规则
                    DataValidationConstraint constraint = null;
                    for (DataValida dataValida : dataValidaList) {
                        if (dataValida.getValidationType().equals(DataValidationConstraint.ValidationType.ANY)) {
                            constraint = ExcelUtil.getFormulaAny(helper);
                        } else if (dataValida.getValidationType().equals(DataValidationConstraint.ValidationType.INTEGER)) {
                            constraint = ExcelUtil.getIntegerConstraint(helper, dataValida);
                        } else if (dataValida.getValidationType().equals(DataValidationConstraint.ValidationType.DECIMAL)) {
                            constraint = ExcelUtil.getDecimalConstraint(helper, dataValida);
                        } else if (dataValida.getValidationType().equals(DataValidationConstraint.ValidationType.LIST)) {
                            constraint = ExcelUtil.getListConstraint(helper, dataValida);
                        } else if (dataValida.getValidationType().equals(DataValidationConstraint.ValidationType.DATE)) {
                            constraint = ExcelUtil.getDateConstraint(helper, dataValida);
                        } else if (dataValida.getValidationType().equals(DataValidationConstraint.ValidationType.TIME)) {
                            constraint = ExcelUtil.getTimeConstraint(helper, dataValida);
                        } else if (dataValida.getValidationType().equals(DataValidationConstraint.ValidationType.TEXT_LENGTH)) {
                            constraint = ExcelUtil.getTextLengthConstraint(helper, dataValida);
                        } else if (dataValida.getValidationType().equals(DataValidationConstraint.ValidationType.FORMULA)) {
                            constraint = ExcelUtil.getFormulaConstraint(helper, dataValida);
                        } else {
                            log.info("无效类型");
                            continue;
                        }
                        CellRangeAddressList addressList = new CellRangeAddressList(1, 65535, entry.getKey(), entry.getKey());
                        DataValidation dataValidation = helper.createValidation(constraint, addressList);
                        dataValidation.setEmptyCellAllowed(dataValida.getEmptyCellAllowed());
                        if (null != dataValida.getError()) {
                            dataValidation.setShowErrorBox(true);
                            Error error = dataValida.getError();
                            dataValidation.createErrorBox(error.getTitle(), error.getContent());
                            dataValidation.setErrorStyle(error.getErrorStyle());
                        }
                        if (null != dataValida.getPrompt()) {
                            dataValidation.setShowPromptBox(true);
                            Prompt prompt = dataValida.getPrompt();
                            dataValidation.createPromptBox(prompt.getTitle(), prompt.getContent());
                        }
                        sheet.addValidationData(dataValidation);
                    }
                }
            }
        }
    }
}
