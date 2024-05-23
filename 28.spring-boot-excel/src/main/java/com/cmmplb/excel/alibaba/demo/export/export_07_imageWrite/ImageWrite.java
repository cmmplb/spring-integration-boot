package com.cmmplb.excel.alibaba.demo.export.export_07_imageWrite;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.FileUtils;
import com.cmmplb.excel.alibaba.demo.export.data.ImageData;
import com.cmmplb.excel.alibaba.demo.util.TestFileUtil;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author penglibo
 * @date 2021-05-24 14:46:56
 * @since jdk 1.8
 */

public class ImageWrite {

    /**
     * 图片导出
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link ImageData}
     * <p>
     * 2. 直接写即可
     */
    public static void main(String[] args) throws Exception {
        String fileName = TestFileUtil.getPath() + "imageWrite" + System.currentTimeMillis() + ".xlsx";
        // 如果使用流 记得关闭
        InputStream inputStream = null;
        try {
            List<ImageData> list = new ArrayList<ImageData>();
            ImageData imageData = new ImageData();
            list.add(imageData);
            // 放入五种类型的图片 实际使用只要选一种即可
            imageData.setByteArray(FileUtils.readFileToByteArray(new File("src/main/resources/img.jpg")));
            imageData.setFile(new File("src/main/resources/img.jpg"));
            imageData.setString("src/main/resources/img.jpg");
            inputStream = FileUtils.openInputStream(new File("src/main/resources/img.jpg"));
            imageData.setInputStream(inputStream);
            imageData.setUrl(new URL(
                    "https://raw.githubusercontent.com/alibaba/easyexcel/master/src/test/resources/converter/img.jpg"));
            EasyExcel.write(fileName, ImageData.class).sheet().doWrite(list);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }
}
