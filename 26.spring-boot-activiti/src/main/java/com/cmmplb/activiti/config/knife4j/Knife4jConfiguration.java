package com.cmmplb.activiti.config.knife4j;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author penglibo
 * @date 2021-08-20 17:55:54
 * @since jdk 1.8
 */

@Configuration
@EnableKnife4j // 开启增强功能
@EnableConfigurationProperties(Knife4jProperties.class)
public class Knife4jConfiguration {

    @Autowired
    private Knife4jProperties knife4jProperties;

    @Bean
    public Docket defaultApi2() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(new ApiInfoBuilder()
                        .title(knife4jProperties.getTitle())
                        .description(knife4jProperties.getDescription())
                        .license(knife4jProperties.getLicense())
                        .licenseUrl(knife4jProperties.getLicenseUrl())
                        .termsOfServiceUrl(knife4jProperties.getTermsOfServiceUrl())
                        .contact(new Contact(knife4jProperties.getContact().getName(), knife4jProperties.getContact().getUrl(), knife4jProperties.getContact().getEmail()))
                        .version(knife4jProperties.getVersion())
                        .build())
                //分组名称
                .groupName(knife4jProperties.getGroupName())
                .host(knife4jProperties.getHost()) //注意这里的主机名：端口是网关的地址和端口
                .select()
                // 此包路径下的类，才生成接口文档 （文件的包不同）
                .apis(RequestHandlerSelectors.basePackage(knife4jProperties.getBasePackage()))
                // 加了ApiOperation注解的类，才生成接口文档
                //.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();
    }
}
