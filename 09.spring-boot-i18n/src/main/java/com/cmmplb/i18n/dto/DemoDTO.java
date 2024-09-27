package com.cmmplb.i18n.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author penglibo
 * @date 2021-08-04 09:55:24
 * @since jdk 1.8
 */

@Data
@Schema(name = "DemoDTO", description = "DemoDTO")
public class DemoDTO {

    @Schema(name = "id", description = "主键id")
    // @Null(message = "新增时id必须为空", groups = {Insert.class})
    // @NotNull(message = "更新时id不能为空", groups = {Update.class})
    private String id;

    @NotBlank(message = "{vo.user}")
    @Schema(name = "name", description = "姓名", example = "张飞")
    private String name;

    @Schema(name = "email", description = "邮箱", example = "@163.com")
    private String email;

    @Schema(name = "orderDateDTO", description = "订单信息")
    private OrderDate orderDate;

    //more...
}
