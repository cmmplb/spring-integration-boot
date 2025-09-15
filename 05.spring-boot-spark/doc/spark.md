# Spark

Spark入门教程: https://blog.csdn.net/Javachichi/article/details/131871627

Spark 是当今大数据领域最活跃、最热门、最高效的大数据通用计算平台之一。

Spark有完善的生态圈:

- Spark Core：实现了 Spark 的基本功能，包含 RDD、任务调度、内存管理、错误恢复、与存储系统交互等模块。
- Spark SQL：Spark 用来操作结构化数据的程序包。通过 Spark SQL，我们可以使用 SQL 操作数据。
- Spark Streaming：Spark 提供的对实时数据进行流式计算的组件。提供了用来操作数据流的 API。
- Spark MLlib：提供常见的机器学习(ML)功能的程序库。包括分类、回归、聚类、协同过滤等，还提供了模型评估、数据导入等额外的支持功能。
- GraphX(图计算)：Spark 中用于图计算的 API，性能良好，拥有丰富的功能和运算符，能在海量数据上自如地运行复杂的图算法。
- 集群管理器：Spark 设计为可以高效地在一个计算节点到数千个计算节点之间伸缩计算。
- Structured Streaming：处理结构化流,统一了离线和实时的 API。

## Spark 运行模式

① local 本地模式(单机)：分为 local 单线程和 local-cluster 多线程。（本地使用）
② standalone 独立集群模式：典型的 Mater/slave 模式。（本地使用）
③ standalone-HA 高可用模式：基于 standalone 模式，使用 zk 搭建高可用，避免 Master 是有单点故障的。（生产环境使用）
④ on yarn 集群模式：运行在 yarn 集群之上，由 yarn 负责资源管理，Spark 负责任务调度和计算。（生产环境使用）
⑤ on mesos 集群模式：运行在 mesos 资源管理器框架之上，由 mesos 负责资源管理，Spark 负责任务调度和计算。
⑥ on cloud 集群模式：中小公司未来会更多的使用云服务，比如 AWS 的 EC2，使用这个模式能很方便的访问 Amazon 的 S3。

## Spark Core