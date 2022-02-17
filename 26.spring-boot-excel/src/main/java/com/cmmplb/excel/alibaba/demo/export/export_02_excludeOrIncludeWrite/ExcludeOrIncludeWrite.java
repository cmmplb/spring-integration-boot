package com.cmmplb.excel.alibaba.demo.export.export_02_excludeOrIncludeWrite;

import com.alibaba.excel.EasyExcel;
import com.cmmplb.excel.alibaba.demo.export.data.DemoData;
import com.cmmplb.excel.alibaba.demo.util.TestFileUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * @author penglibo
 * @date 2021-05-22 17:57:07
 * @since jdk 1.8
 * 学习根据参数只导出指定列
 */

public class ExcludeOrIncludeWrite {

    /**
     * 根据参数只导出指定列
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link DemoData}
     * <p>
     * 2. 根据自己或者排除自己需要的列
     * <p>
     * 3. 直接写即可
     *
     * @since 2.1.1
     */
    public static void main(String[] args) {

        // https://www.yuque.com/easyexcel/doc/write#34577a27

        // 根据用户传入字段 假设我们--要忽略 date
        String fileName = TestFileUtil.getPath() + "excludeOrIncludeWrite" + System.currentTimeMillis() + ".xlsx";
        Set<String> excludeColumnFiledNames = new HashSet<String>();
        excludeColumnFiledNames.add("date");
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileName, DemoData.class).excludeColumnFiledNames(excludeColumnFiledNames).sheet("模板")
                .doWrite(DemoData.data());



        // 根据用户传入字段 假设我们--只要导出 date
        fileName = TestFileUtil.getPath() + "excludeOrIncludeWrite" + System.currentTimeMillis() + ".xlsx";
        Set<String> includeColumnFiledNames = new HashSet<String>();
        includeColumnFiledNames.add("date");
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileName, DemoData.class).includeColumnFiledNames(includeColumnFiledNames).sheet("模板")
                .doWrite(DemoData.data());
    }
}
