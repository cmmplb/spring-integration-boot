# 学习 SpringBoot 整合

spring 官网：https://spring.io/

Spring Cloud Alibaba-logo：https://sca.aliyun.com/

kubernetes：https://kubernetes.io/

spring-boot 和 spring-cloud 版本关系图

![版本关系图.png](doc%2Fimages%2F%E7%89%88%E6%9C%AC%E5%85%B3%E7%B3%BB%E5%9B%BE.png)

master 分支为最新版本 3.3.4.x：

版本从新到旧排列，feature todo：

| 分支       | spring-boot    | spring-cloud | spring-cloud-alibaba | spring-cloud-kubernetes | 完成度 |
|----------|----------------|--------------|----------------------|-------------------------|-----|
| 3.3.4.x  | 3.3.4          | 2023.0.3     | 2023.0.1.2           | 3.1.3                   | ✅   |
| 3.1.12.x | 3.1.12         | 2022.0.5     | 2022.0.0.0           | 3.0.5                   | ❌   |
| 2.7.18.x | 2.7.18         | 2021.0.9     | 2021.0.6.1           | 2.1.9                   | ❌   |
| 2.5.15.x | 2.5.15         | 2020.0.6     | 2021.1               | 2.0.6                   | ✅   |
| 2.3.12.x | 2.3.12.RELEASE | Hoxton.SR12  | 2.2.10-RC2           | 1.1.10.RELEASE          | ❌   |

当前分支整合相关依赖版本

| 依赖                                     | 版本          |
|----------------------------------------|-------------|
| spring-boot                            | 2.5.15      |
| mapstruct                              | 1.5.2.Final |
| jsoup                                  | 1.14.2      |
| springfox-boot-starter                 | 3.0.0       |
| knife4j-spring-boot-starter            | 3.0.3       |
| springdoc-openapi-ui                   | 1.8.0       |
| pagehelper-spring-boot-starter         | 1.3.0       |
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

**参考开源仓库**：

[SpringAll: Spring 系列教程 | https://github.com/wuyouzhuguli/SpringAll (gitee.com)](https://gitee.com/yuhq_git/SpringAll?_from=gitee_search)

[SpringBoot-Learning: Spring Boot基础教程, Spring Boot 2.x版本连载中！！！ (gitee.com)](https://gitee.com/didispace/SpringBoot-Learning)