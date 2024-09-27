# 整合swagger3

springboot3.0不再支持springfox

**依赖**

````
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-boot-starter</artifactId>
    <version>3.0.0</version>
</dependency>
````

**版本差异**

- 由 @EnableSwagger2 改为 @EnableOpenApi

- 配置方法Docket中DocumentationType版本改变，变更为DocumentationType.OAS_30

- 访问地址变更，从之前的http://localhost:8080/swagger-ui.html变更为http://localhost:8080/swagger-ui/index.html

- 重写WebMvcConfigurer类中的addResourceHandlers接口

springfox.boot.starter.autoconfigure.SwaggerUiWebMvcConfigurer对请求路径做了处理：

````java
public class SwaggerUiWebMvcConfigurer implements WebMvcConfigurer {
    private final String baseUrl;

    public SwaggerUiWebMvcConfigurer(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String baseUrl = StringUtils.trimTrailingCharacter(this.baseUrl, '/');
        registry.addResourceHandler(new String[]{baseUrl + "/swagger-ui/**"}).addResourceLocations(new String[]{"classpath:/META-INF/resources/webjars/springfox-swagger-ui/"}).resourceChain(false);
    }

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController(this.baseUrl + "/swagger-ui/").setViewName("forward:" + this.baseUrl + "/swagger-ui/index.html");
    }
}
````