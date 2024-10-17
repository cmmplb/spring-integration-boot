package io.github.cmmplb.security.domain.dto;

import lombok.Data;

/**
 * @author penglibo
 * @date 2024-09-03 13:55:29
 * @since jdk 1.8
 */

@Data
public class LoginDTO {

    private String username;

    private String password;
}
