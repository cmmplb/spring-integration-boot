package io.github.cmmplb.activiti.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/**
 * @author penglibo
 * @date 2023-11-17 15:32:52
 * @since jdk 1.8
 */

@Data
@Schema(name = "ProcessInstanceVO", description = "流程实例信息")
public class ProcessInstanceVO {

    /**
     * id
     */
    @Schema(description = "id", example = "1")
    private String id;

    /**
     * 执行实例名称
     */
    @Schema(description = "执行实例名称", example = "请假")
    private String name;

    /**
     * 执行实例id（外键EXECUTION_ID_）
     */
    @Schema(description = "执行实例id", example = "1")
    private String executionId;

    /**
     * 父级执行实例id（外键EXECUTION_ID_）
     */
    @Schema(description = "父级执行实例id", example = "1")
    private String parentExecutionId;

    /**
     * 业务key
     */
    @Schema(description = "业务key", example = "leave")
    private String businessKey;

    /**
     * 名称
     */
    @Schema(description = "名称", example = "1")
    private String processDefinitionName;

    /**
     * 是否挂机/激活
     */
    @Schema(description = "是否挂机/激活", example = "false")
    private Boolean isSuspended;

    /**
     * 是否结束
     */
    @Schema(description = "是否结束", example = "1")
    private Boolean ended;

    /**
     * 是否结束
     */
    @Schema(description = "是否结束", example = "1")
    private String endedName;

    /**
     * 是否激活
     */
    @Schema(description = "是否激活", example = "1")
    private Boolean active;

    /**
     * 开始时间
     */
    @Schema(description = "开始时间", example = "2021-01-01 12:00:00")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /**
     * 结束时间
     */
    @Schema(description = "结束时间", example = "2021-01-01 12:00:00")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /**
     * 流程启动/发起人id
     */
    @Schema(description = "流程启动/发起人id", example = "1")
    private String startUserId;

    /**
     * 流程启动/发起人姓名
     */
    @Schema(description = "流程启动/发起人姓名", example = "张三")
    private String startUserName;

    /**
     * 当前任务节点
     */
    @Schema(description = "当前任务节点", example = "1")
    private String taskName;

    /**
     * 流程负责人姓名
     */
    @Schema(description = "流程负责人姓名", example = "1")
    private String assigneeName;

    public String getEndedName() {
        return ended ? "是" : "否";
    }
}
