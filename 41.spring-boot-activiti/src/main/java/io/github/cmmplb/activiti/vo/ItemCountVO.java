package io.github.cmmplb.activiti.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author penglibo
 * @date 2023-12-06 13:44:34
 * @since jdk 1.8
 */

@Data
public class ItemCountVO {

    /**
     * 代办数量
     */
    @Schema(description = "代办数量", example = "1")
    private Long incompleteCount;

    /**
     * 已办数量
     */
    @Schema(description = "已办数量", example = "1")
    private Long completedCount;

    /**
     * 请假数量
     */
    @Schema(description = "请假数量", example = "1")
    private Long leaveCount;

    /**
     * 出差数量
     */
    @Schema(description = "出差数量", example = "1")
    private Long evectionCount;

}
