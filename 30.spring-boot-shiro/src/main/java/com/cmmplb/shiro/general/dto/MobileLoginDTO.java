package com.cmmplb.shiro.general.dto;

import lombok.Data;

/**
 * @author penglibo
 * @date 2021-09-27 18:09:14
 * @since jdk 1.8
 */

@Data
public class MobileLoginDTO {

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 短信验证码
     */
    private String code;

}
