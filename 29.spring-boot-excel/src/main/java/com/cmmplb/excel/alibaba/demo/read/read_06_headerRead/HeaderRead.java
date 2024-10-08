package com.cmmplb.excel.alibaba.demo.read.read_06_headerRead;

import com.alibaba.excel.EasyExcel;
import io.github.cmmplb.core.utils.FileUtil;
import com.cmmplb.excel.alibaba.demo.read.data.DemoData;

import java.io.InputStream;

/**
 * @author penglibo
 * @date 2023-08-08 11:29:07
 * @since jdk 1.8
 * 读取表头数据
 */
public class HeaderRead {

    public static void main(String[] args) {
        InputStream inputStream = FileUtil.getInputStream("read/demo.xlsx");
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet
        EasyExcel.read(inputStream, DemoData.class, new DemoHeadDataListener()).sheet().doRead();
    }
}
