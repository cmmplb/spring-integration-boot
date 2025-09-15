# Logback.xml 配置详解

## 基本结构

````xml

<configuration>
    <!-- Appenders -->
    <appender name="APPENDER_NAME" class="APPENDER_CLASS">
        <!-- Appender-specific configurations -->
    </appender>

    <!-- Loggers -->
    <logger name="LOGGER_NAME" level="LOG_LEVEL">
        <appender-ref ref="APPENDER_NAME"/>
    </logger>

    <!-- Root Logger -->
    <root level="LOG_LEVEL">
        <appender-ref ref="APPENDER_NAME"/>
    </root>
</configuration>
````

### Appenders

Appender 用于定义日志的输出目标，Logback 支持多种类型的 appender，如控制台、文件、数据库等。

- ConsoleAppender：将日志输出到控制台。

    ````xml
    
    <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>
    ````

- FileAppender：将日志输出到文件。

  ````xml
  
  <appender name="FILE" class="ch.qos.logback.core.FileAppender">
      <file>logs/app.log</file>
      <append>true</append> <!-- 是否追加到文件末尾 -->
      <encoder>
          <pattern>%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n</pattern>
      </encoder>
  </appender>
  ````

- RollingFileAppender：将日志输出到滚动文件，支持按大小或时间滚动。

  ````xml
  
  <appender name="ROLLING" class="ch.qos.logback.core.rolling.RollingFileAppender">
      <file>logs/app.log</file>
      <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
          <fileNamePattern>logs/app.%d{yyyy-MM-dd}.log</fileNamePattern>
          <maxHistory>30</maxHistory> <!-- 保留最近30天的日志文件 -->
      </rollingPolicy>
      <encoder>
          <pattern>%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n</pattern>
      </encoder>
  </appender>
  ````

FileAppender 和 RollingFileAppender 的区别：

````
特性	              FileAppender	            RollingFileAppender
文件管理	          单一文件持续写入	            自动按策略分割、归档旧日志
文件大小控制	      无，文件会无限增长	        支持按大小（如 50MB）或时间（如每天）滚动
历史日志处理	      需手动清理	                自动删除过期日志（如保留最近 30 天）
典型场景	          临时调试、小应用日志	        生产环境、需要长期保存日志的场景
````

- DatabaseAppender：将日志输出到数据库，相关数据库表脚本文件存放在源码 `ch.qos.logback:logback-classic:1.2.3.jar-ch.qos.logback.classic.db.script` 包下。

  ````xml
  
  <appender name="database" class="ch.qos.logback.classic.db.DBAppender">
    <connectionSource class="ch.qos.logback.core.db.DataSourceConnectionSource">
        <dataSource class="com.zaxxer.hikari.HikariDataSource">
            <driverClass>com.mysql.cj.jdbc.Driver</driverClass>
            <jdbcUrl>jdbc:mysql://127.0.0.1:3306/spring_boot_start</jdbcUrl>
            <user>root</user>
            <password>cmmplb</password>
        </dataSource>
    </connectionSource>
  </appender>
  ````

````sql
BEGIN;
DROP TABLE IF EXISTS `logging_event`;
DROP TABLE IF EXISTS `logging_event_property`;
DROP TABLE IF EXISTS `logging_event_exception`;
COMMIT;

