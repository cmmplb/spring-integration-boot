package io.github.cmmplb.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author penglibo
 * @date 2021-09-08 11:46:41
 * @since jdk 1.8
 */
public interface UserService extends UserDetailsService {

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    UserDetails getUserByMobile(String mobile);
}
