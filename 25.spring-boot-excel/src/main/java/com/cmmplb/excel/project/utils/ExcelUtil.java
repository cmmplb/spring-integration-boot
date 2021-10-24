package com.cmmplb.excel.project.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.cmmplb.core.constants.StringConstants;
import com.cmmplb.core.exception.ExcelException;
import com.cmmplb.excel.project.beans.ExcelResult;
import com.cmmplb.excel.project.listen.ExcelListener;
import com.cmmplb.web.utils.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author penglibo
 * @date 2021-08-24 14:34:21
 * @since jdk 1.8
 * excel导入导出工具类
 */

@Slf4j
@Component
public class ExcelUtil<T> {


    // ------------------------------------------读取-------------------------------------------------------------------

    // 打印文件信息
    private void printFileInfo(MultipartFile file) {
        log.info("收到文件，文件大小为:{}", file.getSize() / 1000 + "kb");
    }

    /**
     * 读取单个sheet页
     */
    public ExcelResult<T> readSingleExcel(MultipartFile file, Class<T> clazz) throws Exception {
        printFileInfo(file);
        ExcelListener<T> listener = new ExcelListener<>();
        EasyExcel.read(file.getInputStream(), clazz, listener).sheet().doRead();
        return listener.getResult();
    }

    /**
     * 读取指定行开始
     */
    public ExcelResult<T> readHeadRowNumberExcel(MultipartFile file, Class<T> clazz, Integer headRowNumber) throws Exception {
        printFileInfo(file);
        ExcelListener<T> listener = new ExcelListener<>();
        EasyExcel.read(file.getInputStream(), clazz, listener).sheet() // .doRead();
                // 这里可以设置1,因为头就是一行.如果多行头,可以设置其他值。不传入也可以，因为默认会根据 clazz 来解析,他没有指定头,也就是默认1行
                .headRowNumber(headRowNumber).doRead();
        return listener.getResult();
    }

    /**
     * 读取指定sheet页
     */
    public ExcelResult<T> readAssignExcel(MultipartFile file, Class<T> clazz, Integer sheetNo) throws Exception {
        printFileInfo(file);
        ExcelListener<T> listener = new ExcelListener<>();
        EasyExcel.read(file.getInputStream(), clazz, listener).sheet(sheetNo).doRead();
        return listener.getResult();
    }

    /**
     * 读取指定多sheet页
     */
    public ExcelResult<T> readAssignManyExcel(MultipartFile file, Class<T> clazz) throws Exception {
        //初始化返回结果
        printFileInfo(file);
        ExcelListener<T> listener = new ExcelListener<>();

        //-使用同一的listener-第一种方式
        // ExcelReader excelReader = EasyExcel.read(file.getInputStream(), listener).build();
        // ReadSheet readSheet1 = EasyExcel.readSheet(0).head(clazz).build();
        // ReadSheet readSheet2 = EasyExcel.readSheet(1).head(clazz).build();

        //-使用同一的listener-第二种方式
        //ExcelReader excelReader = EasyExcel.read(file.getInputStream()).build();
        //List<ReadSheet> readSheetList = new ArrayList<>();
        //for (Integer sheetNo : sheetList) {
        //    readSheetList.add(EasyExcel.readSheet(sheetNo).head(clazz).registerReadListener(listener).build());
        //}
        //excelReader.read(readSheetList);  // 这里注意把sheet一起传进去,不然有个问题就是03版的excel会读取多次,浪费性能

        //-使用功能不同的Listener

        ExcelReader excelReader = EasyExcel.read(file.getInputStream()).build();
        // todo:这个整合实际项目，不同sheet页使用不同listener处理
        ReadSheet readSheet1 = EasyExcel.readSheet(0).head(clazz).registerReadListener(listener).build();
        ReadSheet readSheet2 = EasyExcel.readSheet(1).head(clazz).registerReadListener(listener).build();
        excelReader.read(readSheet1, readSheet2);  // 这里注意把sheet一起传进去,不然有个问题就是03版的excel会读取多次,浪费性能

        // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
        excelReader.finish();
        return listener.getResult();
    }

    /**
     * 读取全部sheet页-一般sheet都是不同的内容，这个感觉没什么用
     */
    public ExcelResult<T> readAllExcel(MultipartFile file, Class<T> clazz) throws Exception {
        //初始化返回结果
        printFileInfo(file);
        ExcelListener<T> listener = new ExcelListener<>();
        // 这个读取单个sheet页
        EasyExcel.read(file.getInputStream(), clazz, listener).doReadAll();
        return listener.getResult();
    }

    // ------------------------------------------写入-------------------------------------------------------------------


