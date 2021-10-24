package com.cmmplb.shiro.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author penglibo
 * @date 2021-09-27 18:09:14
 * @since jdk 1.8
 */

@Data
public class LoginDTO {

    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty(value = "用户名", example = "小明", required = true)
    private String username;

    @NotBlank(message = "密码不能为空")
    @ApiModelProperty(value = "密码-这里演示就明文传输，后端md5加密一次校验了", example = "123456", required = true)
    private String password;

    @NotBlank(message = "图形验证码uuid不能为空")
    @ApiModelProperty(value = "获取短信的uuid", example = "6624429abf22632d59ceef20cad4aa38", required = true)
    private String uuid;

    @NotBlank(message = "图形验证码不能为空")
    @ApiModelProperty(value = "图形验证码", example = "am9", required = true)
    private String graphCode;

    @ApiModelProperty(value = "记住我", example = "true", required = true)
    private Boolean rememberMe;

}
