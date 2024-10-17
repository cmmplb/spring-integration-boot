# 学习 SpringBoot 整合

spring 官网：https://spring.io/

Spring Cloud Alibaba-logo：https://sca.aliyun.com/

kubernetes：https://kubernetes.io/

spring-boot 和 spring-cloud 版本关系图

![版本关系图.png](doc%2Fimages%2F%E7%89%88%E6%9C%AC%E5%85%B3%E7%B3%BB%E5%9B%BE.png)

master 分支为最新版本 3.3.4.x：

版本从新到旧排列：

| 分支       | spring-boot    | spring-cloud | spring-cloud-alibaba | spring-cloud-kubernetes | 完成度 |
|----------|----------------|--------------|----------------------|-------------------------|-----|
| 3.3.4.x  | 3.3.4          | 2023.0.3     | 2023.0.1.2           | 3.1.3                   | ✅   |
| 3.1.12.x | 3.1.12         | 2022.0.5     | 2022.0.0.0           | 3.0.5                   | ✅   |
| 2.7.18.x | 2.7.18         | 2021.0.9     | 2021.0.6.1           | 2.1.9                   | ✅   |
| 2.5.15.x | 2.5.15         | 2020.0.6     | 2021.1               | 2.0.6                   | ✅   |
| 2.3.12.x | 2.3.12.RELEASE | Hoxton.SR12  | 2.2.10-RC2           | 1.1.10.RELEASE          | ❌   |

当前分支整合相关依赖版本

| 依赖                                     | 版本          |
|----------------------------------------|-------------|
| spring-boot                            | 3.1.12      |
| mapstruct                              | 1.5.2.Final |
| jsoup                                  | 1.14.2      |
| springfox-boot-starter                 | 3.0.0       |
| dynamic-datasource-spring-boot-starter | 3.4.1       | 
| springdoc-openapi-starter-webmvc-ui    | 2.6.0       | 
| pagehelper-spring-boot-starter         | 2.1.0       |
| mybatis-spring-boot-starter            | 2.1.4       |
| tk-mapper                              | 4.1.5       |
| mybatis-plus-boot-starter              | 3.4.3.1     |
| querydsl-jpa                           | 4.4.0       |
| dynamic-datasource-spring-boot-starter | 3.4.1       |
| druid-spring-boot-starter              | 1.2.5       |
| easyexcel                              | 3.3.2       |
| flyway-core                            | 7.7.3       |
| sa-token-spring-boot-starter           | 1.38.0      |
| shiro-spring                           | 1.8.0       |
| sharding-jdbc-spring-boot-starter      | 3.1.0       |
| xxl-job-core                           | 2.3.0       |
| activiti-spring-boot-starter           | 7.1.0.M6    |

**其他版本查看spring-parent.pom中配置的properties**

**TODO:**

````
- mapstruct-plus-spring-boot-starter                  对象模型之间需要相互转换
- mybatis-plus-join-boot-starter                      mybatis-plus 连表查询插件
- mybatis-flex                                        MyBatis 增强框架
- camunda-bpm-spring-boot-starter                     工作流引擎
- AbstractMojo                                        自定义maven插件
````

****

## 版本变化

### 当前分支：feature/3.3.4.x，对比上一个版本 3.1.12.x 的配置变化和问题：

...

## 历史版本变化和问题

### feature/3.1.12.x，对比上一个版本 feature/2.7.18.x 的变化：

****

1. maven.compiler.source 和 maven.compiler.target 升级为 17；

****

2. javax.servlet.* 相关包更改为 jakarta.servlet.*；

****

