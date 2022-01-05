package com.cmmplb.excel.alibaba.demo.other.name;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.fastjson.JSON;
import com.cmmplb.core.utils.RandomUtil;
import com.cmmplb.excel.alibaba.demo.read.dao.DemoDAO;
import com.cmmplb.excel.alibaba.demo.read.dao.impl.DemoDaoImpl;
import com.cmmplb.excel.alibaba.demo.util.TestFileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author penglibo
 * @date 2021-05-24 17:00:34
 * @since jdk 1.8
 */

// 有个很重要的点 DemoDataListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
public class NameDataListener extends AnalysisEventListener<NameData> {
    private static final Logger LOGGER = LoggerFactory.getLogger(NameDataListener.class);
    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 3000;
    List<NameData> list = new ArrayList<NameData>();
    /**
     * 假设这个是一个DAO，当然有业务逻辑这个也可以是一个service。当然如果不用存储这个对象没用。
     */
    private DemoDAO demoDAO;

    public NameDataListener() {
        // 这里是demo，所以随便new一个。实际使用如果到了spring,请使用下面的有参构造函数
        demoDAO = new DemoDaoImpl();
    }

    /**
     * 如果使用了spring,请使用这个构造方法。每次创建Listener的时候需要把spring管理的类传进来
     * @param demoDAO
     */
    public NameDataListener(DemoDAO demoDAO) {
        this.demoDAO = demoDAO;
    }

    /**
     * 这个每一条数据解析都会来调用
     * @param data    one row value. Is is same as {@link AnalysisContext#readRowHolder()}
     * @param context
     */
    @Override
    public void invoke(NameData data, AnalysisContext context) {
        LOGGER.info("解析到一条数据:{}", JSON.toJSONString(data));
        String name = data.getName();
        Set<String> pinyinBai = new HashSet<>();// RandomUtil.getPinyinBai();
        for (String s : pinyinBai) {
            if (name.length() > s.length()) {
                String substring = name.substring((name.length() - s.length()));
                if (substring.equals(s)) {
                    list.add(new NameData(name.replace(s, "")));
                    return;
                }
            }
        }
        list.add(data);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
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
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
        LOGGER.info("所有数据解析完成！");
    }

    /**
     * 加上存储数据库
     */
    private void saveData() {
        LOGGER.info("{}条数据，开始存储数据库！", list.size());

        LOGGER.info("存储数据库成功！");
        System.out.println(list);

        String fileName = TestFileUtil.getPath() + "name" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写
        ExcelWriter excelWriter = null;
        try {
            excelWriter = EasyExcel.write(fileName, NameData.class).build();
            WriteSheet writeSheet = EasyExcel.writerSheet("模板").build();
            excelWriter.write(list, writeSheet);
        } finally {
            // 千万别忘记finish 会帮忙关闭流
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }
    }
}
