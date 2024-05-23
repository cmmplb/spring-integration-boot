package com.cmmplb.i18n.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author penglibo
 * @date 2021-09-14 11:59:14
 * @since jdk 1.8
 * 国际化配置类
 */

@Data
@ConfigurationProperties(prefix = MessageSourceProperties.PREFIX)
public class MessageSourceProperties {

    /**
     * 是否使用消息配置,默认false
     */
    private boolean enabled = false;

    /**
     * 消息包的编码，默认：UTF-8
     */
    private Encoding encoding = Encoding.UTF_8;

    /**
     * 消息文件路径名称
     */
    private String basename = "i18n/messages,i18n/validations";

    /**
     * 加载资源文件的时间，-1：永不过期，默认：-1
     */
    private Integer cacheSeconds = -1;

    /**
     * 系统默认语言 zh_CN
     */
    private Language defaultLanguage = Language.SIMPLIFIED_CHINESE;

    /**
     * 消息包编码枚举
     */
    @Getter
    @AllArgsConstructor
    public enum Encoding {
        /**
         * 编码类型
         */
        US_ASCII("US-ASCII"),
        ISO_8859_1("ISO-8859-1"),
        UTF_8("UTF-8"),
        UTF_16BE("UTF-16BE"),
        UTF_16LE("UTF-16LE"),
        UTF_16("UTF-16");

        private final String charset;
    }

    /**
     * 语言枚举
     */
    @Getter
    @AllArgsConstructor
    public enum Language {
        /**
         * 简体中文
         */
        SIMPLIFIED_CHINESE("zh", "CN"),
        /**
         * 繁体中文
         */
        TRADITIONAL_CHINESE("zh", "TW"),
        FRANCE("fr", "FR"),
        GERMANY("de", "DE"),
        ITALY("it", "IT"),
        JAPAN("ja", "JP"),
        KOREA("ko", "KR"),
        UK("en", "GB"),
        US("en", "US"),
        CANADA("en", "CA"),
        CANADA_FRENCH("fr", "CA");

        private final String lang;
        private final String country;
    }

    public static final String PREFIX = "message";

    public static final String ENABLED = "enabled";

}
