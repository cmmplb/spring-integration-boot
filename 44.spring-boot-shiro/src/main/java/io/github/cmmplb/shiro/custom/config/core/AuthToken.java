package io.github.cmmplb.shiro.custom.config.core;

import lombok.AllArgsConstructor;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author penglibo
 * @date 2022-02-14 15:36:19
 * @since jdk 1.8
 */

@AllArgsConstructor
public class AuthToken implements AuthenticationToken {

    private static final long serialVersionUID = 5540930321295225997L;

    private String token;

    @Override
    public Object getPrincipal() {
        return this.token;
    }

    @Override
    public Object getCredentials() {
        return this.token;
    }
}
