package com.cmmplb.canal.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author momo
 * @date 2021-12-02 11:50:46
 * @since jdk 1.8
 * 
 */

@Data
@Schema(name = "Test", description = "信息参数")
public class TestVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(name = "id", description = "主键id", example = "1")
    private Long id;

    @Schema(description = "名称")
    private String name;

    /**
    * 创建时间
    */
    @Schema(description = "创建时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}