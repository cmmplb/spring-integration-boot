package com.cmmplb.shiro.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author penglibo
 * @date 2021-09-27 17:03:49
 * @since jdk 1.8
 */

@Data
@AllArgsConstructor
public class LoginVO {

    @ApiModelProperty(value = "令牌", example = "123456")
    private String token;
}
