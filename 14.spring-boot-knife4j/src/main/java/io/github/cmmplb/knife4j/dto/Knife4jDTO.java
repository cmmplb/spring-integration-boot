package io.github.cmmplb.knife4j.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author penglibo
 * @date 2021-08-04 09:55:24
 * @since jdk 1.8
 */

@Data
@Schema(name = "knife4jDTO", description = "请求参数")
public class Knife4jDTO {

    @Schema(description = "订单编号")
    private String id;

    /**
     * required = true替换为 requiredMode = Schema.RequiredMode.REQUIRED
     */
    @Schema(description = "姓名", example = "张飞", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(name = "email", description = "邮箱", example = "@163.com")
    private String email;

    @Schema(description = "订单信息")
    private OrderDateDTO orderDateDTO;

    //more...
}
