package io.github.cmmplb.excel.alibaba.demo.fill.fill_01_simpleFill;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.MapUtils;
import io.github.cmmplb.core.utils.FileUtil;
import io.github.cmmplb.excel.alibaba.demo.fill.data.FillData;
import io.github.cmmplb.excel.alibaba.demo.util.TestFileUtil;

import java.io.InputStream;
import java.util.Map;

/**
 * @author penglibo
 * @date 2023-08-14 09:57:23
 * @since jdk 1.8
 * 最简单的填充
 */
public class SimpleFill {

    public static void main(String[] args) {
        // 模板注意 用{} 来表示你要用的变量 如果本来就有"{","}" 特殊字符 用"\{","\}"代替
        InputStream inputStream = FileUtil.getInputStream("fill/simple.xlsx");

        // 方案1 根据对象填充
        String fileName = TestFileUtil.getPath() + "simpleFill" + System.currentTimeMillis() + ".xlsx";
        // 这里 会填充到第一个sheet,  然后文件流会自动关闭
        FillData fillData = new FillData();
        fillData.setName("张三");
        fillData.setNumber(5.2);
        EasyExcel.write(fileName).withTemplate(inputStream).sheet().doFill(fillData);

        // 方案2 根据Map填充
        fileName = TestFileUtil.getPath() + "simpleFill" + System.currentTimeMillis() + ".xlsx";
        inputStream = FileUtil.getInputStream("fill/simple.xlsx");
        // 这里 会填充到第一个sheet,  然后文件流会自动关闭
        Map<String, Object> map = MapUtils.newHashMap();
        map.put("name", "张三");
        map.put("number", 5.2);
        EasyExcel.write(fileName).withTemplate(inputStream).sheet().doFill(map);
    }
}
