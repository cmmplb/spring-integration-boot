package com.cmmplb.i18n.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author penglibo
 * @date 2021-08-04 09:59:53
 * @since jdk 1.8
 */

@Data
@Schema(name = "OrderDate", description = "OrderDate")
public class OrderDate {

    @Schema(name = "id", description = "主键id")
    private String id;
}
