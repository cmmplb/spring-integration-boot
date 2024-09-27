package com.cmmplb.knife4j.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


/**
 * @author penglibo
 * @date 2021-08-04 09:59:53
 * @since jdk 1.8
 */

@Data
@Schema(name = "OrderDateDTO", description = "OrderDateDTO")
public class OrderDateDTO {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private String id;

    @Schema(name = "name", description = "名称")
    private String name;
}
