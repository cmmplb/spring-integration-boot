# 热部署

**坐标**

````xml

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <optional>true</optional>
</dependency>
````

**设置idea自动构建项目**

打开idea设置, 找到【Build, Execution, Deployment】=>【Compiler】=>【勾选【Build project automatically】】自动构建项目

**参与热部署监控的文件范围配置**

````
- /META-INF/maven
- /META-INF/resources
- /resources
- /static
- /public
- /templates
````

````yml
spring:
  devtools:
    restart:
      # 设置不参与热部署的文件或文件夹
      exclude: static/**,public/**,config/application.yml
````

**关闭热部署**

我这个改了这个不生效, 发现热部署还在。

````
spring:
  devtools:
    restart:
      enabled: false
````

配置文件层级过多导致相符覆盖最终引起配置失效, 可以提高配置的层级, 在更高层级中配置关闭热部署。例如在启动容器前通过系统属性设置关闭热部署功能。

````
System.setProperty("spring.devtools.restart.enabled","false");
````

**其他问题**

快捷键 ctrl + shift + alt + /, 选择Registry, 勾选 Compiler auto Make allow when app running

````xml

<build>
    <plugins>

        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
            <configuration>
                <!-- 如果没有该项配置, devtools没有作用 -->
                <fork>true</fork>
            </configuration>
        </plugin>
    </plugins>
</build>
````