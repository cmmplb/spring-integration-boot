package io.github.cmmplb.i18n.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author penglibo
 * @date 2021-08-04 09:55:24
 * @since jdk 1.8
 */

@Data
public class DemoDTO {

    @ApiModelProperty(value = "主键id")
    // @Null(message = "新增时id必须为空", groups = {Insert.class})
    // @NotNull(message = "更新时id不能为空", groups = {Update.class})
    private String id;

    @NotBlank(message = "{vo.user}")
    @ApiModelProperty(value = "姓名", example = "张飞")
    private String name;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "订单信息")
    private OrderDate orderDate;

    //more...
}
