package io.github.cmmplb.activiti.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/**
 * @author penglibo
 * @date 2023-10-17 11:13:43
 * @since jdk 1.8
 */

@Data
@Schema(name = "JobVO", description = "任务信息")
public class JobVO {

    /**
     * id
     */
    @Schema(description = "id", example = "1")
    private String id;

    /**
     * 处理日期
     */
    @Schema(description = "处理日期")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dueDate;

    /**
     * 任务类型
     */
    @Schema(description = "任务类型")
    private String jobType;

    /**
     * 异常原因
     */
    @Schema(description = "异常原因")
    private String exceptionMessage;

    /**
     * 任务执行类型
     */
    @Schema(description = "任务执行类型")
    private String jobHandlerType;

    /**
     * 流程定义id
     */
    @Schema(description = "流程定义id")
    private String processDefinitionId;

    /**
     * 流程实例id
     */
    @Schema(description = "流程实例id")
    private String processInstanceId;

    /**
     * 流程执行id
     */
    @Schema(description = "流程执行id")
    private String executionId;
}
