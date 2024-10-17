package io.github.cmmplb.swagger.controller;

import io.github.cmmplb.swagger.configuration.SwaggerResourcesProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.swagger.web.*;

import java.util.List;

@RestController
@ApiIgnore
@RequestMapping("/swagger-resources/a")
public class ResourceController {

    @Autowired(required = false)
    private SecurityConfiguration securityConfiguration;
    @Autowired(required = false)
    private UiConfiguration uiConfiguration;

    private final SwaggerResourcesProviderImpl swaggerResources;

    @Autowired
    public ResourceController(
            SwaggerResourcesProviderImpl swaggerResources,
            @Value("${springfox.documentation.swagger-ui.base-url:}") String swaggerUiBaseUrl) {
        this.swaggerResources = swaggerResources;
        this.uiConfiguration = UiConfigurationBuilder.builder()
                .copyOf(uiConfiguration)
                .swaggerUiBaseUrl(StringUtils.trimTrailingCharacter(swaggerUiBaseUrl, '/'))
                .build();
        this.securityConfiguration = SecurityConfigurationBuilder.builder()
                .copyOf(securityConfiguration)
                .build();
    }

    @GetMapping(value = "/configuration/security", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SecurityConfiguration> securityConfiguration() {
        return new ResponseEntity<>(securityConfiguration, HttpStatus.OK);
    }

    @GetMapping(value = "/configuration/ui", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UiConfiguration> uiConfiguration() {
        return new ResponseEntity<>(uiConfiguration, HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SwaggerResource>> swaggerResources() {
        return new ResponseEntity<>(swaggerResources.get(), HttpStatus.OK);
    }
}
