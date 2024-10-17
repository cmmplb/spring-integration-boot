package io.github.cmmplb.activiti.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author penglibo
 * @date 2023-10-17 11:13:43
 * @since jdk 1.8
 */

@Data
@Schema(name = "EvectionApplyDTO", description = "出差申请参数")
public class EvectionApplyDTO {

    /**
     * 标题
     */
    @Schema(description = "标题")
    private String title;

    /**
     * 申请时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date applyTime;

    /**
     * 申请时间
     */
    @Schema(description = "开始时间")
    private Date startTime;

    /**
     * 申请时间
     */
    @Schema(description = "结束时间")
    private Date endTime;

    /**
     * 出差说明
     */
    @Schema(description = "出差说明")
    private String reason;
}
