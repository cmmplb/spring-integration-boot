package com.cmmplb.security.handler.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author penglibo
 * @date 2024-09-03 14:46:37
 * @since jdk 1.8
 * {@link org.springframework.security.core.userdetails.UsernameNotFoundException}
 */
public class MobileNotFoundException extends AuthenticationException {

    public MobileNotFoundException(String msg) {
        super(msg);
    }

    public MobileNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
