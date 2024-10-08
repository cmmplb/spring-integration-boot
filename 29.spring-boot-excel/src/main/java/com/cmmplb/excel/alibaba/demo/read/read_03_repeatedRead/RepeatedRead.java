package com.cmmplb.excel.alibaba.demo.read.read_03_repeatedRead;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import io.github.cmmplb.core.utils.FileUtil;
import com.cmmplb.excel.alibaba.demo.read.data.DemoData;
import com.cmmplb.excel.alibaba.demo.read.read_01_simpleRead.DemoDataListener;

import java.io.InputStream;

/**
 * @author penglibo
 * @date 2021-05-24 17:34:04
 * @since jdk 1.8
 * 读多个sheet
 */

public class RepeatedRead {

    /**
     * 读多个或者全部sheet,这里注意一个sheet不能读取多次，多次读取需要重新读取文件
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link DemoData}
     * <p>
     * 2. 由于默认一行行的读取excel，所以需要创建excel一行一行的回调监听器，参照{@link DemoDataListener}
     * <p>
     * 3. 直接读即可
     */
    public static void main(String[] args) {
        InputStream inputStream = FileUtil.getInputStream("read/demo.xlsx");
        // 读取全部sheet
        // 这里需要注意 DemoDataListener的doAfterAllAnalysed 会在每个sheet读取完毕后调用一次。然后所有sheet都会往同一个DemoDataListener里面写
        EasyExcel.read(inputStream, DemoData.class, new DemoDataListener<DemoData>()).doReadAll();

        // 读取部分sheet
        ExcelReader excelReader = null;
        try {
            excelReader = EasyExcel.read(inputStream).build();

            // 这里为了简单 所以注册了 同样的head 和Listener 自己使用功能必须不同的Listener
            ReadSheet readSheet1 =
                    EasyExcel.readSheet(0).head(DemoData.class).registerReadListener(new DemoDataListener<DemoData>()).build();
            ReadSheet readSheet2 =
                    EasyExcel.readSheet(1).head(DemoData.class).registerReadListener(new DemoDataListener<DemoData>()).build();
            // 这里注意 一定要把sheet1 sheet2 一起传进去，不然有个问题就是03版的excel 会读取多次，浪费性能
            excelReader.read(readSheet1, readSheet2);
        } finally {
            if (excelReader != null) {
                // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
                excelReader.finish();
            }
        }
    }
}
