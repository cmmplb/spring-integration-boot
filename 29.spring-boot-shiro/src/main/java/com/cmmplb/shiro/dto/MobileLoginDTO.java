package com.cmmplb.shiro.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author penglibo
 * @date 2021-09-27 18:09:14
 * @since jdk 1.8
 */

@Data
public class MobileLoginDTO {

    @ApiModelProperty(value = "手机号", example = "19999999999", required = true)
    private String mobile;

    @ApiModelProperty(value = "短信验证码", example = "1456", required = true)
    private String code;

}
