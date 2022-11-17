#!/bin/bash

# ==================================================== JVM - 解锁实验参数
# -XX:+UnlockExperimentalVMOptions JVM解锁实验参数，使用实验参数-XX:......生效

# ==================================================== JVM - 物理内存分配
# -XX:+AlwaysPreTouch 则jvm启动的时候，会分配物理内存

# ==================================================== JVM - 对象分配（栈上分配和TLAB分配的对象不会引发CG）
# --------------- 栈上分配
# -XX:DoEscapeAnalysis 启用逃逸分析（使用server模式下默认开启）
# -XX:+EliminateAllocations开启标量替换（默认开启）
# --------------- TLAB分配
# -XX:+UseTLAB 开启TLAB（默认开启）
# --------------------------------------------

# ==================================================== JVM - 堆内存大小
# -XX:+UseCGroupMemoryLimitForHeap 允许JVM检测容器中的最大堆大小（-Xmx会覆盖该参数）
# -XX:MaxRAMFraction 堆内存 = JVM使用可用内存 / -XX:MaxRAMFraction

# ==================================================== JVM - GC算法
# -XX:+UseG1GC 启动G1垃圾回收算法
# -XX:+UseStringDeduplication 内存中删除重复的字符串对象（G1 GC算法时生效）
# -XX:InitiatingHeapOccupancyPercent 触发全局并发标记的老年代使用占比（默认45%）
# -XX:MaxGCPauseMillis 暂停时间（默认值200ms）这是一个软性目标，G1会尽量达成，如果达不成，会逐渐做自我调整
# -XX:GCTimeRatio （默认值9）GCTimeRatio的值越大，Java堆尺寸的增长就会更加得积极
# -XX:G1HeapWastePercent 触发Mixed GC的堆垃圾占比，（默认值5%）
#     全局标记结束后能够统计出所有Cset内可被回收的垃圾占整对的比例值
#     如果超过5%，那么就会触发之后的多轮Mixed GC
#     如果不超过，那么会在之后的某次Young GC中重新执行全局并发标记。
#     可以尝试适当的调高此阈值，能够适当的降低Mixed GC的频率
# -XX:ConcGCThreads 指定并发GC线程数（默认-XX:ParallelGCThreads / 4），非Stop-The-World期间的GC工作线程数
# -XX:ParallelGCThreads 指定并行GC线程数，Stop-The-World阶段工作的GC线程数
#     用户显示指定了，使用用户指定的值
#     用户未显示指定了，需要根据实际的CPU所能够支持的线程数来计算ParallelGCThreads的值
#         如果物理CPU所能够支持线程数小于8，则值为CPU所支持的线程数
#         物理CPU所能够支持线程数大于8，则值为8加上一个调整值（ParallelGCThreads = 8 + (N - 8) * 【5/8 或 5/16】）JVM会根据实际的情况来选择具体是乘以5/8还是5/16
#         比如：在64线程的x86 CPU上，如果用户未指定ParallelGCThreads的值，则默认的计算方式为：ParallelGCThreads = 8 + (64 - 8) * (5/8) = 8 + 35 = 43
# -XX:+DisableExplicitGC 禁用System.gc()，禁用后会导致Netty框架无法申请到足够的堆外内存,从而产生#java.lang.OutOfMemoryError: Direct buffer memory
nohup java  \
-javaagent:/elastic-apm-agent-1.19.0.jar  \
-Delastic.apm.service_name=app-name \
-Delastic.apm.server_urls=${APM_SERVER} \
-Delastic.apm.application_packages=${APM_PACKAGE} \
-Delastic.apm.environment=${ENV} \
-Delastic.apm.max_queue_size=512 \
-Delastic.apm.metrics_interval=15s \
-Delastic.apm.transaction_sample_rate=0.8 \
-Dapollo.configService=${APOLLO_META} \
-Dlogging.config=${LOG_CONFIG} \
-Dlogging.file.path=${LOG_PATH} \
-Dlogging.level.root=info \
-jar \
-XX:+UnlockExperimentalVMOptions \
-XX:+UseCGroupMemoryLimitForHeap \
${JVM_MEM} ${JVM_GC} \
/app-name.jar \
--spring.profiles.active=${PROFILE_ACTIVE}