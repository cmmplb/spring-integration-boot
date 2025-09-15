# Logstash

# Logback 配置 Logstash 输出

添加依赖

在项目的 pom.xml 中添加 Logstash 编码器依赖：

````
<dependency>
    <groupId>net.logstash.logback</groupId>
    <artifactId>logstash-logback-encoder</artifactId>
    <version>7.4</version> <!-- 最新稳定版本 -->
</dependency>
````

配置 Logback.xml

````
<configuration>
    <!-- 系统变量配置 -->
    <property name="LOG_PATTERN" value="%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n"/>
    <property name="LOGSTASH_HOST" value="localhost"/>
    <property name="LOGSTASH_PORT" value="5000"/>

    <!-- 控制台输出 -->
    <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>${LOG_PATTERN}</pattern>
        </encoder>
    </appender>

    <!-- Logstash 输出 (TCP 协议) -->
    <appender name="LOGSTASH" class="net.logstash.logback.appender.LogstashTcpAppender">
        <destination>${LOGSTASH_HOST}:${LOGSTASH_PORT}</destination>
        
        <!-- 编码器：将日志转换为 JSON 格式 -->
        <encoder class="net.logstash.logback.encoder.LogstashEncoder">
            <!-- 自定义字段：添加应用名称 -->
            <customFields>{"app_name": "your-application-name"}</customFields>
            <!-- 包含 MDC 上下文信息 -->
            <includeMdc>true</includeMdc>
        </encoder>
        
        <!-- 连接重试策略 -->
        <reconnectionDelay>5000</reconnectionDelay>
        <keepAliveDuration>30 minutes</keepAliveDuration>
    </appender>

    <!-- 根日志配置 -->
    <root level="INFO">
        <appender-ref ref="CONSOLE"/>
        <appender-ref ref="LOGSTASH"/>
    </root>
</configuration>

````

配置说明
LogstashTcpAppender：通过 TCP 协议将日志发送到 Logstash。
LogstashEncoder：将日志格式化为 JSON，便于 Logstash 解析。
customFields：添加自定义元数据（如应用名、环境），方便后续在 Elasticsearch 中过滤。
includeMdc：包含 MDC（Mapped Diagnostic Context）信息，可用于链路追踪。

## 安装

1. 从 Elastic 官网 下载并安装 Logstash（版本需与 Elasticsearch 兼容）。

2. 创建 Logstash 配置文件，在 Logstash 的 config 目录下创建 logstash.conf：

````
# 输入：接收来自 Logback 的日志
input {
  tcp {
    port => 5000
    codec => json_lines  # 解析 JSON 格式日志
    tags => ["application_logs"]
  }
}

# 过滤：可选，对日志进行预处理
filter {
  # 解析时间戳
  if [timestamp] {
    date {
      match => ["timestamp", "ISO8601"]
      target => "@timestamp"
    }
  }
  
  # 添加额外字段（如环境信息）
  mutate {
    add_field => { "environment" => "production" }
  }
}

# 输出：发送到 Elasticsearch
output {
  elasticsearch {
    hosts => ["http://localhost:9200"]
    index => "application-logs-%{+YYYY.MM.dd}"
    user => "elastic"
    password => "your-password"
  }
  
  # 同时输出到控制台（调试用）
  stdout { codec => rubydebug }
}
````

配置说明
input.tcp：监听 5000 端口，接收 Logback 发送的 TCP 数据包。
codec.json_lines：按行解析 JSON 格式的日志。
filter.date：将日志中的时间戳转换为 Elasticsearch 可识别的格式。
output.elasticsearch：将处理后的日志发送到 Elasticsearch，按日期创建索引

## 启动服务

启动 Elasticsearch

````shell
./bin/elasticsearch
````

启动 Logstash

````shell
./bin/logstash -f config/logstash.conf
./bin/logstash -f ./config/conf.d/input-output.conf
````

启动应用程序

查看 Logstash 控制台输出

启动 Logstash 后，若看到类似以下输出，说明已成功接收日志：

````
{
  "@timestamp" => 2025-05-22T08:30:00.000Z,
  "message" => "Hello, Logstash!",
  "level" => "INFO",
  "logger_name" => "com.example.App",
  "app_name" => "your-application-name",
  "environment" => "production"
}
````

在 Elasticsearch 中查询

使用 Kibana 或直接通过 API 验证日志是否已存入 Elasticsearch：

````shell
curl -X GET "localhost:9200/application-logs-*/_search?pretty"
````

## 高级配置

使用 UDP 协议（性能更高）

Logback 配置:

````
<appender name="LOGSTASH" class="net.logstash.logback.appender.LogstashUdpAppender">
    <destination>${LOGSTASH_HOST}:5000</destination>
    <encoder class="net.logstash.logback.encoder.LogstashEncoder"/>
</appender>
````

Logstash 配置：

````
input {
  udp {
    port => 5000
    codec => json_lines
  }
}
````

## 包含 MDC 上下文（链路追踪）

MDC.put("trace_id", UUID.randomUUID().toString());
log.info("Processing request...");
MDC.remove("trace_id");

Logback 配置中启用 MDC：

````
<encoder class="net.logstash.logback.encoder.LogstashEncoder">
    <includeMdc>true</includeMdc>
</encoder>
````

自定义日志字段
在 Logback 中添加自定义字段：

````
<encoder class="net.logstash.logback.encoder.LogstashEncoder">
    <customFields>{"app_name": "your-app", "version": "1.0.0"}</customFields>
</encoder>
````