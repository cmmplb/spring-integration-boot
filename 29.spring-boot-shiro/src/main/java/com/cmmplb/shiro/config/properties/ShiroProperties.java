package com.cmmplb.shiro.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author penglibo
 * @date 2021-09-28 15:00:48
 * @since jdk 1.8
 */

@Data
@Component
@ConfigurationProperties(prefix = "shiro")
public class ShiroProperties {

    /**
     * 前后端分离
     */
    private Boolean split;

    /**
     * 白名单
     */
    private List<String> whitelist;

}
