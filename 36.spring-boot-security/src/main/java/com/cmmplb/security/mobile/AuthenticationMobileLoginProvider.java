package com.cmmplb.security.mobile;

import com.cmmplb.security.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author penglibo
 * @date 2024-09-03 10:59:59
 * @since jdk 1.8
 * 手机号验证提供者
 */

@AllArgsConstructor
public class AuthenticationMobileLoginProvider implements AuthenticationProvider {

    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 根据手机号获取账号信息
        UserDetails userDetails = userService.getUserByMobile((String) authentication.getPrincipal());
        AuthenticationMobileLoginToken authenticationResult = new AuthenticationMobileLoginToken(userDetails, userDetails.getAuthorities());
        authenticationResult.setDetails(authentication.getDetails());
        return authenticationResult;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return AuthenticationMobileLoginToken.class.isAssignableFrom(authentication);
    }
}