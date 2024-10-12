package io.github.cmmplb.excel.alibaba.demo.fill.data;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class FillData {
    private String name;
    private double number;
    private Date date;

    public static List<FillData> data() {
        List<FillData> list = new ArrayList<FillData>();
        for (int i = 0; i < 10; i++) {
            FillData data = new FillData();
            data.setName("字符串" + i);
            data.setDate(new Date());
            data.setNumber(0.56);
            list.add(data);
        }
        return list;
    }
}