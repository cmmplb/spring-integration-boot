package io.github.cmmplb.excel.handler;

import com.alibaba.excel.metadata.data.DataFormatData;
import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
 * @author penglibo
 * @date 2023-11-28 11:01:33
 * @since jdk 1.8
 * 自定义设置列数据格式(这个用于导出时没有数据的单元格设置格式)
 */

@Slf4j
public class DataFormatSheetWriteHandler implements SheetWriteHandler {

    /**
     * key-对应表头列索引, 从0开始
     * value-内容格式索引, 对应：{@link com.alibaba.excel.constant.BuiltinFormats}
     */
    private final Map<Integer, DataFormatData> dataFormatMap;

    public DataFormatSheetWriteHandler(Map<Integer, DataFormatData> dataFormatMap) {
        this.dataFormatMap = dataFormatMap;
    }

    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
        if (!CollectionUtils.isEmpty(this.dataFormatMap)){
            for (Map.Entry<Integer, DataFormatData> entry : dataFormatMap.entrySet()) {
                /**
                 * 对应格式索引:{@link com.alibaba.excel.constant.BuiltinFormats}
                 */
                CellStyle cellStyle = writeWorkbookHolder.getCachedWorkbook().createCellStyle();
                cellStyle.setDataFormat(writeWorkbookHolder.createDataFormat(entry.getValue(), true));
                writeSheetHolder.getSheet().setDefaultColumnStyle(entry.getKey(), cellStyle);
            }
        }
    }
}
