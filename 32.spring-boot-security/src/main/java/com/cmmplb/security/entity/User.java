package com.cmmplb.security.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author penglibo
 * @date 2021-08-30 11:48:02
 * @since jdk 1.8
 */

@Data
public class User implements UserDetails {

    private static final long serialVersionUID = -2595868307427612138L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户的帐号是否已过期,过期的帐户无法*进行身份验证
     */
    private boolean accountNonExpired = true;

    /**
     * 指示用户是被锁定还是未锁定,无法对锁定的用户进行身份验证。
     */
    private boolean accountNonLocked = true;

    /**
     * 指示用户的凭据（密码）是否已过期。过期的 * 凭据会阻止身份验证。
     */
    private boolean credentialsNonExpired = true;

    /**
     * 指示用户是启用还是禁用。禁用的用户无法验证
     */
    private boolean enabled = true;

    // 授予用户的权限
    private List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }
}
