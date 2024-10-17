package io.github.cmmplb.knife4j.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import io.github.cmmplb.core.utils.StringUtil;
import io.github.cmmplb.knife4j.config.properties.SwaggerProperties;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.*;
import org.springdoc.core.customizers.GlobalOperationCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.method.HandlerMethod;

import java.util.ArrayList;
import java.util.List;

import static org.springdoc.core.utils.Constants.ALL_PATTERN;
import static org.springdoc.core.utils.Constants.DEFAULT_GROUP_NAME;

/**
 * @author penglibo
 * @date 2021-08-04 09:55:24
 * @since jdk 1.8
 */

@Configuration
// 自2.0.6版本后, 只需要在配置文件中配置knife4j.enable=true即可不在使用注解@EnableKnife4j
@EnableKnife4j
@EnableConfigurationProperties(SwaggerProperties.class)
public class Knife4jConfiguration {

    /**
     * 接口添加请求头信息
     * 定义了SecurityScheme.Type.HTTP时, 需要启用这个bean
     * 如果是其他类型, 则不需要
     */
    // @Bean
    public OperationCustomizer operationCustomizer(SwaggerProperties swaggerProperties) {
        return new OperationCustomizer(swaggerProperties);
    }

    /**
     * 基于代码配置分组信息, 对应springdoc.group-configs, 如不配置会有一个默认的分组default, 扫描所有
     */
    @Bean
    public GroupedOpenApi authApi(SwaggerProperties swaggerProperties) {
        GroupedOpenApi.Builder builder = GroupedOpenApi.builder();
        // 组名
        if (StringUtil.isNotBlank(swaggerProperties.getGroupName())) {
            builder.group(swaggerProperties.getGroupName());
        } else {
            builder.group(DEFAULT_GROUP_NAME);
        }
        // 扫描的路径, 支持通配符
        if (!swaggerProperties.getPathsToMatch().isEmpty()) {
            builder.pathsToMatch(swaggerProperties.getPathsToMatch().toArray(new String[0]));
        } else {
            builder.pathsToMatch(ALL_PATTERN);
        }
        // 排除的路径
        if (!swaggerProperties.getPathsToExclude().isEmpty()) {
            builder.pathsToExclude(swaggerProperties.getPathsToExclude().toArray(new String[0]));
        }
        // 扫描的包
        if (!swaggerProperties.getPackagesToScan().isEmpty()) {
            builder.packagesToScan(swaggerProperties.getPackagesToScan().toArray(new String[0]));
        }
        // 排除的包
        if (!swaggerProperties.getPackagesToExclude().isEmpty()) {
            builder.packagesToExclude(swaggerProperties.getPackagesToExclude().toArray(new String[0]));
        }
        // 添加方法操作自定义配置
        SwaggerProperties.Authorization authorization = swaggerProperties.getAuthorization();
        if (null != authorization && SwaggerProperties.Authorization.TypeEnum.PARAM.equals(authorization.getType())) {
            builder.addOperationCustomizer((operation, handlerMethod) ->
                    operation.security(getSecurityRequirements(swaggerProperties)));
        }
        return builder.build();
    }

    @Bean
    public OpenAPI openApi(SwaggerProperties swaggerProperties) {

        OpenAPI info = new OpenAPI();

        info.info(info(swaggerProperties));

        // 鉴权配置
        SwaggerProperties.Authorization authorization = swaggerProperties.getAuthorization();
        if (null != authorization) {
            if (SwaggerProperties.Authorization.TypeEnum.PARAM.equals(authorization.getType())) {
                paramSecurity(authorization, info);

            } else if (!SwaggerProperties.Authorization.TypeEnum.NONE.equals(authorization.getType())) {
                oauth2(swaggerProperties, authorization, info);
            }
        }
        return info;
    }

    private static void paramSecurity(SwaggerProperties.Authorization authorization, OpenAPI info) {
        // 需要在接口层面定义使用：@Operation(security = { @SecurityRequirement(name = HttpHeaders.AUTHORIZATION) })
        // 或者直接添加：@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
        Components components = new Components();
        List<SwaggerProperties.Authorization.Parameter> parameterList = authorization.getParameterList();
        for (SwaggerProperties.Authorization.Parameter parameter : parameterList) {
            components.addSecuritySchemes(parameter.getParameterKeyName(),
                    new SecurityScheme()
                            .name(parameter.getParameterKeyName())
                            .type(SecurityScheme.Type.APIKEY)
                            .in(parameter.getParameterType())
            );
        }
        info.components(components);
    }

