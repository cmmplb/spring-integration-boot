package com.cmmplb.springdoc.configuration.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * @author penglibo
 * @date 2024-05-23 17:29:46
 * @since jdk 1.8
 */

@Data
@ConfigurationProperties(prefix = SpringdocProperties.PREFIX)
public class SpringdocProperties {

    /**
     * 是否开启swagger
     */
    private Boolean enabled = true;

    /**
     * swagger会解析的包路径
     **/
    private String basePackage = "";

    /**
     * swagger会解析的url规则
     **/
    private List<String> basePath = new ArrayList<>();

    /**
     * 在basePath基础上需要排除的url规则
     **/
    private List<String> excludePath = new ArrayList<>();

    /**
     * 需要排除的服务
     */
    private List<String> ignoreProviders = new ArrayList<>();

    /**
     * 标题
     **/
    private String title = "";

    /**
     * 版本
     */
    private String version;

    /**
     * 许可
     */
    private License license;

    /**
     * 联系人信息
     */
    private Contact contact;

    /**
     * 文档扩展描述
     */
    private String description;

    @Data
    public static class Contact {
        /**
         * 联系人
         **/
        private String name = "";
        /**
         * 联系人url
         **/
        private String url = "";
        /**
         * 联系人email
         **/
        private String email = "";
    }

    @Data
    public static class License {

        /**
         * 许可协议
         **/
        private String name = "";
        /**
         * 许可协议url
         **/
        private String url = "";
    }

    /**
     * 文档扩展url
     */
    private String url;

    public static final String PREFIX = "springdoc.custom";

    public static final String ENABLED = "enabled";
}
