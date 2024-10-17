# 功能

- Authentication (认证)，就是用户登录
- Authorization (授权)，判断用户拥有什么权限，可以访问什么资源
- 安全防护，跨站脚本攻击，session攻击等

## 配置

````java
/*
 * 1.配置权限相关的配置，在spring security6.x版本之后，原先经常用的and()方法被废除了，现在spring官方推荐使用Lambda表达式的写法。
 * */
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity.authorizeHttpRequests(registry -> registry
            // 设置登录路径所有人都可以访问
            .requestMatchers("/login", "/css/bootstrap.min.css", "/css/signin.css").permitAll()
            // 其他路径都要进行拦截
            .anyRequest().authenticated()
    );
    // 设置登录页面，默认的登录页面导入的两个css需要翻墙才能访问，这里下载下来单独引用
    httpSecurity.formLogin(loginConfigurer -> loginConfigurer.loginPage("/login"));
    // 关闭csrf保护
    httpSecurity.csrf(AbstractHttpConfigurer::disable);
    return httpSecurity.build();
}
````

````java
/**
 * 2.配置AuthenticationManager，这个对象是spring security中用户认证的核心对象，它负责用户认证
 * 也可以对每个set提取出来，创建对应的bean实例
 * // @Autowired
 // private AuthenticationConfiguration authenticationConfiguration;
 // return authenticationConfiguration.getAuthenticationManager();
 */
@Bean
public AuthenticationManager authenticationManager() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    // 将编写的UserDetailsService注入进来
    // provider.setUserDetailsService(myUserDetailsService);
    // 将使用的密码编译器加入进来
    // provider.setPasswordEncoder(passwordEncoder);
    // 将provider放置到AuthenticationManager 中
    return new ProviderManager(provider);
}
````

## 注解

**@Secured**

- @Secured注解源自Spring Security框架，它通过指定一个或多个“角色”来限制对方法的访问。

- 当用户尝试访问一个被@Secured注解的方法时，Spring Security将检查用户是否具备注解中指定的任何一个角色。如果用户不具备所需角色，那么访问将被拒绝，并返回一个HTTP
  403 Forbidden响应。

- 在用户向浏览器发送一个请求时会去访问控制器中的方法，然后在访问此控制器中的方法之前会先去UserDetailsService用户细节
  实现类的实现方法中return的User对象查看是否具有@Secured注解中指定的角色，如果有指定的角色，那么系统允许用户访问此控制器方法，
  否则，系统不允许访问此控制器方法；注意在使用@Secured设置角色名字的时候，角色名的前面一定要加上ROLE_前缀

````
@Secured("RLOE_user")
````

**@RolesAllowed**

- @RolesAllowed注解来源于Java的企业版（Java EE）规范，特别是在EJB（Enterprise JavaBeans）中使用。

- 与@Secured类似，@RolesAllowed也是用于限制对特定方法的访问，但它更加灵活，支持更复杂的安全策略表达。

**它们之间的区别**

- 来源不同：@Secured是Spring Security特有的，而@RolesAllowed是Java EE标准的一部分。
- 灵活性：@RolesAllowed通常提供更丰富的配置选项，比如支持角色的OR和AND组合，而@Secured则相对简单，通常只支持单一角色或角色的简单组合。
- 环境适用性：@Secured主要用于Spring框架环境下，特别是Spring MVC和Spring Boot项目中。而@RolesAllowed主要用在Java
  EE环境中，如EJB。
- 安全性需求：对于需要复杂安全策略的应用，@RolesAllowed可能更为合适。但对于大多数基于Spring的应用，@Secured已足够满足需求。

**@PreAuthorize**

访问方法之前进行权限认证，在浏览器发送一个请求后，会访问控制器中的对应的方法，@PreAuthorize注解会在访问控制器中的方法之前进行权限认证，
看看UserDetailsService用户细节实现类中对应的用户有没有相应的权限，如果有那么该用户发送的请求可以进入控制器中对应的方法，
如果没有相应的权限，那么用户发送的请求不能进去控制器中对应的方法；

````
@PreAuthorize("hasAnyAuthority('read','write')")
````

**@PostAuthorize**

在访问控制器中的相关方法之后（方法的return先不访问），进行权限认证，去看看UserDetailsService用户细节实现类中用户是否有对应的权限，
如果有的话，那么控制器方法的最后一句return语句会执行，否则，控制器方法的最后一句return语句不会执行；

````
@PostAuthorize("hasAnyAuthority('write')")
````

**@PostFilter**

注意@PostFilter注解只有在控制器方法的return返回值是一个集合的时候才可以使用；

@PostFilter注解的作用：如果控制器方法的return返回值是一个集合，此注解可以对return的这个集合进行过滤输出；

使用@PostFilte注解可以对集合类型的返回值进行过滤。使用@PostFilter时，Spring Security将移除使对应表达式即的结果为false的元素。

````
filterObject是使用@PreFilter和@PostFilter时的一个内置表达式，表示集合中的当前对象。
@PostFilter(value = "filterObject.username == '张三'")
````

## 报错问题

404: http://localhost/css/bootstrap.css.map?continue

删除掉bootstrap@4.0.0.css末尾的这个注释

/*# sourceMappingURL=bootstrap.css.map */


