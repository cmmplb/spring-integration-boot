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
public class UserLoginDTO {

    @Xss(message = "账号输入包含 xss 风险内容")
    // @NotBlank(message = "账号不能为空")
    @NotBlank
    private String username;

    @Xss
    // @NotBlank(message = "密码不能为空")
    @NotBlank
    private String password;

    @Xss
    // @NotBlank(message = "内容不能为空")
    @NotBlank
    private String content;
}
