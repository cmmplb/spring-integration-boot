package com.cmmplb.excel.project.listen;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.excel.metadata.CellData;
import com.cmmplb.excel.project.beans.ExcelResult;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @author penglibo
 * @date 2021-08-24 15:33:18
 * @since jdk 1.8
 * Listener不能被spring管理,要每次读取excel都要new,然后里面用到spring可以构造方法传进去
 */

@Data
@Slf4j
@EqualsAndHashCode(callSuper = true)
public class ExcelListener<T> extends AnalysisEventListener<T> {

    private ExcelResult<T> result;

    public ExcelListener() {
        this.result = new ExcelResult<>();
    }

    /**
     * 分析第一行时触发调用函数。
     * @param headMap 头map
     * @param context 分析上下文
     */
    @Override
    public void invokeHead(Map<Integer, CellData> headMap, AnalysisContext context) {
        log.info("分析第一行:{}", headMap.toString());
        result.setHeadMap(headMap);
        super.invokeHead(headMap, context);
    }

    /**
     * 这里会一行行的返回头-重写invokeHeadMap方法，获去表头，如果有需要获取第一行表头就重写这个方法，不需要则不需要重写
     * @param headMap 头map
     * @param context 分析上下文
     */
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        log.info("解析到一条头数据：{}, currentRowHolder: {}", headMap.toString(), context.readRowHolder().getRowIndex());
        result.getHeadList().add(headMap);
        super.invokeHeadMap(headMap, context);
    }

    /**
     * 异常时调用,抛出异常则停止读取,如果这里不抛出异常则继续读取下一行
     * @param exception 异常
     * @param context   分析上下文
     * @throws Exception e
     */
    @Override
    public void onException(Exception exception, AnalysisContext context) throws Exception {
        if (exception instanceof ExcelDataConvertException) {
            ExcelDataConvertException e = (ExcelDataConvertException) exception;
            String message = "第" + (e.getRowIndex() + 1) + "行,第" + (e.getColumnIndex() + 1) + "列," + e.getCellData() + "解析异常";
            log.error(message);
            this.result.getMessages().add(message);
        }
        // super.onException(exception, context);
    }

    /**
     * 这个每一条数据解析都会来调用
     * @param data            one row value. Is is same as {@link AnalysisContext#readRowHolder()}
     * @param analysisContext 分析上下文
     */
    @Override
    public void invoke(T data, AnalysisContext analysisContext) {
        this.result.getDataList().add(data);
    }

    /**
     * 所有数据解析完成了 都会来调用
     * @param analysisContext 分析上下文
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("数据解析完成");
    }
}
