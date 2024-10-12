package io.github.cmmplb.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.write.handler.WriteHandler;
import io.github.cmmplb.core.exception.BusinessException;
import io.github.cmmplb.excel.alibaba.demo.util.TestFileUtil;
import io.github.cmmplb.report.excel.beans.ExcelResult;
import io.github.cmmplb.report.excel.utils.ExcelUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
public class ExcelTests {

    // public static void main(String[] args) {
    //     String fileName = TestFileUtil.getPath() + "simpleWrite" + System.currentTimeMillis() + ".xlsx";
    //     // EasyExcel.write(fileName, TestData.class).excelType(ExcelTypeEnum.XLS).sheet("模板").doWrite(TestData::data);
    //
    //
    //     List<DropDown> list = new ArrayList<>();
    //     List<String> brandNameList = Arrays.asList("塑料陈列物料", "广告物料", "铁质陈列物料");
    //
    //     List<String> parentList = Arrays.asList("塑料陈列物料", "广告物料", "铁质陈列物料");
    //     Map<String, List<String>> childrenMap = new HashMap<>();
    //     List<String> childrenList = Arrays.asList("铅笔", "橡皮擦", "玩具");
    //     childrenMap.put("塑料陈列物料", childrenList);
    //     childrenList = Arrays.asList("书包", "风扇", "电脑");
    //     childrenMap.put("广告物料", childrenList);
    //     childrenList = Arrays.asList("拖鞋", "坤", "外套");
    //     childrenMap.put("铁质陈列物料", childrenList);
    //     DropDown dropDown = new DropDown();
    //     dropDown.setDataList(brandNameList);
    //     dropDown.setError(new Error("请从列表中选择一项品牌数据", "数据错误"));
    //     dropDown.setPrompt(new Prompt("请从列表中选择一项品牌数据", "品牌不能为空"));
    //     dropDown.setCascade(true);
    //     dropDown.setParentList(parentList);
    //     dropDown.setChildrenMap(childrenMap);
    //     dropDown.setParentIndex(9);
    //     dropDown.setChildrenIndex(10);
    //     list.add(dropDown);
    //     //
    //     dropDown = new DropDown();
    //     dropDown.setDataList(brandNameList);
    //     dropDown.setError(new Error("请从列表中选择一项品牌数据", "数据错误"));
    //     dropDown.setPrompt(new Prompt("请从列表中选择一项品牌数据", "品牌不能为空"));
    //     dropDown.setCascade(true);
    //     parentList = Arrays.asList("测试1", "测试2", "测试3");
    //     childrenMap = new HashMap<>();
    //     childrenList = Arrays.asList("1", "2", "3");
    //     childrenMap.put("测试1", childrenList);
    //     childrenList = Arrays.asList("4", "5", "6");
    //     childrenMap.put("测试2", childrenList);
    //     childrenList = Arrays.asList("7", "8", "9");
    //     childrenMap.put("测试3", childrenList);
    //     dropDown.setParentList(parentList);
    //     dropDown.setChildrenMap(childrenMap);
    //     dropDown.setParentIndex(11);
    //     dropDown.setChildrenIndex(12);
    //     list.add(dropDown);
    //     EasyExcel.write(fileName, TestData.class).registerWriteHandler(new DropDownSheetWriteHandler(list)).sheet("sheet1").doWrite(TestData::data);
    //
    //
    //     Map<Integer, String> map1 = new HashMap<>();
    //     map1.put(2, "brandNameList1");
    //     map1.put(3, "brandNameList2");
    //     map1.put(4, "brandNameList3");
    //     map1.put(5, "brandNameList4");
    //
    //     // EasyExcel.write(fileName, TestData.class).registerWriteHandler(new CustomCellWriteHandler(map1)).sheet().doWrite(TestData::data);
    //
    //     Map<Integer, DataFormatData> map2 = new HashMap<>();
    //     DataFormatData dataFormatData = new DataFormatData();
    //     dataFormatData.setFormat("#,##0.0000");
    //     map2.put(9, dataFormatData);
    //     dataFormatData = new DataFormatData();
    //     dataFormatData.setFormat("#,##0.00000");
    //     map2.put(10, dataFormatData);
    //     dataFormatData = new DataFormatData();
    //     dataFormatData.setFormat("#,##0.000000");
    //     map2.put(11, dataFormatData);
    //     dataFormatData = new DataFormatData();
    //     dataFormatData.setFormat("#,##0.0000000");
    //     map2.put(12, dataFormatData);
    //     dataFormatData = new DataFormatData();
    //     dataFormatData.setFormat("#,##0.00000000");
    //     map2.put(13, dataFormatData);
    //     // EasyExcel.write(fileName, TestData.class)
    //     //         .registerWriteHandler(new DataFormatSheetWriteHandler(map2))
    //     //         .registerWriteHandler(new DataFormatCellWriteHandler(map2))
    //     //         .sheet().doWrite(TestData::data);
    //
    //     // EasyExcel.write(fileName, TestData.class).registerWriteHandler(new DataFormatCellWriteHandler(map2)).sheet().doWrite(TestData::data);
    //
    //
    //     Map<Integer, List<DataValida>> map3 = new HashMap<>();
    //     List<String> unitNameList = new ArrayList<>();
    //     unitNameList.add("套");
    //     unitNameList.add("套1");
    //     unitNameList.add("套2");
    //
    //     List<DataValida> datalist = new ArrayList<>();
    //     DataValida dataValida = new DataValida();
    //     dataValida.setValidationType(DataValidationConstraint.ValidationType.LIST);
    //     dataValida.setDataList(unitNameList);
    //     dataValida.setError(new Error("提示", "您输入的值未在备选列表中, 请从列表中选择一项单位数据"));
    //     dataValida.setPrompt(new Prompt("请从列表中选择一项单位数据", "单位不能为空"));
    //     datalist.add(dataValida);
    //     map3.put(2, datalist);
    //     map3.put(3, datalist);
    //     map3.put(4, datalist);
    //     map3.put(5, datalist);
    //     // EasyExcel.write(fileName, TestData.class).registerWriteHandler(new DataValidationSheetWriteHandler(map3)).sheet().doWrite(TestData::data);
    //
    // }

