package io.github.cmmplb.xss.dto;

import io.github.cmmplb.xss.annotation.Xss;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author penglibo
 * @date 2025-06-06 15:46:24
 * @since jdk 1.8
 */

@Data
public class ContentDTO {

    @Xss
    // @NotBlank(message = "内容不能为空")
    @NotBlank
    private String content;
}
