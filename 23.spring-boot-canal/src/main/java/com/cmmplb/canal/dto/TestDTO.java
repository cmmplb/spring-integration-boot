package com.cmmplb.canal.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import javax.validation.constraints.*;


/**
 * @author momo
 * @date 2021-12-02 11:50:46
 * @since jdk 1.8
 * 
 */

@Data
@ApiModel(value = "TestDTO", description = "信息参数")
public class TestDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "", example = "1", hidden = true)
    private Long id;

    @ApiModelProperty(value = "", example = "String")
    private String name;


}