    /**
     * 单sheet页导出
     * @param fileName  文件名称
     * @param sheetName sheet页名称
     * @param t         导出对象
     * @param list      导出数据列表
     */
    public void writeSingleExcel(String fileName, String sheetName, T t, List<T> list) {
        HttpServletResponse response = ServletUtils.getResponse();
        response.setContentType(StringConstants.EXCEL_CONTENT_TYPE);
        response.setCharacterEncoding(StringConstants.UTF8);
        try {
            fileName = URLEncoder.encode(fileName, StringConstants.UTF8).replaceAll(StringConstants.EXCEL_ATTACHMENT, StringConstants.BLANK);
            response.setHeader(StringConstants.CONTENT_DISPOSITION, StringConstants.EXCEL_ATTACHMENT + fileName + StringConstants.XLSX);
            EasyExcel.write(response.getOutputStream(), t.getClass()).sheet(sheetName).doWrite(list);
        } catch (Exception e) {
            throw new ExcelException(e);
        }
    }

    public void writeManyExcel(String fileName, String sheetName, List<Integer> sheetNos, T t, List<T> list) {
        HttpServletResponse response = ServletUtils.getResponse();
        response.setContentType(StringConstants.EXCEL_CONTENT_TYPE);
        response.setCharacterEncoding(StringConstants.UTF8);
        ExcelWriter excelWriter = null;
        // 写到不同的sheet-同一个对象
        try {
            excelWriter = EasyExcel.write(response.getOutputStream(), t.getClass()).build();
            for (int i = 0; i < sheetNos.size(); i++) {
                // 每次都要创建writeSheet 这里注意必须指定sheetNo 而且sheetName必须不一样
                WriteSheet writeSheet = EasyExcel.writerSheet(i, sheetName + sheetNos.get(i)).build();
                // 分页去数据库查询数据 这里可以去数据库查询每一页的数据
                excelWriter.write(list, writeSheet);
            }

        } catch (Exception e) {
            throw new ExcelException(e);
        } finally {
            // 千万别忘记finish 会帮忙关闭流
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }

        // 写到不同的sheet 不同的对象
        try {
            excelWriter = EasyExcel.write(fileName).build();
            for (int i = 0; i < sheetNos.size(); i++) {
                // 每次都要创建writeSheet 这里注意必须指定sheetNo 而且sheetName必须不一样。这里注意DemoData.class 可以每次都变，我这里为了方便 所以用的同一个class 实际上可以一直变
                WriteSheet writeSheet = EasyExcel.writerSheet(i, sheetName + sheetNos.get(i)).head(t.getClass()).build();
                excelWriter.write(list, writeSheet);
            }
        } catch (Exception e) {
            throw new ExcelException(e);
        } finally {
            // 千万别忘记finish 会帮忙关闭流
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }
    }

    /**
     * 重复多次写入
     */
    public void test(String fileName, String sheetName, T t, List<T> list) {
        // 重复多次写入(写到单个或者多个Sheet)
        HttpServletResponse response = ServletUtils.getResponse();
        response.setContentType(StringConstants.EXCEL_CONTENT_TYPE);
        response.setCharacterEncoding(StringConstants.UTF8);
        // 方法1 如果写到同一个sheet
        ExcelWriter excelWriter = null;
        try {
            fileName = URLEncoder.encode(fileName, StringConstants.UTF8).replaceAll(StringConstants.EXCEL_ATTACHMENT, StringConstants.BLANK);
            response.setHeader(StringConstants.CONTENT_DISPOSITION, StringConstants.EXCEL_ATTACHMENT + fileName + StringConstants.XLSX);
            excelWriter = EasyExcel.write(response.getOutputStream(), t.getClass()).build();
            // 这里注意 如果同一个sheet只要创建一次
            WriteSheet writeSheet = EasyExcel.writerSheet(sheetName).build();
            for (int i = 0; i < 5; i++) {
                // 分页去数据库查询数据 这里可以去数据库查询每一页的数据
                excelWriter.write(list, writeSheet);
            }
        } catch (Exception e) {
            throw new ExcelException(e);
        } finally {
            // 千万别忘记finish 会帮忙关闭流
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }

        // 方法2 如果写到不同的sheet 同一个对象
        try {
            excelWriter = EasyExcel.write(response.getOutputStream(), t.getClass()).build();
            for (int i = 0; i < 5; i++) {
                // 每次都要创建writeSheet 这里注意必须指定sheetNo 而且sheetName必须不一样
                WriteSheet writeSheet = EasyExcel.writerSheet(i, sheetName + i).build();
                // 分页去数据库查询数据 这里可以去数据库查询每一页的数据
                excelWriter.write(list, writeSheet);
            }
        } catch (Exception e) {
            throw new ExcelException(e);
        } finally {
            // 千万别忘记finish 会帮忙关闭流
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }

        // 方法3 如果写到不同的sheet 不同的对象
        try {
            excelWriter = EasyExcel.write(fileName).build();
            for (int i = 0; i < 5; i++) {
                // 每次都要创建writeSheet 这里注意必须指定sheetNo 而且sheetName必须不一样。这里注意DemoData.class 可以每次都变，我这里为了方便 所以用的同一个class 实际上可以一直变
                WriteSheet writeSheet = EasyExcel.writerSheet(i, sheetName + i).head(t.getClass()).build();
                excelWriter.write(list, writeSheet);
            }
        } catch (Exception e) {
            throw new ExcelException(e);
        } finally {
            // 千万别忘记finish 会帮忙关闭流
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }
    }
}
