package io.github.cmmplb.security.sms;

import io.github.cmmplb.security.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author penglibo
 * @date 2021-09-26 15:44:24
 * @since jdk 1.8
 * 手机号验证提供者
 */

@AllArgsConstructor
public class SmsAuthenticationProvider implements AuthenticationProvider {

    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 根据手机号获取账号信息
        UserDetails userDetails = userService.getUserByMobile((String) authentication.getPrincipal());
        SmsAuthenticationToken authenticationResult = new SmsAuthenticationToken(userDetails, userDetails.getAuthorities());
        authenticationResult.setDetails(authentication.getDetails());
        return authenticationResult;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SmsAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
