package io.github.cmmplb.map.struct.advanced.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author penglibo
 * @date 2022-08-03 16:55:31
 * @since jdk 1.8
 */

@Data
@Accessors(chain = true)
public class UserVO {

    /** 用户编号 **/
    private Integer id;
    /** 用户名 **/
    private String username;
    /** 密码 **/
    private String password;

    // public Integer getId() {
    //     return id;
    // }
    //
    // public void setId(Integer id) {
    //     this.id = id;
    // }
    //
    // public String getUsername() {
    //     return username;
    // }
    //
    // public void setUsername(String username) {
    //     this.username = username;
    // }
    //
    // public String getPassword() {
    //     return password;
    // }
    //
    // public void setPassword(String password) {
    //     this.password = password;
    // }
}
