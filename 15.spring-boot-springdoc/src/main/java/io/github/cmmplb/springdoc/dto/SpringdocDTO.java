package io.github.cmmplb.springdoc.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author penglibo
 * @date 2024-05-23 09:52:48
 * @since jdk 1.8
 */

@Data
@Schema(name = "SpringdocDTO实体类")
public class SpringdocDTO {

    @Schema(description = "主键id", example = "1")
    private String id;

    @NotNull
    @Min(18)
    @Max(35)
    @Schema(description = "姓名", example = "张飞")
    private String name;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "订单信息", type = "List")
    private List<OrderDateDTO> orderDateDTO;

    //more...
}
