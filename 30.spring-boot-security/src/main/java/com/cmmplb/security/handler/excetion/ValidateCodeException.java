package com.cmmplb.security.handler.excetion;

import org.springframework.security.core.AuthenticationException;

public class ValidateCodeException extends AuthenticationException {

    private static final long serialVersionUID = 2272155535583332928L;

    public ValidateCodeException(String message) {
        super(message);
    }
}
