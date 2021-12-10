package com.cmmplb.shiro.config.shiro;

import com.cmmplb.common.redis.service.RedisService;
import com.cmmplb.shiro.config.properties.RedisProperties;
import com.cmmplb.shiro.config.properties.ShiroProperties;
import com.cmmplb.shiro.config.shiro.session.CustomSessionDao;
import com.cmmplb.shiro.config.shiro.session.ShiroSessionIdGenerator;
import com.cmmplb.shiro.config.shiro.session.ShiroSessionListener;
import com.cmmplb.shiro.config.shiro.session.ShiroSessionManager;
import com.cmmplb.shiro.constants.RedisConstants;
import com.cmmplb.shiro.filter.AuthenticationFilter;
import com.cmmplb.shiro.utils.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author penglibo
 * @date 2021-09-18 15:16:54
 * @since jdk 1.8
 * shiro配置
 */

@Slf4j
@Configuration
@EnableConfigurationProperties
public class ShiroConfig {

    @Autowired
    private RedisService redisService;

    @Autowired
    private RedisProperties redisProperties;

    @Autowired
    private ShiroProperties shiroProperties;

    // ==================================1-过滤配置=================================================

    /**
     * 过滤配置
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager()); // 设置securityManager
        if (shiroProperties.getSplit()) { // 是否前后端分离
            shiroFilterFactoryBean.setLoginUrl("/basic/un/login"); // 未登录跳转的url-请求路径
        } else {
            shiroFilterFactoryBean.setLoginUrl("/login"); // 未登录跳转的url-freemarker请求路径映射的login.html页面
            shiroFilterFactoryBean.setSuccessUrl("/"); // 登录成功后跳转的url
        }
        shiroFilterFactoryBean.setUnauthorizedUrl("/basic/unauthorized"); // 未授权url

        // anon-放行，authc-需要认证，注意的是filterChain基于短路机制，即最先匹配原则，如：
        //  /user/**=anon
        //  /user/aa=authc 永远不会执行
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

        // 添加自定义过滤器
        Map<String, Filter> filterMap = new HashMap<>();
        filterMap.put("authenticationFilter", new AuthenticationFilter());
        shiroFilterFactoryBean.setFilters(filterMap);

        // shiroProperties.getWhitelist().forEach(url -> filterChainDefinitionMap.put(url, "anon"));

        // ==================静态资源不拦截==================
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/favicon.ico", "anon");
        filterChainDefinitionMap.put("/doc.html", "anon");
        filterChainDefinitionMap.put("/webjars/**", "anon"); // swagger相关资源
        filterChainDefinitionMap.put("/swagger-ui.html", "anon");
        filterChainDefinitionMap.put("/swagger-resources/**", "anon");
        filterChainDefinitionMap.put(/*v2改成v3了*/"/v3/api-docs/**", "anon");
        filterChainDefinitionMap.put("/druid/**", "anon");  // druid数据源监控页面不拦截

        // ==================放行接口请求==================
        filterChainDefinitionMap.put("/logout", "logout"); // 配置退出过滤器，其中具体的退出代码Shiro已经替我们实现了
        filterChainDefinitionMap.put("/basic/**", "anon"); // 基础管理里面的接口都放行
        filterChainDefinitionMap.put("/login/do", "anon"); // 放行登录请求

        // 除上以外所有url都必须认证通过才可以访问，未通过认证自动访问LoginUrl
        filterChainDefinitionMap.put("/**", "authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 开启Shiro-aop注解支持-好像1.4+高版本自动开启了这个bean
     */
    /*@Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
        return authorizationAttributeSourceAdvisor;
    }*/

    // ==================================2-安全管理器=================================================

    /**
     * 安全管理器
     */
    @Bean
    public SessionsSecurityManager securityManager() {
        // 配置SecurityManager，并注入shiroRealm
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(shiroRealm()); // 自定义Realm验证
        // securityManager.setCacheManager(cacheManager()); // 自定义Cache实现
        securityManager.setSessionManager(sessionManager()); // 自定义Ssession管理
        // securityManager.setRememberMeManager(rememberMeManager()); // 记住我-自动登录功能
        return securityManager;
    }

    // ==================================3-身份验证器=================================================

    /**
     * 身份验证器-自定义实现
     */
    @Bean
    public ShiroRealm shiroRealm() {
        ShiroRealm shiroRealm = new ShiroRealm();
        shiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return shiroRealm;
    }

    /**
     * 凭证匹配器
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher shaCredentialsMatcher = new HashedCredentialsMatcher();
        shaCredentialsMatcher.setHashAlgorithmName(MD5Util.MD5); // 使用MD5加密算法
        shaCredentialsMatcher.setHashIterations(MD5Util.HASH_ITERATIONS); // 循环次数一次
        return shaCredentialsMatcher;
    }

    // ==================================4-权限Cache管理器=================================================

    /**
     * 配置Cache管理器
     */
    /*@Bean
    public RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        redisCacheManager.setKeyPrefix(RedisConstants.CACHE_KEY);
        // 配置缓存的话要求放在session里面的实体类必须有个id标识
        redisCacheManager.setPrincipalIdFieldName("userId");
        //用户权限信息缓存时间
        redisCacheManager.setExpire(RedisConstants.USER_EXPIRE);
        return redisCacheManager;
    }*/

    /**
     * 配置Redis管理器
     */
    @Bean
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(redisProperties.getHostAndPort());
        redisManager.setTimeout(redisProperties.getTimeout());
        redisManager.setPassword(redisProperties.getPassword());
        redisManager.setDatabase(redisProperties.getDatabase());
        return redisManager;
    }

    // ==================================5-Session管理器=================================================

    /**
     * 配置Session管理器
     */
    @Bean
    public SessionManager sessionManager() {
        ShiroSessionManager shiroSessionManager = new ShiroSessionManager();
        // shiroSessionManager.setSessionDAO(memorySessionDAO()); // 基于内存
        shiroSessionManager.setSessionDAO(redisSessionDAO()); // 基于缓存
        // shiroSessionManager.setSessionDAO(customSessionDao()); // 自定义
        shiroSessionManager.setSessionListeners(Collections.singletonList(new ShiroSessionListener()));
        shiroSessionManager.setSessionIdCookieEnabled(false); // 禁用cookie
        shiroSessionManager.setGlobalSessionTimeout(RedisConstants.GLOBAL_SESSION_TIMEOUT);
        return shiroSessionManager;
    }

    /**
     * 配置MemorySessionDAO-内存session
     */
    /*@Bean
    public MemorySessionDAO memorySessionDAO() {
        MemorySessionDAO memorySessionDAO = new MemorySessionDAO();
        memorySessionDAO.setSessionIdGenerator(sessionIdGenerator());
        return memorySessionDAO;
    }*/

    /**
     * 配置RedisSessionDAO
     */
    @Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        redisSessionDAO.setSessionIdGenerator(sessionIdGenerator());
        redisSessionDAO.setKeyPrefix(RedisConstants.SESSION_KEY);
        redisSessionDAO.setExpire(RedisConstants.SESSION_EXPIRE);
        return redisSessionDAO;
    }

    /**
     * 自定义SessionDAO-上面的RedisSessionDAO存储的session过期时间设置有问题。
     */
    @Bean
    public CustomSessionDao customSessionDao() {
        CustomSessionDao customSessionDao = new CustomSessionDao();
        customSessionDao.setRedisService(redisService);
        customSessionDao.setSessionIdGenerator(sessionIdGenerator());
        return customSessionDao;
    }

    /**
     * SessionID生成器
     */
    @Bean
    public ShiroSessionIdGenerator sessionIdGenerator() {
        return new ShiroSessionIdGenerator();
    }

    // ==================================6-RememberMe=================================================

    /**
     * cookie管理对象
     */
    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        // rememberMe cookie加密的密钥
        cookieRememberMeManager.setCipherKey(Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));
        return cookieRememberMeManager;
    }

    /**
     * cookie对象
     */
    public SimpleCookie rememberMeCookie() {
        // 设置cookie名称，对应login.html页面的<input type="checkbox" name="rememberMe"/>
        SimpleCookie cookie = new SimpleCookie("rememberMe");
        // 设置cookie的过期时间，单位为秒，这里为一天
        cookie.setMaxAge(86400);
        return cookie;
    }


}
