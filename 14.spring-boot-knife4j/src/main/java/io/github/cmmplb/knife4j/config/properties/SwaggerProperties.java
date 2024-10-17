package io.github.cmmplb.knife4j.config.properties;

import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * @author penglibo
 * @date 2021-10-29 14:12:13
 * @since jdk 1.8
 */

@Data
@ConfigurationProperties(SwaggerProperties.PREFIX)
public class SwaggerProperties {

    /**
     * 是否开启swagger
     */
    private Boolean enabled = false;

    /**
     * swagger页面内的请求的接口前缀
     **/
    private String pathMapping = "";

    /**
     * 解析的包路径集合, 为空扫描全部
     **/
    private List<String> packagesToScan = new ArrayList<>();

    /**
     * 排除的包路径集合
     */
    private List<String> packagesToExclude = new ArrayList<>();

    /**
     * 解析的url规则, 支持通配符/**
     **/
    private List<String> pathsToMatch = new ArrayList<>();

    /**
     * 排除的url规则, 支持通配符/**
     **/
    private List<String> pathsToExclude = new ArrayList<>();

    /**
     * 需要排除的服务
     */
    private List<String> ignoreProviders = new ArrayList<>();

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

    /**
     * 全局统一鉴权配置
     **/
    private Authorization authorization;

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
    public static class Authorization {

        /**
         * 认证类型-默认关闭-开启在左侧菜单显示认证
         * none-无认证
         * param-param模式-需要配置parameterList
         * implicit-简化模式
         * authorization_code-授权码模式
         * password-oauth2-密码模式
         * client_credentials-oauth2-客户端模式
         */
        private TypeEnum type = TypeEnum.NONE;

        /**
         * param模式参数
         */
        private List<Parameter> parameterList = new ArrayList<>();

        /**
         * 认证url
         */
        private String authorizationUrl;

        /**
         * 权限认证调用服务api
         */
        private String tokenUrl;

        /**
         * 刷新令牌url
         */
        private String refreshUrl;

        /**
         * 鉴权作用域列表
         */
        private List<AuthorizationScope> authorizationScopeList = new ArrayList<>();

        @Getter
        @AllArgsConstructor
        public enum TypeEnum {
            /**
             * 无认证
             */
            NONE,
            /**
             * param模式-需要配置Parameter
             */
            PARAM,
            /**
             * 简化模式
             */
            IMPLICIT,
            /**
             * 授权码模式
             */
            AUTHORIZATION_CODE,
            /**
             * oauth2-密码模式
             */
            PASSWORD,
            /**
             * client_credentials：oauth2-客户端模式
             */
            CLIENT_CREDENTIALS;
        }

        @Data
        public static class Parameter {

            /**
             * 参数key
             */
            private String parameterKeyName = "";

            /**
             * 参数名称
             */
            private String parameterName = "";

            /**
             * 参数类型：query,header,path,cookie,form,formData,body
             */
            private SecurityScheme.In parameterType = SecurityScheme.In.HEADER;
        }

        @Data
        public static class AuthorizationScope {

            /**
             * 作用域范围
             */
            private String scope = "";

            /**
             * 作用域描述
             */
            private String description = "";
        }
    }

    public static final String PREFIX = "swagger";

    public static final String ENABLED = "enabled";
}