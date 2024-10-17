package io.github.cmmplb.activiti.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/**
 * @author penglibo
 * @date 2023-10-17 11:13:43
 * @since jdk 1.8
 */

@Data
public class EvectionApplyDetailsVO {

    /**
     * 申请人姓名
     */
    @Schema(description = "申请人姓名")
    private String userName;

    /**
     * 请假说明
     */
    @Schema(description = "请假说明")
    private String reason;

    /**
     * 开始时间
     */
    @Schema(description = "开始时间")
    private Date startTime;

    /**
     * 结束时间
     */
    @Schema(description = "结束时间")
    private Date endTime;
}
