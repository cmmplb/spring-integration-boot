package com.cmmplb.excel.alibaba.demo.read.read_11_noModelRead;

import com.alibaba.excel.EasyExcel;
import com.cmmplb.core.utils.FileUtil;

import java.io.InputStream;

/**
 * @author penglibo
 * @date 2023-08-08 11:35:10
 * @since jdk 1.8
 * 不创建对象的读
 */
public class NoModelRead {

    public static void main(String[] args) {
        InputStream inputStream = FileUtil.getInputStream("read/demo.xlsx");
        // 这里只要，然后读取第一个sheet 同步读取会自动finish
        EasyExcel.read(inputStream, new NoModelDataListener()).sheet().doRead();
    }
}
