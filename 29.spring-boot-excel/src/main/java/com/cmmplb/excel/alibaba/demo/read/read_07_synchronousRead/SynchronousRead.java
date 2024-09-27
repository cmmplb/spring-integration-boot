package com.cmmplb.excel.alibaba.demo.read.read_07_synchronousRead;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson.JSON;
import com.cmmplb.core.utils.FileUtil;
import com.cmmplb.excel.alibaba.demo.read.data.DemoData;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * @author penglibo
 * @date 2023-08-04 15:57:14
 * @since jdk 1.8
 * 同步的返回
 */

@Slf4j
public class SynchronousRead {

    /**
     * 同步的返回, 不推荐使用, 如果数据量大会把数据放到内存里面
     */
    public static void main(String[] args) {
        InputStream inputStream = FileUtil.getInputStream("read/demo.xlsx");
        // 这里 需要指定读用哪个class去读, 然后读取第一个sheet 同步读取会自动finish
        List<DemoData> list = EasyExcel.read(inputStream).head(DemoData.class).sheet().doReadSync();
        for (DemoData data : list) {
            log.info("读取到数据:{}", JSON.toJSONString(data));
        }
        inputStream = FileUtil.getInputStream("read/demo.xlsx");
        // 这里 也可以不指定class, 返回一个list, 然后读取第一个sheet 同步读取会自动finish
        List<Map<Integer, String>> listMap = EasyExcel.read(inputStream).excelType(ExcelTypeEnum.XLSX).sheet().doReadSync();
        for (Map<Integer, String> data : listMap) {
            // 返回每条数据的键值对 表示所在的列 和所在列的值
            log.info("读取到数据:{}", JSON.toJSONString(data));
        }
    }
}