    private static void oauth2(SwaggerProperties swaggerProperties, SwaggerProperties.Authorization authorization, OpenAPI info) {
        List<SwaggerProperties.Authorization.AuthorizationScope> authorizationScopeList = authorization.getAuthorizationScopeList();
        Scopes scopes = new Scopes();
        for (SwaggerProperties.Authorization.AuthorizationScope authorizationScope : authorizationScopeList) {
            scopes.addString(authorizationScope.getScope(), authorizationScope.getScope());
        }
        OAuthFlow oAuthFlow = new OAuthFlow();
        oAuthFlow.setAuthorizationUrl(authorization.getAuthorizationUrl());
        oAuthFlow.setTokenUrl(authorization.getTokenUrl());
        oAuthFlow.setRefreshUrl(authorization.getRefreshUrl());
        oAuthFlow.setScopes(scopes);

        OAuthFlows oauthFlows = new OAuthFlows();
        if (SwaggerProperties.Authorization.TypeEnum.IMPLICIT.equals(swaggerProperties.getAuthorization().getType())) {
            // 简化模式
            oauthFlows.setImplicit(oAuthFlow);

        } else if (SwaggerProperties.Authorization.TypeEnum.AUTHORIZATION_CODE.equals(swaggerProperties.getAuthorization().getType())) {
            // 授权码模式
            oauthFlows.setAuthorizationCode(oAuthFlow);

        } else if (SwaggerProperties.Authorization.TypeEnum.PASSWORD.equals(swaggerProperties.getAuthorization().getType())) {
            // 密码模式
            oauthFlows.password(oAuthFlow);

        } else {
            // 客户端模式
            oauthFlows.setClientCredentials(oAuthFlow);
        }
        SecurityScheme securityScheme = new SecurityScheme();
        securityScheme.setType(SecurityScheme.Type.OAUTH2);
        securityScheme.setFlows(oauthFlows);
        info.schemaRequirement(HttpHeaders.AUTHORIZATION, securityScheme);
    }

    private static List<SecurityRequirement> getSecurityRequirements(SwaggerProperties swaggerProperties) {
        SwaggerProperties.Authorization authorization = swaggerProperties.getAuthorization();
        List<SwaggerProperties.Authorization.Parameter> parameterList = authorization.getParameterList();
        List<SecurityRequirement> security = new ArrayList<>();
        SecurityRequirement securityRequirement = new SecurityRequirement();
        for (SwaggerProperties.Authorization.Parameter parameter : parameterList) {
            securityRequirement.addList(parameter.getParameterName());
            security.add(securityRequirement);
        }
        return security;
    }

    private static Info info(SwaggerProperties swaggerProperties) {
        Contact contact = new Contact();
        contact.setEmail(swaggerProperties.getContact().getEmail());
        contact.setName(swaggerProperties.getContact().getName());
        contact.setUrl(swaggerProperties.getContact().getUrl());

        return new Info()
                .title(swaggerProperties.getTitle())
                .version(swaggerProperties.getVersion())
                .contact(contact)
                .description(swaggerProperties.getDescription())
                .termsOfService(swaggerProperties.getTermsOfServiceUrl())
                .license(new License().name(swaggerProperties.getLicense()).url(swaggerProperties.getLicenseUrl()));
    }

    /**
     * 接口添加请求头信息
     * 鉴权配置
     * OpenAPI3 Security在规范中的定义, 分为两部分：
     * 1. 在components组件下定义Security的鉴权方案类型
     * 2. 在接口级别的Operation对象级别下的security属性中引用components组件中定义的Security的鉴权方案类型
     * 所以上面定义好info.components(components);
     * 之后需要在接口层面定义使用：@Operation(security = { @SecurityRequirement(name = HttpHeaders.AUTHORIZATION) })
     * 或者添加：              @SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
     * 为了解决每个接口手动添加的麻烦, 下面配置是统一添加
     */
    public static class OperationCustomizer implements GlobalOperationCustomizer {

        private final SwaggerProperties swaggerProperties;

        public OperationCustomizer(SwaggerProperties swaggerProperties) {
            this.swaggerProperties = swaggerProperties;
        }

        @Override
        public Operation customize(Operation operation, HandlerMethod handlerMethod) {
            List<SecurityRequirement> security = operation.getSecurity();
            // 鉴权配置
            SwaggerProperties.Authorization authorization = swaggerProperties.getAuthorization();
            if (null != authorization && null == security) {
                if (SwaggerProperties.Authorization.TypeEnum.PARAM.equals(authorization.getType())) {
                    operation.setSecurity(getSecurityRequirements(swaggerProperties));
                }
            }
            return operation;
        }
    }
}