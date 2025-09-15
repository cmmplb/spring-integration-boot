package io.github.cmmplb.activiti.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author penglibo
 * @date 2023-10-17 13:55:18
 * @since jdk 1.8
 */

@Data
public class TaskVO {

    /**
     * id
     */
    @ApiModelProperty(value = "id", example = "1")
    private String id;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称", example = "1")
    private String name;

    /**
     * 流程实例id
     */
    @ApiModelProperty(value = "流程实例id", example = "1")
    private String processInstanceId;

    /**
     * 流程定义id
     */
    @ApiModelProperty(value = "流程定义id", example = "1")
    private String processDefinitionId;
}
