package io.github.cmmplb.shiro.custom.config;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import io.github.cmmplb.shiro.custom.config.core.DefaultSubjectFactory;
import io.github.cmmplb.shiro.custom.config.core.RedisCacheManager;
import io.github.cmmplb.shiro.general.constants.AuthorizationConstants;
import io.github.cmmplb.shiro.general.filter.AuthFilter;
import io.github.cmmplb.shiro.general.properties.ShiroProperties;
import io.github.cmmplb.core.constants.StringConstant;
import io.github.cmmplb.redis.configuration.properties.RedisProperties;
import io.github.cmmplb.redis.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisSentinelManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
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
@EnableConfigurationProperties({RedisProperties.class, ShiroProperties.class})
@ConditionalOnProperty(prefix = ShiroProperties.PREFIX, name = ShiroProperties.GENERAL, havingValue = StringConstant.FALSE)
public class ShiroConfig {

    @Autowired
    private RedisProperties redisProperties;

    @Autowired
    private RedisService redisService;

    @Autowired
    private ShiroProperties shiroProperties;

    /**
     * 过滤配置
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 设置securityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager());

        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

        // 添加自定义过滤器
        Map<String, Filter> filterMap = new HashMap<>();
        filterMap.put(AuthFilter.AUTH, new AuthFilter(shiroProperties));
        shiroFilterFactoryBean.setFilters(filterMap);

        filterChainDefinitionMap.put(AuthFilter.PATTEN, AuthFilter.AUTH);

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 安全管理器=SessionsSecurityManager
     */
    @Bean
    public SecurityManager securityManager() {
        // 配置SecurityManager, 并注入shiroRealm
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 自定义Realm验证
        securityManager.setRealm(shiroRealm());
        // 自定义Cache实现
        securityManager.setCacheManager(cacheManager());
        securityManager.setSubjectFactory(subjectFactory());
        securityManager.setSubjectDAO(subjectDAO());
        return securityManager;
    }

    @Bean
    public DefaultSubjectFactory subjectFactory() {
        return new DefaultSubjectFactory();
    }

    @Bean
    public DefaultSubjectDAO subjectDAO() {
        DefaultSubjectDAO defaultSubjectDAO = new DefaultSubjectDAO();
        defaultSubjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator());
        return defaultSubjectDAO;
    }

    @Bean
    public DefaultSessionStorageEvaluator defaultSessionStorageEvaluator() {
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        return defaultSessionStorageEvaluator;
    }

    /**
     * 身份验证器-自定义实现
     */
    @Bean
    public ShiroRealm shiroRealm() {
        ShiroRealm shiroRealm = new ShiroRealm(redisService);
        shiroRealm.setCachingEnabled(true);
        return shiroRealm;
    }

    /**
     * 配置Cache管理器
     */
    @Bean
    public RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        redisCacheManager.setExpire(AuthorizationConstants.AUTH_PERMISSIONS_CACHE_EXPIRE_SECONDS);
        return redisCacheManager;
    }

    /**
     * 配置Redis管理器
     * org.crazycake.shiro.RedisManager
     * org.crazycake.shiro.RedisClusterManager
     * org.crazycake.shiro.RedisSentinelManager
     */
    @Bean
    public RedisSentinelManager redisManager() {
        RedisProperties.Sentinel sentinel = redisProperties.getSentinel();
        RedisSentinelManager redisSentinelManager = new RedisSentinelManager();
        redisSentinelManager.setHost(CollUtil.join(sentinel.getNodes(), StrUtil.COMMA));
        redisSentinelManager.setMasterName(sentinel.getMaster());
        redisSentinelManager.setPassword(sentinel.getPassword());
        redisSentinelManager.setDatabase(sentinel.getDatabase());
        return redisSentinelManager;
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    /**
     * 开启Shiro-aop注解支持-好像1.4+高版本自动开启了这个bean
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
        return authorizationAttributeSourceAdvisor;
    }

}
