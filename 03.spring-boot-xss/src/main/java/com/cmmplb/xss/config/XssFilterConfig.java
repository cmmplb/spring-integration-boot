package com.cmmplb.xss.config;

import com.cmmplb.core.beans.DataMap;
import com.cmmplb.xss.filter.XssFilter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.DispatcherType;
import java.util.HashMap;
import java.util.Map;

/**
 * @author penglibo
 * @date 2021-09-10 17:21:42
 * @since jdk 1.8
 */

@Configuration
@EnableConfigurationProperties(XssProperties.class)
public class XssFilterConfig {

    @Autowired
    private XssProperties xssProperties;

    @Bean
    public FilterRegistrationBean<?> xssFilterRegistration() {
        FilterRegistrationBean<XssFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setDispatcherTypes(DispatcherType.REQUEST);
        registrationBean.setFilter(new XssFilter());
        registrationBean.addUrlPatterns(StringUtils.split(xssProperties.getUrlPatterns(), ","));
        registrationBean.setInitParameters(new DataMap<String, String>()
                .set("excludes", xssProperties.getExcludes())
                .set("enabled", xssProperties.getEnabled())
                .set("isIncludeRichText", xssProperties.getIsIncludeRichText())
        );
        return registrationBean;
    }
}
