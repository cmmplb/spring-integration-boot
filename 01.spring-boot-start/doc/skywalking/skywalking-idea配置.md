
-javaagent:/Users/penglibo/Cmmplb/Dev/Skywalking/skywalking-agent/skywalking-agent.jar \
-Dskywalking.agent.service_name=spring-boot-start \
-Dskywalking.collector.backend_service=localhost:11800


javaagent：jar包地址
-Dskywalking.agent.service_name：对应服务名称
-Dskywalking.collector.backend_service：agent-config配置的端口



加入对SpringCloud gateway网关的支持
将skywalking/plugins目录下的apm-spring-cloud-gateway-2.1.x-plugin-8.5.0.jar、apm-spring-cloud-gateway-2.0.x-plugin-8.5.0.jar 
拷贝到skywalking/optional-plugins目录下即可

3.2.3 忽略跟踪某项节点（url）
1.将skywalking/plugins目录下的apm-trace-ignore-plugin-8.5.0.jar 拷贝到skywalking/optional-plugins目录下
2.在skywalking/agent/config下创建apm-trace-ignore-plugin.config文件, 并加入trace.ignore_path=${SW_AGENT_TRACE_IGNORE_PATH:/api-docs/**} 为例, 
来忽略访问swaggerUI的追踪