package io.github.cmmplb.security.handler.exception;

import org.springframework.security.core.AuthenticationException;

public class ValidateCodeException extends AuthenticationException {

    private static final long serialVersionUID = 2272155535583332928L;

    public ValidateCodeException(String message) {
        super(message);
    }
}
