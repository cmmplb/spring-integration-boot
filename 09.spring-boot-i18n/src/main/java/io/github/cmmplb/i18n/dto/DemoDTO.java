package io.github.cmmplb.i18n.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * @author penglibo
 * @date 2021-08-04 09:55:24
 * @since jdk 1.8
 */

@Data
@Schema(name = "DemoDTO", description = "Demo请求参数")
public class DemoDTO {

    @Schema(description = "主键id", hidden = true)
    // @Null(message = "新增时id必须为空", groups = {Insert.class})
    // @NotNull(message = "更新时id不能为空", groups = {Update.class})
    private String id;

    @NotBlank(message = "{vo.user}")
    @NotBlank(message = "{vo.user}")
    @Size(max = 255, message = "姓名长度限制255")
    @Schema(description = "姓名", example = "张飞", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "排序", example = "1", type = "integer")
    private Integer weight;

    @Schema(description = "邮箱", example = "cmmplb@163.com")
    private String email;

    @Schema(description = "订单信息")
    private OrderDate orderDate;

    //more...
}
