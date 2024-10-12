package io.github.cmmplb.excel.alibaba.demo.export.export_10_annotationStyleWrite;

import com.alibaba.excel.EasyExcel;
import io.github.cmmplb.excel.alibaba.demo.export.data.DemoStyleData;
import io.github.cmmplb.excel.alibaba.demo.util.TestFileUtil;

/**
 * @author penglibo
 * @date 2021-05-24 15:12:04
 * @since jdk 1.8
 * 注解形式自定义样式
 */

public class AnnotationStyleWrite {

    /**
     * 注解形式自定义样式
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link DemoStyleData}
     * <p>
     * 3. 直接写即可
     * @since 2.2.0-beta1
     */
    public static void main(String[] args) {
        String fileName = TestFileUtil.getPath() + "annotationStyleWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写, 然后写到第一个sheet, 名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileName, DemoStyleData.class).sheet("模板").doWrite(DemoStyleData.data());
    }
}
