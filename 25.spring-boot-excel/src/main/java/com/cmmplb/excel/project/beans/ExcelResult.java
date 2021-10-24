package com.cmmplb.excel.project.beans;

import com.alibaba.excel.metadata.CellData;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author penglibo
 * @date 2021-08-26 11:17:34
 * @since jdk 1.8
 */

@Data
@SuppressWarnings("rawtypes")
public class ExcelResult<T> {

    /**
     * 第一行表头数据-(key-表格索引-0开始)
     */
    private Map<Integer, CellData> headMap;

    /**
     * 解析到的表头数据-(key-表格索引-value表头名称)
     */
    private List<Map<Integer, String>> headList;

    /**
     * 解析到的数据列表
     */
    private List<T> dataList;

    /**
     * 解析异常行列信息列表
     */
    private List<String> messages;

    public ExcelResult() {
        this.headMap = new HashMap<>();
        this.headList = new ArrayList<>();
        this.dataList = new ArrayList<>();
        this.messages = new ArrayList<>();
    }
}
