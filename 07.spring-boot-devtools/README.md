# 热部署

**坐标**

````xml

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <optional>true</optional>
</dependency>
````

**打包插件**

````xml

<build>
    <plugins>
        <!-- 版本 2.3.12.x 可以不需要此依赖 -->
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

**设置idea自动构建项目**

版本 2.3.12.x 可以不设置这个配置

打开idea设置, 找到【Build, Execution, Deployment】=>【Compiler】=>【勾选【Build project automatically】】自动构建项目