package com.cmmplb.excel.handler;

import com.alibaba.excel.metadata.data.DataFormatData;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.handler.context.CellWriteHandlerContext;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
 * @author penglibo
 * @date 2023-11-28 11:05:42
 * @since jdk 1.8
 * 整合单元格操作
 */

@Slf4j
public class IntegrationCellWriteHandler implements CellWriteHandler {

    /**
     * key-对应表头列索引，从0开始
     * value-批注内容
     */
    private final Map<Integer, String> commentMap;

    /**
     * key-对应表头列索引，从0开始
     * value-内容格式索引，对应：{@link com.alibaba.excel.constant.BuiltinFormats}
     * 自定义设置列数据格式(这个用于导出时有数据的单元格设置格式-没有数据的单元格依旧是常规)
     */
    private final Map<Integer, DataFormatData> dataFormatMap;

    public IntegrationCellWriteHandler(Map<Integer, String> commentMap, Map<Integer, DataFormatData> dataFormatMap) {
        this.commentMap = commentMap;
        this.dataFormatMap = dataFormatMap;
    }

    @Override
    public void afterCellDispose(CellWriteHandlerContext context) {
        afterCellDispose(context.getFirstCellData().getOrCreateStyle(), context.getColumnIndex(), context.getWriteSheetHolder(), context.getCell(), context.getHead());
    }

    public void afterCellDispose(WriteCellStyle writeCellStyle, Integer columnIndex, WriteSheetHolder writeSheetHolder, Cell cell, Boolean isHead) {
        if (isHead) {
            // 表头操作-添加批注
            comment(writeSheetHolder, cell);
        } else {
            // 内容单元格添加数据格式（有数据的单元格）
            dataFormat(writeCellStyle, columnIndex);
        }
    }

    /**
     * 添加单元格数据格式（有数据的单元格）
     */
    private void dataFormat(WriteCellStyle writeCellStyle, Integer columnIndex) {
        if (!CollectionUtils.isEmpty(this.dataFormatMap)) {
            for (Map.Entry<Integer, DataFormatData> entry : this.dataFormatMap.entrySet()) {
                if (columnIndex.equals(entry.getKey())) {
                    /**
                     * 对应格式索引:{@link com.alibaba.excel.constant.BuiltinFormats}
                     */
                    writeCellStyle.setDataFormatData(entry.getValue());
                }
            }
        }
    }

    /**
     * 添加批注
     */
    private void comment(WriteSheetHolder writeSheetHolder, Cell cell) {
        Sheet sheet = writeSheetHolder.getSheet();
        Drawing<?> drawingPatriarch = sheet.createDrawingPatriarch();
        if (!CollectionUtils.isEmpty(commentMap) && commentMap.containsKey(cell.getColumnIndex())) {
            String context = this.commentMap.get(cell.getColumnIndex());
            Comment comment = drawingPatriarch.createCellComment(new XSSFClientAnchor(0, 0, 0, 0, cell.getColumnIndex(), 0, 2, 1));
            // 输入批注信息
            comment.setString(new XSSFRichTextString(context));
            // 将批注添加到单元格对象中
            cell.setCellComment(comment);
        }
    }
}
