package com.cmmplb.excel.alibaba.demo.export.export_12_mergeWrite;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.List;

/**
 * <a href="https://blog.csdn.net/m0_72960906/article/details/133382956">...</a>
 * 合并单元格-相同行
 */
public class MergeStrategyCellWriteHandler implements CellWriteHandler {

    @Override
    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, List<WriteCellData<?>> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
        if (isHead) {
            //如果是表头不做处理
            return;
        }
        // 如果当前是第一行不做处理
        if (relativeRowIndex == 0) {
            return;
        }
        // 获取当前行下标, 上一行下标, 上一行对象, 上一列对象
        Sheet sheet = cell.getSheet();
        int rowIndex = cell.getRowIndex();
        int rowIndexPrev = rowIndex - 1;
        Row row = sheet.getRow(rowIndexPrev);
        Cell cellPrev = row.getCell(cell.getColumnIndex());

        // 得到当前单元格值和上一行单元格
        Object cellValue = cell.getCellType() == CellType.STRING ? cell.getStringCellValue() : cell.getNumericCellValue();
        Object cellValuePrev = cellPrev.getCellType() == CellType.STRING ? cellPrev.getStringCellValue() : cellPrev.getNumericCellValue();

        // 如果当前单元格和上一行单元格值相等就合并
        if (!cellValue.equals(cellValuePrev)) {
            return;
        }
        // 获取已有策略
        List<CellRangeAddress> mergedRegions = sheet.getMergedRegions();
        boolean mergen = false;
        for (int i = 0; i < mergedRegions.size(); i++) {
            CellRangeAddress cellAddresses = mergedRegions.get(i);
            if (cellAddresses.isInRange(rowIndexPrev, cell.getColumnIndex())) {
                sheet.removeMergedRegion(i);
                cellAddresses.setLastRow(rowIndex);
                sheet.addMergedRegion(cellAddresses);
                mergen = true;
                break;
            }
        }
        if (!mergen) {
            CellRangeAddress cellAddresses = new CellRangeAddress(rowIndexPrev, rowIndex, cell.getColumnIndex(), cell.getColumnIndex());
            sheet.addMergedRegion(cellAddresses);
        }
    }
}