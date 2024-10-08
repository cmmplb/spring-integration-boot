package com.cmmplb.springdoc.configuration;

import io.github.cmmplb.core.constants.StringConstant;
import com.cmmplb.springdoc.configuration.properties.SpringdocProperties;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.Scopes;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.Setter;
import org.springdoc.core.SpringDocUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

/**
 * @author penglibo
 * @date 2024-05-23 09:40:23
 * @since jdk 1.8
 */

@Configuration
@EnableConfigurationProperties(SpringdocProperties.class)
@ConditionalOnProperty(prefix = SpringdocProperties.PREFIX, name = SpringdocProperties.ENABLED, havingValue = StringConstant.TRUE)
public class SpringdocAutoConfiguration extends OpenAPI implements InitializingBean, ApplicationContextAware {

    @Setter
    private String path;

    private ApplicationContext applicationContext;

    // @Bean
    // public OpenAPI openApi() {
    //     return new OpenAPI()
    //             .info(new Info()
    //                     .title("程序员API")
    //                     .description("程序员的大本营")
    //                     .version("v1.0.0")
    //                     .license(new License()
    //                             .name("许可协议")
    //                             .url("https://shusheng007.top"))
    //                     .contact(new Contact()
    //                             .name("书生007")
    //                             .email("wangben850115@gmail.com")))
    //             .externalDocs(new ExternalDocumentation()
    //                     .description("ShuSheng007博客")
    //                     .url("https://shusheng007.top"));
    // }

    @Override
    public void afterPropertiesSet() throws Exception {
        SpringdocProperties springdocProperties = applicationContext.getBean(SpringdocProperties.class);
        this.info(new Info().title(springdocProperties.getTitle()));
        this.externalDocs(new ExternalDocumentation()
                .description(springdocProperties.getDescription())
                .url(springdocProperties.getUrl()));
        // oauth2.0 password
        // this.schemaRequirement(HttpHeaders.AUTHORIZATION, this.securityScheme(springdocProperties));
        // servers
        // List<Server> serverList = new ArrayList<>();
        // serverList.add(new Server().url(springdocProperties.getGateway() + "/" + path));
        // this.servers(serverList);
        // 支持参数平铺
        SpringDocUtils.getConfig().addSimpleTypesForParameterObject(Class.class);
    }

    private SecurityScheme securityScheme(SpringdocProperties springdocProperties) {
        OAuthFlow clientCredential = new OAuthFlow();
        clientCredential.setTokenUrl(springdocProperties.getTokenUrl());
        clientCredential.setScopes(new Scopes().addString(springdocProperties.getScope(), springdocProperties.getScope()));
        OAuthFlows oauthFlows = new OAuthFlows();
        oauthFlows.password(clientCredential);
        SecurityScheme securityScheme = new SecurityScheme();
        securityScheme.setType(SecurityScheme.Type.OAUTH2);
        securityScheme.setFlows(oauthFlows);
        return securityScheme;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
