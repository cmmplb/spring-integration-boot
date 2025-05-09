# 整合swagger3

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

- 配置方法Docket中DocumentationType版本改变, 变更为DocumentationType.OAS_30

- 访问地址变更, 从之前的http://localhost:8080/swagger-ui.html变更为http://localhost:8080/swagger-ui/index.html

- 重写WebMvcConfigurer类中的addResourceHandlers接口

