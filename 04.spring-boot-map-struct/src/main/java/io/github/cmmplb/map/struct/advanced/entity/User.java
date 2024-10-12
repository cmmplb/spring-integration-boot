package com.cmmplb.map.struct.advanced.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author penglibo
 * @date 2022-08-04 09:31:50
 * @since jdk 1.8
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    /** 用户编号 **/
    private Integer id;
    /** 用户名 **/
    private String username;
    /** 密码 **/
    private String password;
}
