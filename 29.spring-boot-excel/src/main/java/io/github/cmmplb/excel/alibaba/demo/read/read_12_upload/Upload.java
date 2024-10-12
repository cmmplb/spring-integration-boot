package io.github.cmmplb.excel.alibaba.demo.read.read_12_upload;

import com.alibaba.excel.EasyExcel;
import io.github.cmmplb.excel.alibaba.demo.read.data.UploadData;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author penglibo
 * @date 2023-08-08 11:42:32
 * @since jdk 1.8
 * web中的读
 */
public class Upload {

    private UploadDAO uploadDAO;

    /**
     * 文件上传
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link UploadData}
     * <p>
     * 2. 由于默认一行行的读取excel, 所以需要创建excel一行一行的回调监听器, 参照{@link UploadDataListener}
     * <p>
     * 3. 直接读即可
     */
    @PostMapping("upload")
    @ResponseBody
    public String upload(MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), UploadData.class, new UploadDataListener(uploadDAO)).sheet().doRead();
        return "success";
    }
}
