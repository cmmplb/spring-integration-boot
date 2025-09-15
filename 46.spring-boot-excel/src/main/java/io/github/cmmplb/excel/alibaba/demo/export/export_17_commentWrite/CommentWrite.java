package io.github.cmmplb.excel.alibaba.demo.export.export_17_commentWrite;

import com.alibaba.excel.EasyExcel;
import io.github.cmmplb.excel.alibaba.demo.export.data.DemoData;
import io.github.cmmplb.excel.alibaba.demo.util.TestFileUtil;

/**
 * @author penglibo
 * @date 2021-05-24 16:22:28
 * @since jdk 1.8
 * 插入批注
 */

public class CommentWrite {

    /**
     * 插入批注
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link DemoData}
     * <p>
     * 2. 注册拦截器 {@link CommentWriteHandler}
     * <p>
     * 2. 直接写即可
     */
    public static void main(String[] args) {
        String fileName = TestFileUtil.getPath() + "commentWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写, 然后写到第一个sheet, 名字为模板 然后文件流会自动关闭
        // 这里要注意inMemory 要设置为true, 才能支持批注. 目前没有好的办法解决 不在内存处理批注. 这个需要自己选择. 
        EasyExcel.write(fileName, DemoData.class).inMemory(Boolean.TRUE).registerWriteHandler(new CommentWriteHandler())
                .sheet("模板").doWrite(DemoData.data());
    }
}
