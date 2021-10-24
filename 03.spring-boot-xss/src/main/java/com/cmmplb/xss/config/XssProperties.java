package com.cmmplb.xss.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author penglibo
 * @date 2021-09-10 17:22:27
 * @since jdk 1.8
 */

@Data
@ConfigurationProperties(prefix = "xss")
public class XssProperties {

    /**
     * 是否启用xss过滤,默认false
     */
    private String enabled = "false";

    /**
     * 是否过滤富文本内容
     */
    private String isIncludeRichText = "false";

    /**
     * 需要排除的资源,多个','分割
     */
    private String excludes;

    /**
     * 拦截规则,多个','分割
     */
    private String urlPatterns = "/*";
}
