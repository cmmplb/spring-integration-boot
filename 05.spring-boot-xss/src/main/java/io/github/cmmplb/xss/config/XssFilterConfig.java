package io.github.cmmplb.xss.config;

import io.github.cmmplb.core.beans.DataMap;
import io.github.cmmplb.core.constants.StringConstant;
import io.github.cmmplb.core.utils.StringUtil;
import io.github.cmmplb.xss.config.properties.XssProperties;
import io.github.cmmplb.xss.filter.XssFilter;
import jakarta.servlet.DispatcherType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author penglibo
 * @date 2021-09-10 17:21:42
 * @since jdk 1.8
 */

@Slf4j
@Configuration
@EnableConfigurationProperties(XssProperties.class)
public class XssFilterConfig {

    @Autowired
    private XssProperties xssProperties;

    @Bean
    public FilterRegistrationBean<?> xssFilterRegistration() {
        log.info("Creating XssFilterRegistration");
        FilterRegistrationBean<XssFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setDispatcherTypes(DispatcherType.REQUEST);
        registrationBean.setFilter(new XssFilter());
        registrationBean.addUrlPatterns(StringUtils.split(xssProperties.getUrlPatterns(), StringConstant.COMMA));
        DataMap<String, String> param = new DataMap<>();
        param.set(XssProperties.COL_ENABLED, String.valueOf(xssProperties.getEnabled()));
        param.set(XssProperties.COL_IS_INCLUDE_RICH_TEXT, String.valueOf(xssProperties.getIsIncludeRichText()));
        if (StringUtil.isNotEmpty(xssProperties.getExcludes())) {
            param.set(XssProperties.COL_EXCLUDES, xssProperties.getExcludes());
        }
        registrationBean.setInitParameters(param);
        return registrationBean;
    }
}
