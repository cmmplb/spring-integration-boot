package io.github.cmmplb.excel.alibaba.demo.read.read_04_convertToJavaData;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.fastjson.JSON;
import io.github.cmmplb.excel.alibaba.demo.read.dao.DemoDAO;
import io.github.cmmplb.excel.alibaba.demo.read.dao.impl.DemoDaoImpl;
import io.github.cmmplb.excel.alibaba.demo.read.data.ConverterData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author penglibo
 * @date 2021-05-24 17:00:34
 * @since jdk 1.8
 */

// 有个很重要的点 DemoDataListener 不能被spring管理, 要每次读取excel都要new,然后里面用到spring可以构造方法传进去
public class ConverterDataListener extends AnalysisEventListener<ConverterData> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConverterDataListener.class);
    /**
     * 每隔5条存储数据库, 实际使用中可以3000条, 然后清理list , 方便内存回收
     */
    private static final int BATCH_COUNT = 5;
    List<ConverterData> list = new ArrayList<ConverterData>();
    /**
     * 假设这个是一个DAO, 当然有业务逻辑这个也可以是一个service. 当然如果不用存储这个对象没用. 
     */
    private DemoDAO demoDAO;

    public ConverterDataListener() {
        // 这里是demo, 所以随便new一个. 实际使用如果到了spring,请使用下面的有参构造函数
        demoDAO = new DemoDaoImpl();
    }
    /**
     * 如果使用了spring,请使用这个构造方法. 每次创建Listener的时候需要把spring管理的类传进来
     *
     * @param demoDAO
     */
    public ConverterDataListener(DemoDAO demoDAO) {
        this.demoDAO = demoDAO;
    }

    /**
     * 这个每一条数据解析都会来调用
     *
     * @param data one row value. Is is same as {@link AnalysisContext#readRowHolder()}
     * @param context
     */
    @Override
    public void invoke(ConverterData data, AnalysisContext context) {
        LOGGER.info("解析到一条数据:{}", JSON.toJSONString(data));
        list.add(data);
        // 达到BATCH_COUNT了, 需要去存储一次数据库, 防止数据几万条数据在内存, 容易OOM
        if (list.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            list.clear();
        }
    }
    /**
     * 所有数据解析完成了 都会来调用
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要保存数据, 确保最后遗留的数据也存储到数据库
        saveData();
        LOGGER.info("所有数据解析完成！");
    }
    /**
     * 加上存储数据库
     */
    private void saveData() {
        LOGGER.info("{}条数据, 开始存储数据库！", list.size());

        LOGGER.info("存储数据库成功！");
    }

    /**
     * 这里会一行行的返回头
     *
     * @param headMap
     * @param context
     */
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        LOGGER.info("解析到一条头数据:{}", JSON.toJSONString(headMap));
    }

    /**
     * 在转换异常 获取其他异常下会调用本接口. 抛出异常则停止读取. 如果这里不抛出异常则 继续读取下一行. 
     *
     * @param exception
     * @param context
     * @throws Exception
     */
    @Override
    public void onException(Exception exception, AnalysisContext context) {
        LOGGER.error("解析失败, 但是继续解析下一行:{}", exception.getMessage());
        // 如果是某一个单元格的转换异常 能获取到具体行号
        // 如果要获取头的信息 配合invokeHeadMap使用
        if (exception instanceof ExcelDataConvertException) {
            ExcelDataConvertException excelDataConvertException = (ExcelDataConvertException)exception;
            LOGGER.error("第{}行, 第{}列解析异常", excelDataConvertException.getRowIndex(),
                    excelDataConvertException.getColumnIndex());
        }
    }
}
