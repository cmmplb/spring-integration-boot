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
@Schema(name = "ApplyDTO", description = "申请信息")
public class ApplyDTO extends QueryPageBean {

    /**
     * 类型:1-请假;2-出差;3...
     */
    @Schema(description = "类型:1-请假;2-出差;3...", example = "1")
    private Byte type;

    /**
     * 流程状态:0-进行中;1-已完成;2-已驳回;3-已撤销;
     */
    @Schema(description = "流程状态:0-进行中;1-已完成;2-已驳回;3-已撤销;", example = "1")
    private Byte status;

    /**
     * 申请人id
     */
    @Schema(description = "申请人id", example = "1")
    private Long userId;
}
