package com.cmmplb.i18n.config;

import com.cmmplb.core.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import javax.validation.Validator;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Locale;

/**
 * @author penglibo
 * @date 2021-09-14 11:58:58
 * @since jdk 1.8
 */


@Configuration
@EnableConfigurationProperties(I18nConfigProperties.class)
public class I18nConfig {

    private Locale locale;

    private final static String EN = "en";

    private final static String US = "US";

    private final static String LANG = "lang";

    @Autowired
    private I18nConfigProperties i18nSpringConfigProperties;

    @Bean(name = "messageSource")
    public ResourceBundleMessageSource getMessageResource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setCacheSeconds(i18nSpringConfigProperties.getCacheSeconds());
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
        messageSource.getBasenameSet().addAll(Arrays.asList(i18nSpringConfigProperties.getBasename().split(",")));
        return messageSource;
    }

    /**
     * 默认解析器 其中locale表示默认语言,当请求中未包含语种信息，则设置默认语种
     */
    @Bean
    public LocaleResolver localeResolver() {
        // AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver(); // 报错-java.lang.UnsupportedOperationException: Cannot change HTTP accept header - use a different locale resolution strategy
        // localeResolver.setSupportedLocales(Arrays.asList(Locale.US, Locale.SIMPLIFIED_CHINESE));
        CookieLocaleResolver localeResolver = new CookieLocaleResolver();
        localeResolver.setDefaultLocale(getLocale());
        return localeResolver;
    }

    @Bean
    public Locale getLocale() {
        if (locale != null) {
            return locale;
        }
        // 设置默认语言
        String[] i18n_defaultArray = new String[]{EN, US};
        if (StringUtil.isEmpty(i18nSpringConfigProperties.getDefaultLanguage())) {
            i18n_defaultArray = i18nSpringConfigProperties.getDefaultLanguage().split("_");
        }
        if (i18n_defaultArray.length == 1) {
            locale = new Locale(i18n_defaultArray[0]);
        } else {
            locale = new Locale(i18n_defaultArray[0], i18n_defaultArray[1]);
        }
        return locale;
    }

    /**
     * 默认拦截器 其中lang表示切换语言的参数名
     * 拦截请求，获取请求参数lang种包含的语种信息并重新注册语种信息
     */
    @Bean
    public WebMvcConfigurer localeInterceptor() {

        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                LocaleChangeInterceptor localeInterceptor = new LocaleChangeInterceptor();
                localeInterceptor.setParamName(LANG);
                registry.addInterceptor(localeInterceptor);
            }
        };
    }

    /**
     * 自定义国际化文件存放路径
     * @return Validator
     */
    @Bean
    public Validator validator() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.setValidationMessageSource(getMessageResource());
        return validator;
    }
}
