package io.github.cmmplb.canal.vo;

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

    @Schema(description = "", example = "1")
    private Long id;

    @Schema(description = "", example = "String")
    private String name;

    /**
    * 创建时间
    */
    @Schema(description = "创建时间", example = "2021-01-01 08:00:00")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}