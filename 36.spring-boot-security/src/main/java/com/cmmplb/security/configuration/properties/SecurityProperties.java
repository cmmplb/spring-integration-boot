package com.cmmplb.security.configuration.properties;

import com.cmmplb.security.annotation.WithoutLogin;
import io.netty.util.internal.StringUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.lang.NonNull;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.HashMap;
import java.util.Map;

/**
 * @author penglibo
 * @date 2024-09-03 17:46:41
 * @since jdk 1.8
 */

@Slf4j
@Setter
@Getter
@ConfigurationProperties(prefix = "security")
public class SecurityProperties implements InitializingBean, ApplicationContextAware {

    /**
     * 是否开启资源拦截, 默认开启
     */
    private Boolean enabled = true;

    /**
     * 白名单, 以map存储, key为资源分组名称, value为资源路径数组, 按','分割, 例:
     * white-list:
     * --system: /user/info/*,/user/info/mobile/*,/client/login
     * 前面的--是为了防止格式化代码, 往前缩进了，实际配置前面需要两个空格。0.0
     */
    private Map<String, String> whiteList = new HashMap<>();

    private ApplicationContext applicationContext;

    // 资源分组名称
    public static final String WITHOUT_LOGIN = "without_login";

    public static final String PREFIX = "security";

    public static final String ENABLED = "enabled";

    private void build(RequestMappingInfo mappingInfo, Object ignore) {
        if (null != ignore && null != mappingInfo.getPatternsCondition()) {
            mappingInfo.getPatternsCondition().getPatterns().forEach(url -> {
                String white = whiteList.get(WITHOUT_LOGIN);
                // 匹配PathVariable的路径正则, 例/user/info/{username}替换为/user/info/*
                String path = url.replaceAll("\\{(.*?)}", "*");
                if (StringUtil.isNullOrEmpty(white)) {
                    white = path;
                } else {
                    white = white + "," + path;
                }
                whiteList.put(WITHOUT_LOGIN, white);
            });
        }
    }

    @Override
    public void afterPropertiesSet() {
        // 获取所有被注解的类或者方法
        RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();
        map.keySet().forEach(mappingInfo -> {
            HandlerMethod handlerMethod = map.get(mappingInfo);
            build(mappingInfo, AnnotationUtils.findAnnotation(handlerMethod.getMethod(), WithoutLogin.class));
            build(mappingInfo, AnnotationUtils.findAnnotation(handlerMethod.getBeanType(), WithoutLogin.class));
        });
        log.info("资源白名单:{}", whiteList);
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext context) {
        this.applicationContext = context;
    }
}