-- logging_event ( 日志事件信息表 )
BEGIN;
CREATE TABLE `logging_event`
(
    `timestmp`          bigint                                  NOT NULL COMMENT '日志事件发生的时间戳',
    `formatted_message` text COLLATE utf8mb4_general_ci         NOT NULL COMMENT '格式化后的日志消息, 实际记录的日志内容',
    `logger_name`       varchar(254) COLLATE utf8mb4_general_ci NOT NULL COMMENT '日志记录器名称, Java 类全限定名',
    `level_string`      varchar(254) COLLATE utf8mb4_general_ci NOT NULL COMMENT '日志级别, 如 DEBUG, INFO, ERROR',
    `thread_name`       varchar(254) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '线程名称',
    `reference_flag`    smallint                                DEFAULT NULL COMMENT '参考标志, 用于标识日志事件的引用',
    `arg0`              varchar(254) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '日志消息中的第一个参数',
    `arg1`              varchar(254) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '日志消息中的第二个参数',
    `arg2`              varchar(254) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '日志消息中的第三个参数',
    `arg3`              varchar(254) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '日志消息中的第四个参数',
    `caller_filename`   varchar(254) COLLATE utf8mb4_general_ci NOT NULL COMMENT '调用日志记录文件名',
    `caller_class`      varchar(254) COLLATE utf8mb4_general_ci NOT NULL COMMENT '调用日志记录类全限定名',
    `caller_method`     varchar(254) COLLATE utf8mb4_general_ci NOT NULL COMMENT '调用日志记录方法名',
    `caller_line`       char(4) COLLATE utf8mb4_general_ci      NOT NULL COMMENT '调用日志记录代码行号',
    `event_id`          bigint                                  NOT NULL AUTO_INCREMENT COMMENT '主键, 日志事件的唯一标识符',
    PRIMARY KEY (`event_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='日志事件信息表';
COMMIT;

-- logging_event_property ( 日志事件额外属性信息表 )
BEGIN;
CREATE TABLE `logging_event_property`
(
    `event_id`     bigint                                  NOT NULL COMMENT '外键, 引用 logging_event 表的 event_id',
    `mapped_key`   varchar(254) COLLATE utf8mb4_general_ci NOT NULL COMMENT '映射的键, 用于标识额外属性的名称',
    `mapped_value` text COLLATE utf8mb4_general_ci         NOT NULL COMMENT '映射的值, 额外属性的值',
    PRIMARY KEY (`event_id`, `mapped_key`),
    CONSTRAINT `logging_event_property_ibfk_1` FOREIGN KEY (`event_id`) REFERENCES `logging_event` (`event_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='日志事件额外属性信息表';
COMMIT;

-- logging_event_exception ( 日志事件异常信息表 )
BEGIN;
CREATE TABLE `logging_event_exception`
(
    `event_id`   bigint                                  NOT NULL COMMENT '外键, 引用 logging_event 表的 event_id',
    `i`          smallint                                NOT NULL COMMENT '异常信息的索引, 堆栈信息的行号',
    `trace_line` varchar(254) COLLATE utf8mb4_general_ci NOT NULL COMMENT '异常堆栈信息的具体行内容',
    PRIMARY KEY (`event_id`, `i`),
    CONSTRAINT `logging_event_exception_ibfk_1` FOREIGN KEY (`event_id`) REFERENCES `logging_event` (`event_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='日志事件异常信息表';
COMMIT;
````

- SMTPAppender：将日志输出到邮件。
- SyslogAppender：将日志输出到 Syslog 服务器。
- AsyncAppender：异步地将日志输出到其他 appender。

### Loggers

Logger 用于定义日志的记录器。可以为每个包或类单独定义 logger，也可以定义全局的 root logger。

- 为特定包或类定义 logger。

````xml

<logger name="com.example" level="DEBUG">
    <appender-ref ref="CONSOLE"/>
</logger>
````

- 定义全局的 root logger。

````xml

<root level="INFO">
    <appender-ref ref="CONSOLE"/>
</root>
````

## 完整示例

````xml

<?xml version="1.0" encoding="UTF-8"?>
<configuration>
    <!-- Console Appender -->
    <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>

    <!-- File Appender -->
    <appender name="FILE" class="ch.qos.logback.core.FileAppender">
        <file>logs/app.log</file>
        <append>true</append>
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>

    <!-- Rolling File Appender -->
    <appender name="ROLLING" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>logs/app.log</file>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>logs/app.%d{yyyy-MM-dd}.log</fileNamePattern>
            <maxHistory>30</maxHistory>
        </rollingPolicy>
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>

    <!-- Logger for specific package -->
    <logger name="com.example" level="DEBUG">
        <appender-ref ref="CONSOLE"/>
    </logger>

    <!-- Root Logger -->
    <root level="INFO">
        <appender-ref ref="CONSOLE"/>
        <appender-ref ref="FILE"/>
        <appender-ref ref="ROLLING"/>
    </root>
</configuration>
````

## 高级配置选项

### 异步日志

使用异步日志可以提高性能，减少日志记录对应用程序的影响。

````xml

<appender name="ASYNC_FILE" class="ch.qos.logback.classic.AsyncAppender">
    <appender-ref ref="FILE"/>
</appender>
````

### 过滤器

过滤器用于过滤日志消息，可以根据日志级别、日志内容、线程名称等条件进行过滤，根据条件动态控制日志输出。

````xml

<appender name="FILTERED_CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
    <filter class="ch.qos.logback.classic.filter.ThresholdFilter">
        <level>ERROR</level>
    </filter>
    <filter class="ch.qos.logback.classic.filter.LevelFilter">
        <level>DEBUG</level>
        <onMatch>ACCEPT</onMatch>
        <onMismatch>DENY</onMismatch>
    </filter>
    <encoder>
        <pattern>%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n</pattern>
    </encoder>
</appender>

````

### 配置上下文监听器

配置上下文监听器可以在应用程序启动时加载外部配置文件，例如从数据库或文件系统加载配置。

````xml

<configuration scan="true" scanPeriod="30 seconds">
    <!-- 配置内容 -->
    <contextListener class="ch.qos.logback.classic.joran.JoranConfigurator">
        <resetJoran>true</resetJoran>
    </contextListener>
</configuration>
````

## 自定义过滤器判断

````xml

<configuration>
    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n</pattern>
        </encoder>
        <!-- 引用自定义过滤器 -->
        <filter class="io.github.cmmplb.start.filter.CustomFilter">
            <enabled>true</enabled> <!-- 根据需要启用或禁用 -->
        </filter>
    </appender>
    <root level="debug">
        <appender-ref ref="STDOUT"/>
    </root>
</configuration>
````

## Logback 使用 janino 实现条件判断

````xml

<dependency>
    <groupId>org.codehaus.janino</groupId>
    <artifactId>janino</artifactId>
</dependency>

````

### 语法

- if-then 语法：

````xml
   <!-- if-then form -->
<if condition="some conditional expression">
    <then>
        ...
    </then>
</if>
````

- if-then-else 语法：

````xml
  <!-- if-then-else form -->
<if condition="some conditional expression">
    <then>
        ...
    </then>
    <else>
        ...
    </else>
</if>
````

#### 条件表达式

- property() or p() or equals()

````
property("someKey").contains("someValue")
p("someKey").contains("someValue")
Boolean.valueOf(property("someValue"))
property("env").equals("local")
````

- isDefined(): 用来检查属性是否定义

````
isDefined("someKey")

````

- isNull(): 用来检查属性是否为 null

````
isNull("someKey")
````

#### 变量

在 logback 中，支持以 ${varName} 来引用变量

1. 可以直接在 logback.xml 中定义变量

````xml

<configuration>
    <property name="USER_HOME" value="/home/logs"/>
    <appender name="FILE" class="ch.qos.logback.core.FileAppender">
        <file>${USER_HOME}/app.log</file>
    </appender>
</configuration>
````

2. 可以通过启动参数 D 定义

````shell
java -DUSER_HOME="/home/logs" -jar xxx.jar
````

3. 可以通过外部文件来定义

外部文件的格式是 key-value 型，USER_HOME=/home/logs

````xml

<configuration>
    <!-- external file -->
    <property file="src/main/java/chapters/configuration/variables1.properties"/>

    <!-- classpath -->
    <property resource="resource.properties"/>
</configuration>

````

#### 变量作用域

定义的变量是有作用域的，如本地作用域，上下文作用域，系统级作用域。默认是本地作用域。

- local: 作用域在配置文件内有效
- context: 作用域的有效范围延伸至 logger context
- system: 整个 JVM 内都有效

logback 在替换变量时，首先搜索 local 变量，然后搜索 context，然后搜索 system，最后是OS environment。

##### 变量的默认值

在引用一个变量时，如果该变量未定义，那么可以为其指定默认值，做法是：

`${aName:-golden}`

- 读取上下文变量的值

`application.yml: log.console: true`

`<springProperty scope="context" name="log.console" source="log.console"/>`

- 读取系统变量的值

    - java -Dlog.console=true -jar xxx.jar
    - System.setProperty("log.console","true");

- 格式

Logback（Spring Boot 的默认日志框架）：

org/springframework/boot/logging/logback/defaults.xml

````xml

<included>
    <conversionRule conversionWord="clr" converterClass="org.springframework.boot.logging.logback.ColorConverter"/>
    <conversionRule conversionWord="wex"
                    converterClass="org.springframework.boot.logging.logback.WhitespaceThrowableProxyConverter"/>
    <conversionRule conversionWord="wEx"
                    converterClass="org.springframework.boot.logging.logback.ExtendedWhitespaceThrowableProxyConverter"/>
    <property name="CONSOLE_LOG_PATTERN"
              value="${CONSOLE_LOG_PATTERN:-%clr(%d{${LOG_DATEFORMAT_PATTERN:-yyyy-MM-dd HH:mm:ss.SSS}}){faint} %clr(${LOG_LEVEL_PATTERN:-%5p}) %clr(${PID:- }){magenta} %clr(---){faint} %clr([%15.15t]){faint} %clr(%-40.40logger{39}){cyan} %clr(:){faint} %m%n${LOG_EXCEPTION_CONVERSION_WORD:-%wEx}}"/>
    <property name="FILE_LOG_PATTERN"
              value="${FILE_LOG_PATTERN:-%d{${LOG_DATEFORMAT_PATTERN:-yyyy-MM-dd HH:mm:ss.SSS}} ${LOG_LEVEL_PATTERN:-%5p} ${PID:- } --- [%t] %-40.40logger{39} : %m%n${LOG_EXCEPTION_CONVERSION_WORD:-%wEx}}"/>

    <logger name="org.apache.catalina.startup.DigesterFactory" level="ERROR"/>
    <logger name="org.apache.catalina.util.LifecycleBase" level="ERROR"/>
    <logger name="org.apache.coyote.http11.Http11NioProtocol" level="WARN"/>
    <logger name="org.apache.sshd.common.util.SecurityUtils" level="WARN"/>
    <logger name="org.apache.tomcat.util.net.NioSelectorPool" level="WARN"/>
    <logger name="org.eclipse.jetty.util.component.AbstractLifeCycle" level="ERROR"/>
    <logger name="org.hibernate.validator.internal.util.Version" level="WARN"/>
    <logger name="org.springframework.boot.actuate.endpoint.jmx" level="WARN"/>
</included>
````

%clr 修饰符：

%clr 是 Logback 提供的用于给日志内容添加颜色或样式的格式修饰符，语法为：
plaintext
%clr(<日志内容>){<颜色/样式标记>}

其中 <颜色/样式标记> 可以是颜色名称（如 red、green）或样式关键词（如 faint、bold）。
{faint} 的作用：
faint 表示 淡色（或浅色）样式，用于将日志内容以淡色显示。例如：
plaintext
%clr(%d{yyyy-MM-dd HH:mm:ss}){faint}

会将日志时间戳以淡色显示（具体颜色可能因终端配置而异，通常为灰色或浅白色）。
支持的颜色和样式标记
Logback 的 %clr 支持以下常见颜色和样式（需终端支持 ANSI 颜色）：
颜色标记（前景色）：
标记 颜色
black 黑色
red 红色
green 绿色
yellow 黄色
blue 蓝色
magenta 品红色
cyan 青色
white 白色
样式标记（非颜色属性）：
标记 样式
faint 淡色（浅色）
bold 加粗
highlight 背景色（需搭配颜色，如 {highlight,red}）
示例：彩色日志格式
以下是 Spring Boot 默认 Logback 格式的拆解（包含 faint 和颜色标记）：

plaintext
%clr(%d{yyyy-MM-dd HH:mm:ss.SSS}){faint} %clr(${LOG_LEVEL_PATTERN:-%5p}) %clr(${PID:- }){magenta} %clr(---){faint}
%clr([%15.15t]){faint} %clr(%-40.40logger{39}){cyan} %clr(:){faint} %m%n

%clr(%d{...}){faint}：时间戳淡色显示。
%clr(${LOG_LEVEL_PATTERN})：日志级别（如 INFO、ERROR）根据级别显示不同颜色（Logback 会自动映射级别到颜色，如 ERROR 红色，INFO
绿色）。
%clr(${PID}){magenta}：进程 ID 以品红色显示。
%clr(---){faint}：分隔符 --- 淡色显示。
%clr([%t]){faint}：线程名淡色显示。
%clr(%logger){cyan}：Logger 名称以青色显示。
注意事项
终端兼容性：
只有在支持 ANSI 颜色的终端（如 Linux/macOS 终端、IntelliJ IDEA 控制台）中，颜色和样式才会生效。Windows 终端默认不支持，需启用「VT100
兼容模式」或使用工具（如 ConEmu）。

- [%logger{200}]

````
[%logger{50}] 是用于指定日志记录器（logger）名称的显示方式。

具体来说，%logger 表示要显示日志记录器的名称，而 {50} 是一个可选的参数，用于指定日志记录器名称的最大长度。如果日志记录器名称的长度超过 50 个字符，它将被截断为 50 个字符。
````

- %file:%line 是用于指定在日志中显示日志记录所在的文件名和行号。

具体解释如下：

%file：表示要显示日志记录所在的文件名。
%line：表示要显示日志记录所在的行号。

例如，如果在某个 Java 类的第 50 行记录了一条日志，并且日志格式中包含 %file:
%line，那么在日志输出中，将会显示类似 [MyClass.java:50] 的信息，其中 MyClass.java 是文件名，50 是行号。

- ${LOG_LEVEL_PATTERN:%5p}：
  ${LOG_LEVEL_PATTERN:-<默认值>}：
  这是一个属性占位符，表示尝试获取配置属性 LOG_LEVEL_PATTERN 的值。如果该属性未定义，则使用 :- 后的 <默认值>（此处默认值为
  %5p）。
  %5p：
  表示日志级别（p 是 Logback 中表示日志级别的转换字符），并使用 5 个字符的固定宽度（右对齐）。例如：
  plaintext
  [ERROR]  → 占5个字符宽度
  [INFO ]  → 不足5个字符时右侧补空格