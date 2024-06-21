package com.cmmplb.swagger.configuration;

import org.springframework.context.annotation.Configuration;

/**
 * @author penglibo
 * @date 2024-06-05 14:27:09
 * @since jdk 1.8
 * 报错Failed to start bean 'documentationPluginsBootstrapper'; nested exception is java.lang.NullPointerException
 * 在SpringBoot2.6之后，Spring MVC 处理程序映射匹配请求路径的默认策略已从 AntPathMatcher 更改为PathPatternParser
 * 使用springboot2.6以上或者springboot3建议弃用springfox3
 * 解决方案:
 * 1) 配置文件添加：spring.mvc.pathmatch.matching-strategy: ant_path_matcher
 * 2) 实现以下配置类
 */

@Configuration
public class WebMvcEndpointHandlerMappingConfig {

    // 2.6版本方式一
    // @Bean
    // public WebMvcEndpointHandlerMapping webEndpointServletHandlerMapping(WebEndpointsSupplier webEndpointsSupplier, ServletEndpointsSupplier servletEndpointsSupplier, ControllerEndpointsSupplier controllerEndpointsSupplier, EndpointMediaTypes endpointMediaTypes, CorsEndpointProperties corsProperties, WebEndpointProperties webEndpointProperties, Environment environment) {
    //     List<ExposableEndpoint<?>> allEndpoints = new ArrayList();
    //     Collection<ExposableWebEndpoint> webEndpoints = webEndpointsSupplier.getEndpoints();
    //     allEndpoints.addAll(webEndpoints);
    //     allEndpoints.addAll(servletEndpointsSupplier.getEndpoints());
    //     allEndpoints.addAll(controllerEndpointsSupplier.getEndpoints());
    //     String basePath = webEndpointProperties.getBasePath();
    //     EndpointMapping endpointMapping = new EndpointMapping(basePath);
    //     boolean shouldRegisterLinksMapping = this.shouldRegisterLinksMapping(webEndpointProperties, environment, basePath);
    //     return new WebMvcEndpointHandlerMapping(
    //             endpointMapping,
    //             webEndpoints,
    //             endpointMediaTypes,
    //             corsProperties.toCorsConfiguration(),
    //             // 覆盖掉spring-boot-actuator-autoconfigure jar中默认的WebMvcAutoConfiguration.pathPatternParser
    //             new EndpointLinksResolver(allEndpoints, basePath), shouldRegisterLinksMapping, null);
    // }

    // private boolean shouldRegisterLinksMapping(WebEndpointProperties webEndpointProperties, Environment environment, String basePath) {
    //     return webEndpointProperties.getDiscovery().isEnabled() && (StringUtils.hasText(basePath) || ManagementPortType.get(environment).equals(ManagementPortType.DIFFERENT));
    // }

    // 方式二，这种有问题，会把文档的接口都清空了
    // @Bean
    // public static BeanPostProcessor springfoxHandlerProviderBeanPostProcessor() {
    //     return new BeanPostProcessor() {
    //         @Override
    //         public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    //             if (bean instanceof WebMvcRequestHandlerProvider || bean instanceof WebFluxRequestHandlerProvider) {
    //                 try {
    //                     Field field = ReflectionUtils.findField(bean.getClass(), "handlerMappings");
    //                     field.setAccessible(true);
    //                     customizeSpringfoxHandlerMappings((List<RequestMappingInfoHandlerMapping>) field.get(bean));
    //                 } catch (IllegalArgumentException | IllegalAccessException e) {
    //                     throw new IllegalStateException(e);
    //                 }
    //             }
    //             return bean;
    //         }
    //
    //         private <T extends RequestMappingInfoHandlerMapping> void customizeSpringfoxHandlerMappings(List<T> mappings) {
    //             List<T> copy = mappings.stream()
    //                     .filter(mapping -> mapping.getPatternParser() == null)
    //                     .collect(Collectors.toList());
    //             mappings.clear();
    //             mappings.addAll(copy);
    //         }
    //     };
    // }
}
