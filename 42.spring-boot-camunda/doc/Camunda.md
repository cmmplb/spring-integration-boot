# API介绍

官方文档

https://docs.camunda.org/manual/7.18/user-guide/process-engine/process-engine-api/

ProcessEngine
为流程引擎，可以通过他获取相关service，里面集成了很多相关service.

RepositoryService
此服务提供用于管理和操作部署和流程定义的操作，使用camunda的第一要务

RuntimeService
运行相关，启动流程实例、删除、搜索等

TaskService
所有围绕任务相关的操作，如完成、分发、认领等

HistoryService
提供引擎搜集的历史数据服务

IdentityService
用户相关，实际中用不太到

## 数据库表结构

ACT_ID_
这部分表示用户模块，配置文件里面的用户，信息就在此模块

ACT_HI_
表示流程历史记录

act_hi_actinst： 执行的活动历史

act_hi_taskinst：执行任务历史

act_hi_procinst：执行流程实例历史

act_hi_varinst：流程变量历史表

ACT_RE_
表示流程资源存储

act_re_procdef：流程定义存储

act_re_deployment: 自动部署，springboot每次启动都会重新部署，生成记录

ACT_RU_
表示流程运行时表数据，流程结束后会删除

act_ru_execution：运行时流程实例

act_ru_task：运行时的任务

act_ru_variable：运行时的流程变量

ACT_GE_
流程通用数据

act_ge_bytearray：每次部署的文件2进制数据，所以如果文件修改后，重启也没用，因为重新生成了记录，需要清掉数据库，或者这个表记录


## 登录界面

localhost:port/index.html


