package com.cmmplb.excel.alibaba.demo.read.read_05_complexHeaderRead;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.cmmplb.core.utils.FileUtil;
import com.cmmplb.excel.alibaba.demo.read.data.DemoData;
import com.cmmplb.excel.alibaba.demo.read.read_01_simpleRead.DemoDataListener;
import com.cmmplb.excel.alibaba.demo.util.TestFileUtil;

import java.io.File;
import java.io.InputStream;

/**
 * @author penglibo
 * @date 2023-08-04 13:53:59
 * @since jdk 1.8
 * 多行头
 */
public class ComplexHeaderRead {

    /**
     * 多行头
     *
     * <p>1. 创建excel对应的实体对象 参照{@link DemoData}
     * <p>2. 由于默认一行行的读取excel, 所以需要创建excel一行一行的回调监听器, 参照{@link DemoDataListener}
     * <p>3. 设置headRowNumber参数, 然后读.  这里要注意headRowNumber如果不指定,  会根据你传入的class的{@link ExcelProperty#value()}里面的表头的数量来决定行数,
     * 如果不传入class则默认为1.当然你指定了headRowNumber不管是否传入class都是以你传入的为准. 
     */
    public static void main(String[] args) {
        InputStream inputStream = FileUtil.getInputStream("read/demo.xlsx");
        // 这里 需要指定读用哪个class去读, 然后读取第一个sheet
        EasyExcel.read(inputStream, DemoData.class, new DemoDataListener<DemoData>()).excelType(ExcelTypeEnum.XLSX).sheet()
                // 这里可以设置1, 因为头就是一行. 如果多行头, 可以设置其他值. 不传入也可以, 因为默认会根据DemoData 来解析, 他没有指定头, 也就是默认1行
                .headRowNumber(1).doRead();
    }
}
