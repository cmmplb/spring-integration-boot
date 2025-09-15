package io.github.cmmplb.excel.handler.test;

import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@RequiredArgsConstructor
public class SelectDataSheetWriteHandler implements SheetWriteHandler {

    private final Map<Integer, ExcelSelectDataColumn> selectedMap;

    @Override
    public void beforeSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
    }

    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
        //尽量少的创建sheet, 也可以只用一个额外的sheet放这些下拉数据
        AtomicReference<Sheet> tmpSheet = new AtomicReference<>(null);
        AtomicInteger tmpSheetStartCol = new AtomicInteger(0);


        AtomicReference<Sheet> tmpCascadeSheet = new AtomicReference<>(null);
        AtomicInteger tmpCascadeSheetStartCol = new AtomicInteger(0);

        for (Map.Entry<Integer, ExcelSelectDataColumn> entry : selectedMap.entrySet()) {
            Integer colIndex = entry.getKey();
            ExcelSelectDataColumn model = entry.getValue();
            if (StrUtil.isNotEmpty(model.getParentColumn())) {
                // 级联下拉框
                Sheet sheet = ExcelUtil.addCascadeValidationToSheet(
                        writeWorkbookHolder,
                        writeSheetHolder,
                        tmpCascadeSheet.get(),
                        (Map<String, List<String>>) model.getSource(),
                        tmpCascadeSheetStartCol,
                        model.getParentColumnIndex(),
                        colIndex,
                        model.getFirstRow(),
                        model.getLastRow()
                );
                tmpCascadeSheet.set(sheet);
            } else {
                // 单下拉框
                Sheet sheet = ExcelUtil.addSelectValidationToSheet(
                        writeWorkbookHolder,
                        writeSheetHolder,
                        tmpSheet.get(),
                        (List<String>) model.getSource(),
                        tmpSheetStartCol,
                        colIndex,
                        model.getFirstRow(),
                        model.getLastRow()
                );
                tmpSheet.set(sheet);
            }
        }
    }
}
