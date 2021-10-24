package com.cmmplb.i18n.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author penglibo
 * @date 2021-09-14 11:59:14
 * @since jdk 1.8
 */

@Data
@ConfigurationProperties(prefix = "spring.messages")
public class I18nConfigProperties {

    /**
     * 消息文件路径名称
     */
    private String basename;

    /**
     * 加载资源文件的时间，-1：永不过期，默认：-1
     */
    private Integer cacheSeconds;

    /**
     * 系统默认语言 zh_CN
     */
    private String defaultLanguage;
}
