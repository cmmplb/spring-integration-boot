package io.github.cmmplb.map.struct.basic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author penglibo
 * @date 2022-08-03 16:55:16
 * @since jdk 1.8
 */

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    /** 用户编号 **/
    private Integer userId;
    /** 用户名 **/
    private String userName;
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
