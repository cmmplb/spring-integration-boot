package io.github.cmmplb.security.domain.convert;

import lombok.Data;
import lombok.Setter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author penglibo
 * @date 2024-09-02 11:29:59
 * @since jdk 1.8
 */

@Data
public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {

    @Serial
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
     * 是否没有过期, 用户的帐号是否已过期, 过期的帐户无法*进行身份验证
     */
    private boolean accountNonExpired = true;

    /**
     * 是否没有锁定, 指示用户是被锁定还是未锁定,无法对锁定的用户进行身份验证.
     */
    private boolean accountNonLocked = true;

    /**
     * 是否没有过期, 指示用户的凭据（密码）是否已过期. 过期的 * 凭据会阻止身份验证.
     */
    private boolean credentialsNonExpired = true;

    /**
     * 是否启用, 指示用户是启用还是禁用. 禁用的用户无法验证
     */
    private boolean enabled = true;

    // 授予用户的权限
    @Setter
    private List<SimpleGrantedAuthority> authorities = new ArrayList<>();

    @Override
    public Collection<? extends SimpleGrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String toString() {
        return this.username;
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return this.toString().equals(obj.toString());
    }
}
