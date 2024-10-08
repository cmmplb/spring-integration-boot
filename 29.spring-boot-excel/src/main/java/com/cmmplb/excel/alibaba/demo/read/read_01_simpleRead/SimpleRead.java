package com.cmmplb.excel.alibaba.demo.read.read_01_simpleRead;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.enums.CellExtraTypeEnum;
import com.alibaba.excel.read.listener.PageReadListener;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson.JSON;
import io.github.cmmplb.core.utils.FileUtil;
import com.cmmplb.excel.alibaba.demo.read.data.DemoData;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author penglibo
 * @date 2021-05-24 17:03:35
 * @since jdk 1.8
 * 最简单的读
 */

@Slf4j
public class SimpleRead {

    /**
     * 最简单的读
     * <p>1. 创建excel对应的实体对象 参照{@link DemoData}
     * <p>2. 由于默认一行行的读取excel，所以需要创建excel一行一行的回调监听器，参照{@link DemoDataListener}
     * <p>3. 直接读即可
     */
    public static void main(String[] args) throws Exception {
        // 写法1
        // method1();
        // 写法2
        // method2();
        // 写法3
        method3();
        // 写法4
        // method4();
    }

    private static void method1() throws IOException {
        // 写法1：JDK8+ ,不用额外写一个DemoDataListener
        // since: 3.0.0-beta1
        InputStream inputStream = FileUtil.getInputStream("read/demo.xlsx");
        // 这里默认每次会读取100条数据 然后返回过来 直接调用使用数据就行
        // 具体需要返回多少行可以在`PageReadListener`的构造函数设置
        // String fileName = "/Users/penglibo/Downloads/demo.xlsx";
        EasyExcel.read(inputStream, DemoData.class, new PageReadListener<DemoData>(dataList -> {
            for (DemoData demoData : dataList) {
                log.info("读取到一条数据{}", JSON.toJSONString(demoData));
            }
        })).sheet().doRead();
    }

    private static void method2() {
        // 写法2：
        // 匿名内部类 不用额外写一个DemoDataListener
        InputStream inputStream = FileUtil.getInputStream("read/demo.xlsx");
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(inputStream, DemoData.class, new ReadListener<DemoData>() {
            /**
             * 单次缓存的数据量
             */
            public static final int BATCH_COUNT = 100;
            /**
             *临时存储
             */
            private List<DemoData> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

            @Override
            public void invoke(DemoData data, AnalysisContext context) {
                cachedDataList.add(data);
                if (cachedDataList.size() >= BATCH_COUNT) {
                    saveData();
                    // 存储完成清理 list
                    cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
                }
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
                saveData();
            }

            /**
             * 加上存储数据库
             */
            private void saveData() {
                log.info("{}条数据，开始存储数据库！", cachedDataList.size());
                log.info("存储数据库成功！");
            }
        }).sheet().doRead();
    }

    private static void method3() {
        // 有个很重要的点 DemoDataListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
        // 写法3：
        InputStream inputStream = FileUtil.getInputStream("read/demo.xlsx");
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(inputStream, DemoData.class, new DemoDataListener<DemoData>()).sheet().doRead();
    }

    private static void method4() {
        // 写法4：
        InputStream inputStream = FileUtil.getInputStream("read/demo.xlsx");
        ExcelReader excelReader = null;
        try {
            excelReader = EasyExcel.read(inputStream, DemoData.class, new DemoDataListener<DemoData>()).build();
            ReadSheet readSheet = EasyExcel.readSheet(0).build();
            excelReader.read(readSheet);
        } finally {
            if (excelReader != null) {
                // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
                excelReader.finish();
            }
        }
    }
}
