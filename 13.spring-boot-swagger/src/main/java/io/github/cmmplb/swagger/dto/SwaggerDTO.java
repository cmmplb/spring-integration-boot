package io.github.cmmplb.swagger.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author penglibo
 * @date 2024-05-23 09:52:48
 * @since jdk 1.8
 */

@Data
public class SwaggerDTO {

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
