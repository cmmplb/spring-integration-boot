package io.github.cmmplb.activiti.vo;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "id", example = "1")
    private String id;

    /**
     * 名称
     */
    @Schema(description = "名称", example = "1")
    private String name;

    /**
     * 流程实例id
     */
    @Schema(description = "流程实例id", example = "1")
    private String processInstanceId;

    /**
     * 流程定义id
     */
    @Schema(description = "流程定义id", example = "1")
    private String processDefinitionId;
}
