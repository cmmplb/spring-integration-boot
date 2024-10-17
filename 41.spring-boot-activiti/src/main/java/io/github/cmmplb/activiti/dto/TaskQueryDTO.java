package io.github.cmmplb.activiti.dto;

import io.github.cmmplb.core.beans.QueryPageBean;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author penglibo
 * @date 2023-10-17 11:13:43
 * @since jdk 1.8
 */

@EqualsAndHashCode(callSuper = true)
@Data
@Schema(name = "TaskDTO", description = "办理信息")
public class TaskQueryDTO extends QueryPageBean {

    /**
     * 类型:1-请假;2-出差;3...
     */
    @Schema(description = "类型:1-请假;2-出差;3...", example = "1")
    private Byte type;

    /**
     * 申请人id
     */
    @Schema(description = "申请人id", example = "1")
    private Long userId;
}
