package io.github.cmmplb.excel.handler.data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Prompt {

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;
}