# 学习 springboot 整合 webservice

todo:

1. 动态发布多个WS服务
2. 使用JDK自带的 JAX-WS 发布WebService服务，使用CXF发布WEbService服务
3. Jax-ws (webservice) 风格和 Jax-rs (restfull) 风格

webservice 尚硅谷周阳新视频：

````
https://www.bilibili.com/video/BV1xE411d7hY
````

Springboot整合CXF_01基本工程创建：

````
https://www.bilibili.com/video/BV1ca41127Q3
````

Apache CXF WebService框架：

````
https://www.bilibili.com/video/BV1Kz4y1f78f
````

Springboot官网：https://spring.io/projects/spring-boot

CXF官网：http://cxf.apache.org/

官网文档： http://cxf.apache.org/docs/index.html

## 报错信息：

Caused by: java.lang.NoClassDefFoundError: com/sun/xml/internal/bind/api/ErrorListener

看 JaxWsDynamicClientFactory.createClient 源码中发现依赖类不存在，导入依赖：

````xml

<dependencies>
    <!-- JaxWsDynamicClientFactory 使用, 如果是 jakarta.servlet 的话使用最新版本 4.0.5/3.0.2 -->
    <dependency>
        <groupId>com.sun.xml.bind</groupId>
        <artifactId>jaxb-xjc</artifactId>
        <version>2.3.9</version>
    </dependency>
</dependencies>
````

相关报错：

org.apache.cxf.interceptor.Fault: The given SOAPAction http://xxxx does not match an operation.

需要在 @WebMethod 注解中指定 action 参数：

````java
// WebMethod 注解中添加 action 参数：
@WebMethod(action = "http://xxx")
````
