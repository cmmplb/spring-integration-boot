package com.cmmplb.i18n.config;

import com.cmmplb.core.constants.StringConstant;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.lang.NonNull;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.Arrays;
import java.util.Locale;

/**
 * @author penglibo
 * @date 2021-09-14 11:58:58
 * @since jdk 1.8
 */

@Configuration
@EnableConfigurationProperties(MessageSourceProperties.class)
@ConditionalOnProperty(prefix = MessageSourceProperties.PREFIX, name = MessageSourceProperties.ENABLED, havingValue = StringConstant.TRUE)
public class MessageSourceConfig {

    private final static String LANG = "lang";

    public final static String MESSAGE_SOURCE = "messageSource";

    @Autowired
    private MessageSourceProperties i18nProperties;

    @Bean(name = MESSAGE_SOURCE)
    public MessageSource getMessageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setCacheSeconds(i18nProperties.getCacheSeconds());
        messageSource.setDefaultEncoding(i18nProperties.getEncoding().getCharset());
        messageSource.getBasenameSet().addAll(Arrays.asList(i18nProperties.getBasename().split(",")));
        return messageSource;
    }

    /**
     * 默认解析器 其中locale表示默认语言,当请求中未包含语种信息, 则设置默认语种
     */
    @Bean
    public LocaleResolver localeResolver() {
        // AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver(); // 报错-java.lang.UnsupportedOperationException: Cannot change HTTP accept header - use a different locale resolution strategy
        // localeResolver.setSupportedLocales(Arrays.asList(Locale.US, Locale.SIMPLIFIED_CHINESE));
        CookieLocaleResolver localeResolver = new CookieLocaleResolver();
        localeResolver.setDefaultLocale(locale());
        return localeResolver;
    }

    @Bean
    public Locale locale() {
        String[] i18nArray;
        if (null != i18nProperties && null != i18nProperties.getDefaultLanguage()) {
            String lang = i18nProperties.getDefaultLanguage().getLang();
            String country = i18nProperties.getDefaultLanguage().getCountry();
            i18nArray = new String[]{lang, country};
        } else {
            // 设置默认语言
            MessageSourceProperties.Language simplifiedChinese = MessageSourceProperties.Language.SIMPLIFIED_CHINESE;
            i18nArray = new String[]{simplifiedChinese.getLang(), simplifiedChinese.getCountry()};
        }
        return new Locale(i18nArray[0], i18nArray[1]);
    }

    /**
     * 默认拦截器 其中lang表示切换语言的参数名
     * 拦截请求, 获取请求参数lang种包含的语种信息并重新注册语种信息
     */
    @Bean
    public WebMvcConfigurer localeInterceptor() {
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(@NonNull InterceptorRegistry registry) {
                LocaleChangeInterceptor localeInterceptor = new LocaleChangeInterceptor();
                localeInterceptor.setParamName(LANG);
                registry.addInterceptor(localeInterceptor);
            }
        };
    }

    /**
     * 自定义国际化文件存放路径,这个需要单独放到具体服务中, 配置到模块中会读取当前classPath目录下的多语言文件
     * 无法读取到引入依赖的模块,这里采用全局异常, 解析@NotBlank(message="{validate}")参数中的validate,
     * 单独调用MessageUtil.getMessage(validate)来响应, 所以这个bean在跨模块中会失效
     * @return Validator
     */
    @Bean
    @Primary
    public Validator validator() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.setValidationMessageSource(getMessageSource());
        return validator;
    }
}
