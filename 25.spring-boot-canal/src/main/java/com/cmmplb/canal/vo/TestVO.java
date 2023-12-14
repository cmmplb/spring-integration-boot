package com.cmmplb.canal.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "Test", description = "信息参数")
public class TestVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "", example = "1")
    private Long id;

    @ApiModelProperty(value = "", example = "String")
    private String name;

    /**
    * 创建时间
    */
    @ApiModelProperty(value = "创建时间", example = "2021-01-01 08:00:00")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}