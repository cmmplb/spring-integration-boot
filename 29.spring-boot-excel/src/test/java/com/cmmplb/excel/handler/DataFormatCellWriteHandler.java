package com.cmmplb.excel.handler;

import com.alibaba.excel.metadata.data.DataFormatData;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.handler.context.CellWriteHandlerContext;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
 * @author penglibo
 * @date 2023-11-28 11:11:41
 * @since jdk 1.8
 * 自定义设置列数据格式(这个用于导出时有数据的单元格设置格式-没有数据的单元格依旧是常规)
 */
public class DataFormatCellWriteHandler implements CellWriteHandler {

    /**
     * key-对应表头列索引, 从0开始
     * value-内容格式索引, 对应：{@link com.alibaba.excel.constant.BuiltinFormats}
     */
    private final Map<Integer, DataFormatData> dataFormatMap;

    public DataFormatCellWriteHandler(Map<Integer, DataFormatData> dataFormatMap) {
        this.dataFormatMap = dataFormatMap;
    }

    @Override
    public void afterCellDispose(CellWriteHandlerContext context) {
        if (!CollectionUtils.isEmpty(this.dataFormatMap)){
            for (Map.Entry<Integer, DataFormatData> entry : this.dataFormatMap.entrySet()) {
                if (context.getColumnIndex().equals(entry.getKey())) {
                    /**
                     * 对应格式索引:{@link com.alibaba.excel.constant.BuiltinFormats}
                     */
                    context.getFirstCellData().getOrCreateStyle().setDataFormatData(entry.getValue());
                }
            }
        }
    }
}