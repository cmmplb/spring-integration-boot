package io.github.cmmplb.excel.handler;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * @author penglibo
 * @date 2023-11-28 11:05:42
 * @since jdk 1.8
 * 自定义表头实现批注
 */

@Slf4j
public class CommentCellWriteHandler implements CellWriteHandler {

    /**
     * key-对应表头列索引, 从0开始
     * value-批注内容
     */
    private final Map<Integer, String> commentMap;


    public CommentCellWriteHandler(Map<Integer, String> commentMap) {
        this.commentMap = commentMap;
    }

    @Override
    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, List<WriteCellData<?>> cellDataList,
                                 Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
        if (isHead) {
            Sheet sheet = writeSheetHolder.getSheet();
            Drawing<?> drawingPatriarch = sheet.createDrawingPatriarch();
            if (!CollectionUtils.isEmpty(this.commentMap) && commentMap.containsKey(cell.getColumnIndex())) {
                String context = this.commentMap.get(cell.getColumnIndex());
                Comment comment = drawingPatriarch.createCellComment(new XSSFClientAnchor(0, 0, 0, 0, cell.getColumnIndex(), 0, 2, 1));
                // 输入批注信息
                comment.setString(new XSSFRichTextString(context));
                // 将批注添加到单元格对象中
                cell.setCellComment(comment);
            }
        }
    }
}
