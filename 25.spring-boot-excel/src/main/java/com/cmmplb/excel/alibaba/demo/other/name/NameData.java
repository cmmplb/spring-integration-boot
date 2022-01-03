package com.cmmplb.excel.alibaba.demo.other.name;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NameData {

    @ExcelProperty("英文ID(去掉姓)")
    private String name;
}