3. Spring Boot 3 只支持 OpenAPI3 规范，springfox 不再使用，替换为 springdoc，同时移除 swagger 部分注解：

   ````
   @Api → @Tag
   @ApiIgnore → @Parameter(hidden = true) 或 @Operation(hidden = true) 或 @Hidden
   @ApiImplicitParam → @Parameter
   @ApiImplicitParams → @Parameters
   @ApiModel → @Schema
   @ApiModelProperty(hidden = true) → @Schema(accessMode = READ_ONLY)
   @ApiModelProperty → @Schema
   @Operation(summary = “foo”, notes = “bar”) → @Operation(summary = “foo”, description = “bar”)，其中summary为左侧菜单展示的标题，description为右侧接口描述
   @ApiParam → @Parameter
   @ApiResponse(code = 404, message = “foo”) → @ApiResponse(responseCode = “404”, description = “foo”)
   ````

   knife4j ApiSupport 排序不生效问题：在 @Tag 中添加 extensions = {@Extension(properties = {@ExtensionProperty(name = "
   x-order", value = "3", parseValue = true)})}，并且description不能为空，原来的 ApiSort 和 ApiSupport
   用了没效果，看后续版本更新会不会生效。0.0

****

4. 访问接口打印警告：

   ````
   ocalVariableTableParameterNameDiscoverer : Using deprecated '-debug' fallback for parameter name resolution. Compile the affected code with '-parameters' instead or avoid its introspection: ...
   ````

    - public Result<String> index(String name), 解析不了请求参数name

    - 默认情况下，任何简单值类型（由 BeanUtils#isSimpleProperty 确定）且未由任何其他参数解析器解析的参数都将被视为使用
      @RequestParam 注解。

    - 如果不使用 spring-boot-starter-parent 作为父工程，那么接口中必须显式声明 @RequestParam("name")

    - LocalVariableTableParameterNameDiscoverer已在 Spring 6.1 中删除。

   **解决方案**

    - 方案1，在方法参数上添加注解

      ````
      @PathVariable(name=“id”, required = true)
      @RequestParam(name=“id”)
      ````

    - 方案2，编译时保留方法参数名

      idea设置：File > Settings > Build, Execution, Deployment > Compiler > Java Compiler > Additional command line
      parameters: （输入框中填写 -parameters）

    - 方案３，pom文件设置

      ````xml
      <!--　添加后重新编译代码　-->
      <plugin>
          <groupId>org.apache.maven.plugins</groupId>
          <artifactId>maven-compiler-plugin</artifactId>
          <configuration>
              <compilerArgs>
                  <arg>-parameters</arg>
              </compilerArgs>
          </configuration>
      </plugin>
      ````

****

5. Springboot3 移除了 EhCacheCacheManager，缓存类型不再是EhCache了，spring.cache.type=jcache，EhCache3是实现JCache的一种缓存，jcache支持：
   ehcache3 redis等，同时spring.cache.ehcache.config属性修改为spring.cache.jcache.config；

****

6. Mybatis-plus count 查询返回值由 int 改为 long；

****

7. 使用到 JDK 9 及以上相关功能：

    - class.newInstance()方法被标记为过时，使用 clazz.getDeclaredConstructor().newInstance()；
    - Base64.decode 移除，Base64.getDecoder().decode

****

8. JDK 14 引入 @Serial 注解，用于标识 serialVersionUID、readObject、writeObject、readResolve 和 writeReplace 等序列化特殊方法；

****

9. jdk16添加instanceof模式匹配，在进行类型检查时可以直接进行类型转换：

```
if ((e instanceof BusinessException)) {
   BusinessException c = (BusinessException) e;
   if (c.getStatusCode() != 0) {
       setStatusCode(c.getStatusCode());
   }
   return ResultUtil.custom(c.getCode(), c.getMessage());
}

=>

if ((e instanceof BusinessException c)) {
   if (c.getStatusCode() != 0) {
       setStatusCode(c.getStatusCode());
   }
   return ResultUtil.custom(c.getCode(), c.getMessage());
}
```

****

10. 依赖：

**依赖升级：**

| 依赖                             | 之前版本         | 升级版本         | 
|--------------------------------|--------------|--------------|
| easyexcel                      | 3.3.2        | 4.0.3        |    
| fastjson                       | 1.2.76       | 2.0.53       |    
| mybatis                        | 3.5.9        | 3.5.16       |    
| mybatis-spring                 | 2.0.7        | 3.5.16       |    
| mybatis-spring-boot-starter    | 2.1.4        | 3.0.3        |    
| mapper(tk.mybatis)             | 4.1.5        | 5.0.0        |    
| netty-all                      | 4.1.66.Final | 4.2.0.Alpha5 |    
| pagehelper-spring-boot-starter | 1.3.0        | 2.1.0        |    
| querydsl                       | 4.2.1        | 5.1.0        |    
| shiro-spring                   | 1.8.0        | 2.0.1        |    
| shiro-spring-boot-starter      | 1.8.0        | 2.0.1        |    

**备注：** querydsl、shiro依赖和apt-maven-plugin插件需要添加<classifier>jakarta</classifier>

****

**增加依赖：**

| 依赖                                           | 版本     |
|----------------------------------------------|--------|
| dynamic-datasource-spring-boot3-starter      | 4.3.1  |     
| knife4j-openapi3-jakarta-spring-boot-starter | 4.5.0  |     
| mybatis-plus-spring-boot3-starter            | 3.5.8  |     
| jaxb-api                                     | 2.3.1  |     
| ehcache                                      | 3.10.8 |     
| springdoc-openapi-starter-webmvc-ui          | 2.6.0  |     

****

**移除依赖：**

| 依赖                                     | 版本       |
|----------------------------------------|----------|
| dynamic-datasource-spring-boot-starter | 3.4.1    |     
| knife4j-spring-boot-starter            | 3.0.3    |     
| mybatis-plus-boot-starter              | 3.4.3.1  |     
| servlet-api                            | 2.5      |  
| ehcache                                | 2.10.9.2 | 
| springdoc-openapi-ui                   | 1.8.0    |

### feature/2.7.18.x，对比上一个版本 feature/2.5.15.x 的变化：

1. `META-INF/spring.factories` 中 org.springframework.boot.autoconfigure.EnableAutoConfiguration
   配置项改为在`META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports`中声明，不需要使用\，每行一个配置类

***

2. elasticsearch相关类包名更换（其他略）：

   `org.elasticsearch.common.unit.TimeValue` => `org.elasticsearch.core.TimeValue`
   `org.elasticsearch.common.xcontent.XContentBuilder` => `org.elasticsearch.xcontent.XContentBuilder`
   `org.elasticsearch.common.xcontent.XContentFactory` => `org.elasticsearch.xcontent.XContentFactory`
   `org.elasticsearch.common.xcontent.XContentType` => `org.elasticsearch.xcontent.XContentType`

**参考开源仓库**：

[SpringAll: Spring 系列教程 | https://github.com/wuyouzhuguli/SpringAll (gitee.com)](https://gitee.com/yuhq_git/SpringAll?_from=gitee_search)

[SpringBoot-Learning: Spring Boot基础教程, Spring Boot 2.x版本连载中！！！ (gitee.com)](https://gitee.com/didispace/SpringBoot-Learning)