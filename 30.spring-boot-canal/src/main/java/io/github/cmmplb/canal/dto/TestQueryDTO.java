package io.github.cmmplb.canal.dto;

import io.github.cmmplb.core.beans.QueryPageBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "TestQueryDTO", description = "信息分页搜索参数")
public class TestQueryDTO extends QueryPageBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "", example = "String")
    private String name;

}