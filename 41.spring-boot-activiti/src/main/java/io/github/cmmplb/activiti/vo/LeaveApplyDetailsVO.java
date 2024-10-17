package io.github.cmmplb.activiti.vo;

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
public class LeaveApplyDetailsVO {

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
     * 请假日期
     */
    @Schema(description = "请假日期")
    private List<LeaveApplyDetailsVO.LeaveDate> leaveDateList;

    @Data
    public static class LeaveDate {

        /**
         * 类型:1-事假;2-病假;3-年假;4-丧假;5-产假;
         */
        @Schema(description = "类型")
        private Byte type;

        /**
         * 类型
         */
        @Schema(description = "类型")
        private String typeName;

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

        public String getTypeName() {
            if (null == this.type) {
                return "";
            }
            // 类型:1-事假;2-病假;3-年假;4-丧假;5-产假;
            switch (this.type) {
                case 1:
                    return "事假";
                case 2:
                    return "病假";
                case 3:
                    return "年假";
                case 4:
                    return "丧假";
                case 5:
                    return "产假";
                default:
                    return "";
            }
        }
    }
}
