package com.cmmplb.swagger.configuration;

import io.github.cmmplb.core.constants.StringConstant;
import io.github.cmmplb.core.exception.ConfigurationException;
import io.github.cmmplb.core.utils.StringUtil;
import com.cmmplb.swagger.configuration.properties.SwaggerProperties;
import com.google.common.collect.Lists;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.util.CollectionUtils;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.schema.ScalarType;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author penglibo
 * @date 2024-05-23 09:40:23
 * @since jdk 1.8
 * 官方文档3.0版本@see {https://github.com/OAI/OpenAPI-Specification/blob/main/versions/3.0.3.md}
 * 禁用方法1：使用注解@Profile({"dev","test"}) 表示在开发或测试环境开启，而在生产关闭。（推荐使用）
 * 禁用方法2：使用注解@ConditionalOnProperty(name = "swagger.enable",havingValue = "true")
 * 在测试配置或者开发配置中添加swagger.enable=true即可开启，生产环境不填则默认关闭Swagger
 * matchIfMissing属性：从application.properties中读取某个属性值，如果该值为空，默认值为true
 * 在spring.factories也配置了
 */

@Configuration
@EnableOpenApi
@Profile({"local", "dev", "uat"})
@EnableConfigurationProperties({SwaggerProperties.class})
@ConditionalOnProperty(prefix = SwaggerProperties.PREFIX, name = SwaggerProperties.ENABLED, havingValue = StringConstant.TRUE)
public class SwaggerAutoConfiguration {

    /**
     * 不显示错误的接口地址->basic-error-controller
     * 默认的排除路径，排除Spring Boot默认的错误处理路径和端点
     */
    private static final List<String> DEFAULT_EXCLUDE_PATH = Arrays.asList("/error", "/actuator/**");
    private static final String OAUTH = "oauth";
    private static final String BASE_PATH = "/**";
    private static final String TOKEN_NAME = "access_token";

    @Bean
    public Docket api(SwaggerProperties swaggerProperties) {
        // base-path处理
        if (swaggerProperties.getBasePath().isEmpty()) {
            swaggerProperties.getBasePath().add(BASE_PATH);
        }
        // exclude-path处理
        if (swaggerProperties.getExcludePath().isEmpty()) {
            swaggerProperties.getExcludePath().addAll(DEFAULT_EXCLUDE_PATH);
        }
        Docket doc = new Docket(DocumentationType.OAS_30)
                // 配置开启swagger
                .enable(swaggerProperties.getEnabled())
                // swagger页面内的请求的接口前缀
                .pathMapping(swaggerProperties.getPathMapping())
                .host(swaggerProperties.getHost())
                .apiInfo(apiInfo(swaggerProperties));

        // 添加请求头
        if (!CollectionUtils.isEmpty(swaggerProperties.getHeaderList())) {
            List<RequestParameter> requestParameters = new ArrayList<>();
            RequestParameterBuilder parameterBuilder = new RequestParameterBuilder();
            for (SwaggerProperties.Header header : swaggerProperties.getHeaderList()) {
                parameterBuilder.in(ParameterType.HEADER).name(header.getName()).description(header.getDescription())
                        .query(param -> param.defaultValue(header.getDefaultValue()).allowEmptyValue(header.getAllowEmptyValue())
                                .model(model -> model.scalarModel(ScalarType.STRING)));
                requestParameters.add(parameterBuilder.build());
            }
            // 添加header
            doc.globalRequestParameters(requestParameters);
        }
        if (StringUtil.isNotEmpty(swaggerProperties.getGroupName())) {
            doc.groupName(swaggerProperties.getGroupName());
        }
        ApiSelectorBuilder builder = doc.select();
        // Api规则配置
        if (StringUtil.isNotEmpty(swaggerProperties.getBasePackage())) {
            // 设定扫描那个包（及子包）中的注解
            builder.apis(RequestHandlerSelectors.basePackage(swaggerProperties.getBasePackage()));
        } else {
            builder
                    .apis(RequestHandlerSelectors.any())
                    // 使用正则表达式匹配,约束生成Api文档的路径地址,.代表任意字符串，*代表 0~n个任意字符
                    // .paths(PathSelectors.regex("/xxx/.*"))
                    // 加了ApiOperation注解的类，才生成接口文档
                    //.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                    // 扫描所有
                    .paths(PathSelectors.any());
        }
        swaggerProperties.getBasePath().forEach(p -> builder.paths(PathSelectors.ant(p)));
        swaggerProperties.getExcludePath().forEach(p -> builder.paths(PathSelectors.ant(p).negate()));

        // 鉴权配置
        SwaggerProperties.Authorization authorization = swaggerProperties.getAuthorization();

        Docket docket = builder.build();

        if (null != authorization) {
            docket.securityContexts(securityContext(swaggerProperties));
            if (SwaggerProperties.Authorization.TypeEnum.PARAM.equals(authorization.getType())) {
                docket.securitySchemes(Lists.newArrayList(paramSecuritySchemes(swaggerProperties)));
            } else if (!SwaggerProperties.Authorization.TypeEnum.NONE.equals(authorization.getType())) {
                docket.securitySchemes(Collections.singletonList(oauthSecuritySchema(swaggerProperties)));
            }
        }
        return docket;
    }

    /**
     * param模式权限参数配置
     */
    private List<ApiKey> paramSecuritySchemes(SwaggerProperties swaggerProperties) {
        if (CollectionUtils.isEmpty(swaggerProperties.getAuthorization().getParameterList())) {
            throw new ConfigurationException("请设置认证类型为param模式对应的parameterList");
        }
        return swaggerProperties.getAuthorization().getParameterList().stream().map(parameter ->
                new ApiKey(parameter.getParameterName(), parameter.getParameterKeyName(), parameter.getParameterType())
        ).collect(Collectors.toList());
    }

    /**
     * Oauth权限配置
     */
    private static OAuth oauthSecuritySchema(SwaggerProperties swaggerProperties) {
        ArrayList<AuthorizationScope> authorizationScopeList = new ArrayList<>();
        swaggerProperties.getAuthorization().getAuthorizationScopeList()
                .forEach(authorizationScope -> authorizationScopeList.add(
                        new AuthorizationScope(authorizationScope.getScope(), authorizationScope.getDescription()))
                );
        ArrayList<GrantType> grantTypes = new ArrayList<>();
        swaggerProperties.getAuthorization().getTokenUrlList()
                // 权限认证调用服务的 Api,如：http://xxx/xxx/login
                .forEach(tokenUrl -> {
                    if (SwaggerProperties.Authorization.TypeEnum.IMPLICIT.equals(swaggerProperties.getAuthorization().getType())) {
                        // 简化模式
                        grantTypes.add(new ImplicitGrant(new LoginEndpoint(swaggerProperties.getAuthorization().getClient().getUrl()), TOKEN_NAME));

                    } else if (SwaggerProperties.Authorization.TypeEnum.AUTHORIZATION_CODE.equals(swaggerProperties.getAuthorization().getType())) {
                        // 授权码模式
                        TokenRequestEndpoint tokenRequestEndpoint = new TokenRequestEndpoint(
                                swaggerProperties.getAuthorization().getClient().getUrl(),
                                swaggerProperties.getAuthorization().getClient().getClientId(),
                                swaggerProperties.getAuthorization().getClient().getClientSecret());
                        grantTypes.add(new AuthorizationCodeGrant(tokenRequestEndpoint, new TokenEndpoint(tokenUrl, TOKEN_NAME)));

                    } else if (SwaggerProperties.Authorization.TypeEnum.PASSWORD.equals(swaggerProperties.getAuthorization().getType())) {
                        // 密码模式
                        grantTypes.add(new ResourceOwnerPasswordCredentialsGrant(tokenUrl));

                    } else {
                        // 客户端模式
                        grantTypes.add(new ClientCredentialsGrant(tokenUrl));
                    }
                });
        return new OAuth(swaggerProperties.getAuthorization().getName(), authorizationScopeList, grantTypes);
    }

    /**
     * 配置默认的全局鉴权策略的开关，通过正则表达式进行匹配；默认匹配所有URL
     * @return SecurityContext
     */
    private static List<SecurityContext> securityContext(SwaggerProperties swaggerProperties) {
        return Collections
                .singletonList(SecurityContext.builder()
                        // .operationSelector(operationContext -> true) // operationContext.requestMappingPattern().startsWith(OAUTH))
                        .securityReferences(defaultAuth(swaggerProperties))
                        // 所有包含"auth"的接口不需要使用securitySchemes
                        // .operationSelector(o -> o.requestMappingPattern().matches("^(?!auth).*$"))
                        .build());
    }

    /**
     * 默认的全局鉴权策略
     * @return List<SecurityReference>
     */
    private static List<SecurityReference> defaultAuth(SwaggerProperties swaggerProperties) {
        ArrayList<AuthorizationScope> authorizationScopeList = new ArrayList<>();
        swaggerProperties.getAuthorization().getAuthorizationScopeList()
                .forEach(authorizationScope -> authorizationScopeList.add(
                        new AuthorizationScope(authorizationScope.getScope(), authorizationScope.getDescription())));
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[authorizationScopeList.size()];
        if (SwaggerProperties.Authorization.TypeEnum.PARAM.equals(swaggerProperties.getAuthorization().getType())) {
            return swaggerProperties.getAuthorization().getParameterList().stream().map(p -> SecurityReference.builder().reference(p.getParameterName())
                    .scopes(authorizationScopeList.toArray(authorizationScopes)).build()).collect(Collectors.toList());
        } else {
            return Collections
                    .singletonList(SecurityReference.builder().reference(swaggerProperties.getAuthorization().getName())
                            .scopes(authorizationScopeList.toArray(authorizationScopes)).build());
        }
    }

    private static ApiInfo apiInfo(SwaggerProperties swaggerProperties) {
        return new ApiInfoBuilder()
                .title(swaggerProperties.getTitle())
                .description(swaggerProperties.getDescription())
                .license(swaggerProperties.getLicense())
                .licenseUrl(swaggerProperties.getLicenseUrl())
                .termsOfServiceUrl(swaggerProperties.getTermsOfServiceUrl())
                .contact(new Contact(swaggerProperties.getContact().getName(), swaggerProperties.getContact().getUrl(), swaggerProperties.getContact().getEmail()))
                .version(swaggerProperties.getVersion())
                .build();
    }
}
