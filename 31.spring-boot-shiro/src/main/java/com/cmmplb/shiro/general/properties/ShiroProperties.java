package com.cmmplb.shiro.general.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * @author penglibo
 * @date 2021-09-28 15:00:48
 * @since jdk 1.8
 */

@Data
@ConfigurationProperties(prefix = ShiroProperties.PREFIX)
public class ShiroProperties {

    /**
     * 前后端分离
     */
    private Boolean split = false;

    /**
     * 是否使用shiro默认配置
     */
    private Boolean general = false;

    /**
     * 白名单
     */
    private List<String> whitelist = new ArrayList<>();

    public static final String PREFIX = "shiro";

    public static final String GENERAL = "general";
}
