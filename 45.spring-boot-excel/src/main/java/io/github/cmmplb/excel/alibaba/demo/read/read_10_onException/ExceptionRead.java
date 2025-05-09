package io.github.cmmplb.excel.alibaba.demo.read.read_10_onException;

import com.alibaba.excel.EasyExcel;
import io.github.cmmplb.core.utils.FileUtil;
import io.github.cmmplb.excel.alibaba.demo.read.data.ExceptionDemoData;

import java.io.InputStream;

/**
 * @author penglibo
 * @date 2023-08-08 11:32:21
 * @since jdk 1.8
 * 数据转换等异常处理
 */
public class ExceptionRead {

    /**
     * 在转换异常 获取其他异常下会调用本接口. 抛出异常则停止读取. 如果这里不抛出异常则 继续读取下一行. 
     */
    public static void main(String[] args) {
        InputStream inputStream = FileUtil.getInputStream("read/demo.xlsx");
        // 这里 需要指定读用哪个class去读, 然后读取第一个sheet
        EasyExcel.read(inputStream, ExceptionDemoData.class, new DemoExceptionListener()).sheet().doRead();
    }
}