    /**
     * 指定单sheet页名称写入-response
     * @param fileName     文件名称
     * @param clazz        写入对象字节码
     * @param writeHandler 自定义writeHandler
     * @param list         写入数据列表
     * @param <T>          写入的文件对象类泛型
     */
    public static <T> void writeSingleExcel(String fileName, Class<T> clazz, WriteHandler writeHandler, List<T> list) {
        try {
            EasyExcel.write(TestFileUtil.getPath(), clazz).registerWriteHandler(writeHandler).sheet().doWrite(list);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    public static void main(String[] args) {
        ExcelResult<Temp> res = ExcelUtil.readSingle("/Users/penglibo/Downloads/需要同步的用户清单v2.xls", Temp.class);
        List<Temp> dataList = res.getDataList();
        String sql = "INSERT INTO `platform_sync_distribute_detail` (\n" +
                "`id`,\n" +
                "`sys_id`,\n" +
                "`uid`,\n" +
                "`status`,\n" +
                "`created_date`,\n" +
                "`updated_date`,\n" +
                "`created_by`,\n" +
                "`updated_by`,\n" +
                "`del_flag`,\n" +
                "`opt_counter`,\n" +
                "`remarks`\n" +
                ")\n" +
                "VALUES  (\n" +
                "REPLACE(UUID(),'-',''),\n" +
                "'永宏BI',\n" +
                "'lvxianglong',\n" +
                "'00',\n" +
                "'2024-03-25 10:20:19',\n" +
                "'2024-03-25 10:20:21',\n" +
                "'1',\n" +
                "'1',\n" +
                "'0',\n" +
                "NULL,\n" +
                "NULL\n" +
                ")";
        for (Temp temp : dataList) {
            sql += ",(\n" +
                    "   REPLACE(UUID(),'-',''),\n" +
                    "   '永宏BI',\n" +
                    "   '"+temp.getLdap()+"',\n" +
                    "   '00',\n" +
                    "   '2024-03-25 10:20:19',\n" +
                    "   '2024-03-25 10:20:21',\n" +
                    "   '1',\n" +
                    "   '1',\n" +
                    "   '0',\n" +
                    "   NULL,\n" +
                    "   NULL\n" +
                    ")";
        }

        log.info("sql：{}", sql);
    }

    @Data
    public static class Temp {

        @ExcelProperty(value = "LDAP账号")
        private String ldap;
    }
}