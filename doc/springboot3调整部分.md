mybatis-flex

https://mybatis-flex.com/zh/base/add-delete-update.html

## 请求参数

- public Result<String> index(String name),解析不了name

- 默认情况下，任何简单值类型（由 BeanUtils#isSimpleProperty 确定）且未由任何其他参数解析器解析的参数都将被视为使用
  @RequestParam 注解。

- 如果不使用 spring-boot-starter-parent 作为父工程，那么接口中必须显式声明 @RequestParam("name")

- LocalVariableTableParameterNameDiscoverer已在 Spring 6.1 中删除。

解决方案

方案1
在方法参数上添加注解

````
@PathVariable(name=“id”, required = true)
@RequestParam(name=“id”)
````

方案2
编译时保留方法参数名

idea设置：File > Settings > Build, Execution, Deployment > Compiler > Java Compiler > Additional command line parameters:
（输入框中填写 -parameters）

pom文件设置

````xml

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

添加后重新编译代码

**xml**

高版本没有com.sun.xml.internal.bind.v2.ContextFactory导致报错

<jaxb-impl.version>2.3.3</jaxb-impl.version>

## swagger

````
# Failed to start bean 'documentationPluginsBootstrapper'
# 从Spring Boot 2.6开始，默认的匹配策略已经改为PathPatternParser
# AntPathMatcher：这种策略支持通配符如*（匹配任意字符序列）和**（匹配任意深度的目录结构）
#    例如，/api/users/**可以匹配到/api/users/123或/api/users/profile等任何以/api/users/开头的路径。
# PathPatternParser：更高效的路径匹配策略
# 特点和改进：
# 主要特性
# 1、性能提升：PathPatternParser经过优化，通常比AntPathMatcher更快，尤其是在处理大量路由规则时，这归功于其内部更高效的数据结构和算法。
# 2、更丰富的匹配模式：
#       支持命名捕获组（Named Captures），允许直接通过名称引用匹配的部分，而不仅仅是位置序号。
#       提供了更精细的控制选项，如忽略大小写匹配、严格模式匹配等。
#       支持更复杂的路径模式，包括精确匹配、通配符匹配、以及正则表达式风格的匹配规则。
# 3、路径段匹配：它以路径段（path segment）为单位进行匹配，而非简单的字符串比较，这使得匹配逻辑更加清晰和高效。
# 4、路径参数增强：除了基本的路径变量匹配外，还支持更复杂的路径参数模式，例如可选参数、重复参数模式等。
# 5、更直观的错误报告：当路径不匹配时，它可以提供更详细的错误信息，帮助开发者快速定位问题。

spring.mvc.pathmatch.matching-strategy: ant_path_matcher
````

Type javax.servlet.http.HttpServletRequest not present
springboot3整合swagger时，springfox版本过低，相关包已经更换成jakarta.servlet.*
这里单独引入javax.servlet

````
<dependency>
    <groupId>javax.servlet</groupId>
    <artifactId>javax.servlet-api</artifactId>
    <version>4.0.1</version>
    <scope>provided</scope>
</dependency>
````

## mybatis

报错:Invalid value type for attribute 'factoryBeanObjectType': java.lang.String

官方 ISSUE: https://github.com/mybatis/spring/issues/855

我这里引用的mybatis-spring-boot-starter:2.1.4, 对应的mybatis-spring版本是2.0.7

在 mybatis-spring 2.1.1 版本的 ClassPathMapperScanner#processBeanDefinitions 方法里将 BeanClassName 赋值给 String 变量

````java
String beanClassName = definition.getBeanClassName();
````

并将 beanClassName 赋值给 factoryBeanObjectType

````
definition.setAttribute("factoryBeanObjectType",beanClassName);
````

但是在 Spring Boot 3.2 版本中

FactoryBeanRegistrySupport#getTypeForFactoryBeanFromAttributes方法已变更，如果 factoryBeanObjectType 不是 ResolvableType
或 Class 类型会抛出 IllegalArgumentException 异常。

