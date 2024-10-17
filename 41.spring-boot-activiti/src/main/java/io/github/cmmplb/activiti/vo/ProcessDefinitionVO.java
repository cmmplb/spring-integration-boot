package io.github.cmmplb.activiti.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author penglibo
 * @date 2023-10-17 11:13:43
 * @since jdk 1.8
 */

@Data
@Schema(name = "ProcessDefinitionVO", description = "流程定义信息")
public class ProcessDefinitionVO {

    /**
     * 流程定义id
     */
    @Schema(description = "流程定义id", example = "1")
    private String id;

    /**
     * 名称
     */
    @Schema(description = "名称", example = "请假")
    private String name;

    /**
     * 类型
     */
    @Schema(description = "类型", example = "home")
    private String category;

    /**
     * 关键字
     */
    @Schema(description = "关键字", example = "leave")
    private String key;

    /**
     * 描述信息
     */
    @Schema(description = "描述信息", example = "请假")
    private String description;

    /**
     * 版本, 从1开始
     */
    @Schema(description = "版本, 从1开始", example = "1")
    private Integer version;

    /**
     * 资源路径
     */
    @Schema(description = "资源路径", example = "/usr/local/leave.bpmn20.xml")
    private String resourceName;

    /**
     * 部署ID
     */
    @Schema(description = "部署ID", example = "1")
    private String deploymentId;

    /**
     * 是否挂起/激活
     */
    @Schema(description = "是否挂起/激活", example = "true")
    private Boolean isSuspended;
}
