package com.cmmplb.swagger.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author penglibo
 * @date 2021-08-04 09:59:53
 * @since jdk 1.8
 */

@Data
public class OrderDateDTO {

    @ApiModelProperty(value = "主键id")
    private String id;
}
