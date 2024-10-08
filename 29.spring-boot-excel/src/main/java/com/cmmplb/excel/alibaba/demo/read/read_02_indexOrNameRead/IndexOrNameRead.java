package com.cmmplb.excel.alibaba.demo.read.read_02_indexOrNameRead;

import com.alibaba.excel.EasyExcel;
import io.github.cmmplb.core.utils.FileUtil;
import com.cmmplb.excel.alibaba.demo.read.data.IndexOrNameData;

import java.io.InputStream;

/**
 * @author penglibo
 * @date 2021-05-24 17:08:17
 * @since jdk 1.8
 * 指定列的下标或者列名
 */

public class IndexOrNameRead {

    /**
     * 指定列的下标或者列名
     *
     * <p>1. 创建excel对应的实体对象,并使用{@link @ExcelProperty}注解. 参照{@link IndexOrNameData}
     * <p>2. 由于默认一行行的读取excel，所以需要创建excel一行一行的回调监听器，参照{@link IndexOrNameDataListener}
     * <p>3. 直接读即可
     */
    public static void main(String[] args) {
        InputStream inputStream = FileUtil.getInputStream("read/demo.xlsx");
        // 这里默认读取第一个sheet
        EasyExcel.read(inputStream, IndexOrNameData.class, new IndexOrNameDataListener()).sheet().doRead();

    }
}
