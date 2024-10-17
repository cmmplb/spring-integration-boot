package io.github.cmmplb.admin.server.config;

/**
 * @author penglibo
 * @date 2021-09-10 15:13:33
 * @since jdk 1.8
 */

// @Configuration
// public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {
//
//     @Autowired
//     private AdminServerProperties adminServer;
//
//     @Override
//     protected void configure(HttpSecurity http) throws Exception {
//         SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
//         successHandler.setTargetUrlParameter("redirectTo");
//         successHandler.setDefaultTargetUrl(adminServer.path("/"));
//
//         http.headers().frameOptions().disable().and()
//                 .authorizeRequests()
//                 .antMatchers(adminServer.path("/assets/**")).permitAll()
//                 .antMatchers(adminServer.path("/login")).permitAll()
//                 .antMatchers(adminServer.path("/instances/**")).permitAll()
//                 .antMatchers(adminServer.path("/actuator/**")).permitAll()
//                 .anyRequest().authenticated()
//                 .and()
//                 .formLogin()
//                 .loginPage(adminServer.path("/login")).successHandler(successHandler)
//                 .and()
//                 .logout().logoutUrl(adminServer.path("/logout"))
//                 .and()
//                 .httpBasic()
//                 .and()
//                 .csrf().disable();
//     }
// }
