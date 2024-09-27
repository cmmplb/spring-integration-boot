/*
 *
 *  Copyright 2017-2018 the original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *
 */

package com.cmmplb.swagger.controller;

import io.swagger.models.Swagger;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UrlPathHelper;
import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.service.Documentation;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.DocumentationCache;
import springfox.documentation.spring.web.OnServletBasedWebApplication;
import springfox.documentation.spring.web.json.Json;
import springfox.documentation.spring.web.json.JsonSerializer;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.web.WebMvcSwaggerTransformationFilter;

import java.util.List;

import static java.util.Optional.ofNullable;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;
import static org.springframework.util.StringUtils.hasText;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromContextPath;

@ApiIgnore
@RestController
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@RequestMapping("/v2/api-docs/a")
@Conditional(OnServletBasedWebApplication.class)
@Order(Ordered.HIGHEST_PRECEDENCE)
public class Swagger2ControllerWebMvcA {
    public static final String SWAGGER2_SPECIFICATION_PATH = "/v2/api-docs";

    private static final Logger LOGGER = LoggerFactory.getLogger(Swagger2ControllerWebMvcA.class);
    private static final String HAL_MEDIA_TYPE = "application/hal+json";
    private final DocumentationCache documentationCache;
    private final JsonSerializer jsonSerializer;
    private final PluginRegistry<WebMvcSwaggerTransformationFilter, DocumentationType> transformations;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    public Swagger2ControllerWebMvcA(
            DocumentationCache documentationCache,
            JsonSerializer jsonSerializer,
            @Qualifier("webMvcSwaggerTransformationFilterRegistry")
            PluginRegistry<WebMvcSwaggerTransformationFilter, DocumentationType> transformations) {
        this.documentationCache = documentationCache;
        this.jsonSerializer = jsonSerializer;
        this.transformations = transformations;
    }

    @RequestMapping(
            method = RequestMethod.GET,
            produces = {APPLICATION_JSON_VALUE, HAL_MEDIA_TYPE})
    public ResponseEntity<Json> getDocumentation(
            @RequestParam(value = "group", required = false) String swaggerGroup) {

        String groupName = ofNullable(swaggerGroup).orElse(Docket.DEFAULT_GROUP_NAME);
        Documentation documentation = documentationCache.documentationByGroup(groupName);
        if (documentation == null) {
            LOGGER.warn("Unable to find specification for group {}", groupName);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Swagger swagger = new ServiceModelToSwagger2MapperA().mapDocumentation(documentation);
        SwaggerTransformationContext<HttpServletRequest> context = new SwaggerTransformationContext<>(swagger, httpServletRequest);
        List<WebMvcSwaggerTransformationFilter> filters = transformations.getPluginsFor(DocumentationType.SWAGGER_2);
        for (WebMvcSwaggerTransformationFilter each : filters) {
            Swagger specification = context.getSpecification();
            context = context.next(specification);
        }
        return new ResponseEntity<>(jsonSerializer.toJson(context.getSpecification()), HttpStatus.OK);
    }

    private String hostName(
            UriComponents uriComponents,
            String hostNameOverride) {
        if ("DEFAULT".equals(hostNameOverride)) {
            String host = uriComponents.getHost();
            int port = uriComponents.getPort();
            if (port > -1) {
                return String.format("%s:%d", host, port);
            }
            return host;
        }
        return hostNameOverride;
    }

    private static ServletUriComponentsBuilder fromServletMapping(
            HttpServletRequest request,
            String basePath) {

        ServletUriComponentsBuilder builder = fromContextPath(request);

        XForwardPrefixPathAdjuster adjuster = new XForwardPrefixPathAdjuster(request);
        String adjustedPath = adjuster.adjustedPath(basePath);
        if (!adjustedPath.equals(basePath)) {
            builder.replacePath(adjustedPath);
        }
        if (hasText(new UrlPathHelper().getPathWithinServletMapping(request))) {
            builder.path(request.getServletPath());
        }

        return builder;
    }
}
