package com.cmmplb.excel.alibaba.demo.export.export_18_variableTitleWrite;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.ListUtils;
import com.cmmplb.excel.alibaba.demo.export.data.ConverterData;
import com.cmmplb.excel.alibaba.demo.util.TestFileUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import static com.cmmplb.excel.alibaba.demo.export.data.ConverterData.data;

/**
 * @author penglibo
 * @date 2023-08-14 09:51:38
 * @since jdk 1.8
 * 可变标题处理(包括标题国际化等)
 */

@Slf4j
public class VariableTitleWrite {

    /**
     * 可变标题处理(包括标题国际化等)
     * <p>
     * 简单的说用List<List<String>>的标题 但是还支持注解
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link ConverterData}
     * <p>
     * 2. 直接写即可
     */
    public static void main(String[] args) {
        // 写法1
        String fileName = TestFileUtil.getPath() + "variableTitleWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileName, ConverterData.class).head(variableTitleHead()).sheet("模板").doWrite(data());
    }

    private static List<List<String>> variableTitleHead() {
        List<List<String>> list = ListUtils.newArrayList();
        List<String> head0 = ListUtils.newArrayList();
        head0.add("string" + System.currentTimeMillis());
        List<String> head1 = ListUtils.newArrayList();
        head1.add("number" + System.currentTimeMillis());
        List<String> head2 = ListUtils.newArrayList();
        head2.add("date" + System.currentTimeMillis());
        list.add(head0);
        list.add(head1);
        list.add(head2);
        return list;
    }
}
