package io.github.cmmplb.canal.dto;

import io.github.cmmplb.core.beans.QueryPageBean;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author momo
 * @date 2021-12-02 11:50:46
 * @since jdk 1.8
 * 
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(name = "TestQueryDTO", description = "信息分页搜索参数")
public class TestQueryDTO extends QueryPageBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "", example = "String")
    private String name;

}