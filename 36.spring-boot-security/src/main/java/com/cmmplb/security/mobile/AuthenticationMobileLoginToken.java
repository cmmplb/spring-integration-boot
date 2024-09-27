package com.cmmplb.security.mobile;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author penglibo
 * @date 2024-09-03 11:00:18
 * @since jdk 1.8
 * 短信登录验证信息封装类
 * 根据 UsernamePasswordAuthenticationToken改造
 * {@link org.springframework.security.authentication.UsernamePasswordAuthenticationToken}
 */

public class AuthenticationMobileLoginToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = -1868502039288777588L;

    private final Object principal;

    public AuthenticationMobileLoginToken(String mobile) {
        super(null);
        this.principal = mobile;
        setAuthenticated(false);
    }

    /**
     * Creates a token with the supplied array of authorities.
     * @param authorities the collection of <tt>GrantedAuthority</tt>s for the principal
     *                    represented by this authentication object.
     */
    public AuthenticationMobileLoginToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        super.setAuthenticated(true); // must use super, as we override
    }

    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }
        super.setAuthenticated(false);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
    }
}
