package io.github.cmmplb.knife4j.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author penglibo
 * @date 2021-08-04 09:55:24
 * @since jdk 1.8
 */

@Data
public class Knife4jDTO {

    @ApiModelProperty(value = "主键id")
    private String id;

    @ApiModelProperty(value = "姓名", example = "张飞")
    private String name;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "订单信息")
    private OrderDateDTO orderDateDTO;

    //more...
}
