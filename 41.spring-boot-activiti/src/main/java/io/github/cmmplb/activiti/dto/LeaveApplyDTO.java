package io.github.cmmplb.activiti.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author penglibo
 * @date 2023-10-17 11:13:43
 * @since jdk 1.8
 */

@Data
@Schema(name = "LeaveApplyDTO", description = "请假申请参数")
public class LeaveApplyDTO {

    /**
     * 标题
     */
    @Schema(description = "标题")
    private String title;

    /**
     * 申请时间
     */
    @Schema(description = "申请时间")
    private Date applyTime;

    /**
     * 请假说明
     */
    @Schema(description = "请假说明")
    private String reason;

    /**
     * 请假日期
     */
    @Schema(description = "请假日期")
    private List<LeaveDate> leaveDateList;

    @Data
    public static class LeaveDate {

        /**
         * 类型:1-事假;2-病假;3-年假;4-丧假;5-产假;
         */
        @Schema(description = "类型")
        private Byte type;

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
}
