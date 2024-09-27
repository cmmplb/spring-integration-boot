package com.cmmplb.canal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;


/**
 * @author momo
 * @date 2021-12-02 11:50:46
 * @since jdk 1.8
 * 
 */

@Data
@Schema(name = "TestDTO", description = "信息参数")
public class TestDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(name = "id", description = "主键id", example = "1")
    private Long id;

    @Schema(description = "名称")
    private String name;


}