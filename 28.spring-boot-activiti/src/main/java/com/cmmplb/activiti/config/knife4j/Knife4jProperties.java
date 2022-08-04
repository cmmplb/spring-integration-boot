package com.cmmplb.activiti.config.knife4j;

import com.cmmplb.core.constants.StringConstants;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author penglibo
 * @date 2021-08-20 17:58:37
 * @since jdk 1.8
 */

@Data
@ConfigurationProperties(prefix = StringConstants.KNIFE4J)
public class Knife4jProperties {

    /**
     * swagger会解析的包路径
     **/
    private String basePackage = "";

    /**
     * 分组名称
     */
    private String groupName = "";

    /**
     * 标题
     **/
    private String title = "";

    /**
     * 描述
     **/
    private String description = "";

    /**
     * 版本
     **/
    private String version = "";

    /**
     * 许可证
     **/
    private String license = "";

    /**
     * 许可证URL
     **/
    private String licenseUrl = "";

    /**
     * 服务条款URL
     **/
    private String termsOfServiceUrl = "";

    /**
     * host信息
     **/
    private String host = "";

    /**
     * 联系人信息
     */
    private Contact contact = new Contact();

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
}
