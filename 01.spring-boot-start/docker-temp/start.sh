#!/bin/bash

# ==================================================== JVM - ����ʵ�����
# -XX:+UnlockExperimentalVMOptions JVM����ʵ�������ʹ��ʵ�����-XX:......��Ч

# ==================================================== JVM - �����ڴ����
# -XX:+AlwaysPreTouch ��jvm������ʱ�򣬻���������ڴ�

# ==================================================== JVM - ������䣨ջ�Ϸ����TLAB����Ķ��󲻻�����CG��
# --------------- ջ�Ϸ���
# -XX:DoEscapeAnalysis �������ݷ�����ʹ��serverģʽ��Ĭ�Ͽ�����
# -XX:+EliminateAllocations���������滻��Ĭ�Ͽ�����
# --------------- TLAB����
# -XX:+UseTLAB ����TLAB��Ĭ�Ͽ�����
# --------------------------------------------

# ==================================================== JVM - ���ڴ��С
# -XX:+UseCGroupMemoryLimitForHeap ����JVM��������е����Ѵ�С��-Xmx�Ḳ�Ǹò�����
# -XX:MaxRAMFraction ���ڴ� = JVMʹ�ÿ����ڴ� / -XX:MaxRAMFraction

# ==================================================== JVM - GC�㷨
# -XX:+UseG1GC ����G1���������㷨
# -XX:+UseStringDeduplication �ڴ���ɾ���ظ����ַ�������G1 GC�㷨ʱ��Ч��
# -XX:InitiatingHeapOccupancyPercent ����ȫ�ֲ�����ǵ������ʹ��ռ�ȣ�Ĭ��45%��
# -XX:MaxGCPauseMillis ��ͣʱ�䣨Ĭ��ֵ200ms������һ������Ŀ�꣬G1�ᾡ����ɣ�����ﲻ�ɣ����������ҵ���
# -XX:GCTimeRatio ��Ĭ��ֵ9��GCTimeRatio��ֵԽ��Java�ѳߴ�������ͻ���ӵû���
# -XX:G1HeapWastePercent ����Mixed GC�Ķ�����ռ�ȣ���Ĭ��ֵ5%��
#     ȫ�ֱ�ǽ������ܹ�ͳ�Ƴ�����Cset�ڿɱ����յ�����ռ���Եı���ֵ
#     �������5%����ô�ͻᴥ��֮��Ķ���Mixed GC
#     �������������ô����֮���ĳ��Young GC������ִ��ȫ�ֲ�����ǡ�
#     ���Գ����ʵ��ĵ��ߴ���ֵ���ܹ��ʵ��Ľ���Mixed GC��Ƶ��
# -XX:ConcGCThreads ָ������GC�߳�����Ĭ��-XX:ParallelGCThreads / 4������Stop-The-World�ڼ��GC�����߳���
# -XX:ParallelGCThreads ָ������GC�߳�����Stop-The-World�׶ι�����GC�߳���
#     �û���ʾָ���ˣ�ʹ���û�ָ����ֵ
#     �û�δ��ʾָ���ˣ���Ҫ����ʵ�ʵ�CPU���ܹ�֧�ֵ��߳���������ParallelGCThreads��ֵ
#         �������CPU���ܹ�֧���߳���С��8����ֵΪCPU��֧�ֵ��߳���
#         ����CPU���ܹ�֧���߳�������8����ֵΪ8����һ������ֵ��ParallelGCThreads = 8 + (N - 8) * ��5/8 �� 5/16����JVM�����ʵ�ʵ������ѡ������ǳ���5/8����5/16
#         ���磺��64�̵߳�x86 CPU�ϣ�����û�δָ��ParallelGCThreads��ֵ����Ĭ�ϵļ��㷽ʽΪ��ParallelGCThreads = 8 + (64 - 8) * (5/8) = 8 + 35 = 43
# -XX:+DisableExplicitGC ����System.gc()�����ú�ᵼ��Netty����޷����뵽�㹻�Ķ����ڴ�,�Ӷ�����#java.lang.OutOfMemoryError: Direct buffer memory
nohup java  \
-javaagent:/elastic-apm-agent-1.19.0.jar  \
-Delastic.apm.service_name=crbr-basic-bp-center \
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
/crbr-basic-bp-center.jar \
--spring.profiles.active=${PROFILE_ACTIVE}