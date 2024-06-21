package com.cmmplb.springdoc.configuration;

import com.cmmplb.core.constants.StringConstants;
import com.cmmplb.springdoc.configuration.properties.SpringdocProperties;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import lombok.Setter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
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
@ConditionalOnProperty(prefix = SpringdocProperties.PREFIX, name = SpringdocProperties.ENABLED, havingValue = StringConstants.TRUE)
public class SpringdocAutoConfiguration extends OpenAPI implements InitializingBean, ApplicationContextAware {

    @Autowired
    private SpringdocProperties springdocProperties;

    @Setter
    private String path;

    private ApplicationContext applicationContext;

    // @Bean
    // public OpenAPI openApi() {
    //     return new OpenAPI()
    //             .info(new Info()
    //                     .title(springdocProperties.getTitle())
    //                     .description(springdocProperties.getDescription())
    //                     .version(springdocProperties.getVersion())
    //                     .license(new License()
    //                             .name(springdocProperties.getLicense().getName())
    //                             .url(springdocProperties.getLicense().getUrl())
    //                     )
    //                     .contact(new Contact()
    //                             .name(springdocProperties.getContact().getName())
    //                             .url(springdocProperties.getContact().getUrl())
    //                             .email(springdocProperties.getContact().getEmail()))
    //             )
    //             .externalDocs(new ExternalDocumentation()
    //                     .description(springdocProperties.getDescription())
    //                     .url(springdocProperties.getUrl())
    //             );
    // }

    @Override
    public void afterPropertiesSet() throws Exception {
        SpringdocProperties springdocProperties = applicationContext.getBean(SpringdocProperties.class);
        this.info(new Info()
                .title(springdocProperties.getTitle())
                .description(springdocProperties.getDescription())
                .version(springdocProperties.getVersion())
                .license(new License()
                        .name(springdocProperties.getLicense().getName())
                        .url(springdocProperties.getLicense().getUrl())
                ).contact(new Contact()
                        .name(springdocProperties.getContact().getName())
                        .url(springdocProperties.getContact().getUrl())
                        .email(springdocProperties.getContact().getEmail())
                )
        );
        this.externalDocs(new ExternalDocumentation()
                .description(springdocProperties.getDescription())
                .url(springdocProperties.getUrl())
        );
        // oauth2.0 password
        // this.schemaRequirement(HttpHeaders.AUTHORIZATION, this.securityScheme(springdocProperties));
        // servers
        // List<Server> serverList = new ArrayList<>();
        // serverList.add(new Server().url(springdocProperties.getGateway() + "/" + path));
        // this.servers(serverList);
        // 支持参数平铺
        // SpringDocUtils.getConfig().addSimpleTypesForParameterObject(Class.class);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
    //
    // private SecurityScheme securityScheme(SpringdocProperties springdocProperties) {
    //     OAuthFlow clientCredential = new OAuthFlow();
    //     clientCredential.setTokenUrl(springdocProperties.getTokenUrl());
    //     clientCredential.setScopes(new Scopes().addString(springdocProperties.getScope(), springdocProperties.getScope()));
    //     OAuthFlows oauthFlows = new OAuthFlows();
    //     oauthFlows.password(clientCredential);
    //     SecurityScheme securityScheme = new SecurityScheme();
    //     securityScheme.setType(SecurityScheme.Type.OAUTH2);
    //     securityScheme.setFlows(oauthFlows);
    //     return securityScheme;
    // }


}
