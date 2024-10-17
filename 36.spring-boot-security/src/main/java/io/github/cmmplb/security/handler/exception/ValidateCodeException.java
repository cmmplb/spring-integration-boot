package io.github.cmmplb.security.handler.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author penglibo
 * @date 2024-09-10 09:04:25
 * @since jdk 1.8
 */

public class ValidateCodeException extends AuthenticationException {

    public ValidateCodeException(String message) {
        super(message);
    }
}