````java
ResolvableType getTypeForFactoryBeanFromAttributes(AttributeAccessor attributes) {
    Object attribute = attributes.getAttribute("factoryBeanObjectType");
    if (attribute == null) {
        return ResolvableType.NONE;
    } else if (attribute instanceof ResolvableType) {
        ResolvableType resolvableType = (ResolvableType) attribute;
        return resolvableType;
    } else if (attribute instanceof Class) {
        Class<?> clazz = (Class) attribute;
        return ResolvableType.forClass(clazz);
    } else {
        throw new IllegalArgumentException("Invalid value type for attribute 'factoryBeanObjectType': " + attribute.getClass().getName());
    }
}
````

此时因为 factoryBeanObjectType 是 String 类型，不符合条件而抛出异常。

升级至3.0.3解决

````
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>3.0.3</version>
</dependency>
````

## mybatis-plus

springboot3使用的依赖

````
<!-- mybatis-plus-spring-boot3 -->
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-spring-boot3-starter</artifactId>
    <version>3.5.7</version>
</dependency>
````

## datajpa

javax包名更改的原因，相关关联的依赖需要添加jakarta

````xml

<dependency>
    <groupId>com.querydsl</groupId>
    <artifactId>querydsl-jpa</artifactId>
    <version>5.1.0</version>
    <classifier>jakarta</classifier>
</dependency>

<dependency>
<groupId>com.querydsl</groupId>
<artifactId>querydsl-apt</artifactId>
<scope>provided</scope>
<optional>true</optional>
<version>5.1.0</version>
<classifier>jakarta</classifier>
</dependency>

        <!-- 插件 -->
<build>
<plugins>
    <!--  querydsl apt plugin, 编译生成QEntity的类  -->
    <plugin>
        <groupId>com.mysema.maven</groupId>
        <artifactId>apt-maven-plugin</artifactId>
        <executions>
            <execution>
                <goals>
                    <goal>process</goal>
                </goals>
                <configuration>
                    <outputDirectory>target/generated-sources/java</outputDirectory>
                    <processor>com.querydsl.apt.jpa.JPAAnnotationProcessor</processor>
                </configuration>
            </execution>
        </executions>
        <dependencies>
            <dependency>
                <groupId>com.querydsl</groupId>
                <artifactId>querydsl-apt</artifactId>
                <version>5.1.0</version>
                <classifier>jakarta</classifier>
            </dependency>
        </dependencies>
    </plugin>
</plugins>
</build>

````

**动态数据源**
发布了springboot3版本

````xml

<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>dynamic-datasource-spring-boot3-starter</artifactId>
    <version>4.3.1</version>
</dependency>
````

**cache**

springboot2的cache2部分整合

EhCache3和Springboot3移除了EhCacheCacheManager

````xml

<dependency>
    <groupId>org.ehcache</groupId>
    <artifactId>ehcache</artifactId>
    <!-- springboot3包含版本号3.10.8 -->
</dependency>
````

缓存类型不再是EhCache了，EhCache3是实现JCache的一种缓存。

spring.cache.type=jcache

同时spring.cache.ehcache.config属性修改为spring.cache.jcache.config

## elasticsearch

从 ElasticSearch 7.0.0 版本开始，ElasticSearch 官方就不建议使用 TransportClient 客户端了，推荐使用 High Level REST Client
客户端了。

从 ElasticSearch 7.15.0 版本开始，ElasticSearch 官方就不建议使用 High Level REST 客户端了，推荐使用 ElasticSearch-java
客户端了。

````
TransportClient 使用内部集群协议进行通信，这会导致以下问题：

集群版本兼容问题：Transport Client 必须与 ElasticSearch 集群的确切版本匹配，如果有版本不匹配则会出现兼容问题，对版本特别敏感。
单点故障：Transport Client 需要连接到特定的节点，如果连接的节点发生故障，客户端则无法自动重新连接到集群的其他节点。
不推荐的跨版本使用： Transport Client 在集群版本升级时可能会遇到问题，因为它需要与集群完全匹配，跨版本使用会引发不兼容性问题，所以使用还不是很灵活。
````

````
<!-- elasticsearch -->
<dependency>
    <!-- 这边匹配的版本是	<elasticsearch-java>8.13.4</elasticsearch-java> -->
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-elasticsearch</artifactId>
</dependency>
<dependency>
    <groupId>co.elastic.clients</groupId>
    <artifactId>elasticsearch-java</artifactId>
    <version>8.13.4</version>
</dependency>
````