package io.github.cmmplb.excel.alibaba.demo.read.read_09_cellDataRead;

import com.alibaba.excel.EasyExcel;
import io.github.cmmplb.core.utils.FileUtil;
import io.github.cmmplb.excel.alibaba.demo.read.data.CellDataReadDemoData;
import io.github.cmmplb.excel.alibaba.demo.read.read_06_headerRead.DemoHeadDataListener;

import java.io.InputStream;

/**
 * @author penglibo
 * @date 2023-08-08 11:23:12
 * @since jdk 1.8
 * 读取公式和单元格类型
 */
public class CellDataRead {

    /**
     * 读取公式和单元格类型
     * 1. 创建excel对应的实体对象 参照{@link CellDataReadDemoData}
     * 2. 由于默认一行行的读取excel, 所以需要创建excel一行一行的回调监听器, 参照{@link DemoHeadDataListener}
     * 3. 直接读即可
     * @since 2.2.0-beat1
     */
    public static void main(String[] args) {
        InputStream inputStream = FileUtil.getInputStream("read/cellDataDemo.xlsx");
        // 这里 需要指定读用哪个class去读, 然后读取第一个sheet
        EasyExcel.read(inputStream, CellDataReadDemoData.class, new CellDataDemoHeadDataListener()).sheet().doRead();
    }
}
