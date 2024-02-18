package com.cmmplb.excel.handler.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.poi.ss.usermodel.DataValidation;

@Data
@AllArgsConstructor
public class Error {

    /**
     * {@link DataValidation.ErrorStyle}
     */
    private Integer errorStyle = DataValidation.ErrorStyle.WARNING;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    public Error(String title) {
        this.title = title;
    }

    public Error(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
