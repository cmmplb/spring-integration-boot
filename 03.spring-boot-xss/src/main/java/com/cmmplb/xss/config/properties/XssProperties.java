package com.cmmplb.xss.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author penglibo
 * @date 2021-09-10 17:22:27
 * @since jdk 1.8
 */

@Data
@ConfigurationProperties(prefix = XssProperties.COL_PREFIX_XXL)
public class XssProperties {

    /**
     * 是否启用xss过滤,默认false
     */
    private Boolean enabled = false;

    /**
     * 是否过滤富文本内容
     */
    private Boolean isIncludeRichText = false;

    /**
     * 需要排除的资源,多个','分割
     */
    private String excludes;

    /**
     * 拦截规则,多个','分割
     */
    private String urlPatterns = "/*";


    public static final String COL_PREFIX_XXL = "xss";

    public static final String COL_ENABLED = "enabled";

    public static final String COL_IS_INCLUDE_RICH_TEXT = "isIncludeRichText";

    public static final String COL_EXCLUDES = "excludes";
}
