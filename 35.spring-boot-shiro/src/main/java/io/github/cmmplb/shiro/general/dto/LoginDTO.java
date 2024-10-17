package io.github.cmmplb.shiro.general.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author penglibo
 * @date 2021-09-27 18:09:14
 * @since jdk 1.8
 */

@Data
public class LoginDTO {

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;

    /**
     * 获取短信的uuid
     */
    @NotBlank(message = "图形验证码uuid不能为空")
    private String uuid;

    /**
     * 图形验证码
     */
    @NotBlank(message = "图形验证码不能为空")
    private String graphCode;
}
