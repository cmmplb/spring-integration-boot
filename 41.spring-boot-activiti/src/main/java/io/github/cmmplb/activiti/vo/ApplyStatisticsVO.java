package io.github.cmmplb.activiti.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author penglibo
 * @date 2023-12-06 13:44:34
 * @since jdk 1.8
 */

@Data
public class ApplyStatisticsVO {

    @Schema(description = "时期,对应小时和日期", example = "13:00/9-25")
    private List<String> timeList;

    @Schema(description = "名称集合", example = "['请假申请','出差申请']")
    private List<String> nameList;

    @Schema(description = "发送数据", example = "1")
    private List<ApplyStatisticsData> dataList;

    @Data
    public static class ApplyStatisticsData {

        @Schema(description = "名称", example = "请假申请")
        private String name;

        @Schema(description = "时间/日期对应数据", example = "[11,33,22,44]")
        private List<Integer> data;
    }
}
