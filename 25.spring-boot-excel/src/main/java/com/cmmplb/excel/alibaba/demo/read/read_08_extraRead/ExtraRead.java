package com.cmmplb.excel.alibaba.demo.read.read_08_extraRead;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.enums.CellExtraTypeEnum;
import com.cmmplb.core.utils.FileUtil;
import com.cmmplb.excel.alibaba.demo.read.data.DemoExtraData;
import com.cmmplb.excel.alibaba.demo.read.read_01_simpleRead.DemoDataListener;

import java.io.InputStream;

/**
 * @author penglibo
 * @date 2023-08-04 16:05:23
 * @since jdk 1.8
 * 额外信息（批注、超链接、合并单元格信息读取）
 */
public class ExtraRead {

    public static void main(String[] args) {
            InputStream inputStream = FileUtil.getInputStream("read/extra.xlsx");
            EasyExcel.read(inputStream, DemoExtraData.class, new DemoDataListener<DemoExtraData>())
                    // 需要读取批注 默认不读取
                    .extraRead(CellExtraTypeEnum.COMMENT)
                    // 需要读取超链接 默认不读取
                    .extraRead(CellExtraTypeEnum.HYPERLINK)
                    // 需要读取合并单元格信息 默认不读取
                    .extraRead(CellExtraTypeEnum.MERGE)
                    .sheet().doRead();
    }
}