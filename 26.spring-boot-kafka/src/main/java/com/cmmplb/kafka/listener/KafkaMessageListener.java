package com.cmmplb.kafka.listener;

import com.cmmplb.kafka.constants.KafkaConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author penglibo
 * @date 2021-09-18 14:37:33
 * @since jdk 1.8
 */

@Component
public class KafkaMessageListener {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    // @KafkaListener除了可以指定Topic名称和分组id外, 我们还可以同时监听来自多个Topic的消息: @KafkaListener(topics = "topic1, topic2")
    // @KafkaListener(topics = KafkaConstants.TEST_TOPIC, groupId = KafkaConstants.TEST_GROUP_ID)
    // public void listen(String message) {
    //     logger.info("接收消息: {}", message);
    // }

    // 还可以通过@Header注解来获取当前消息来自哪个分区（partitions）:
    // @KafkaListener(topics = KafkaConstants.TEST_TOPIC, groupId = KafkaConstants.TEST_GROUP_ID)
    // public void listen(@Payload String message,
    //                    @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
    //     logger.info("接收消息: {}, partition：{}", message, partition); // 没有进行分区, 所以test Topic只有一个区, 下标为0. 
    // }

    // 可以通过@KafkaListener来指定只接收来自特定分区的消息：
    /*@KafkaListener(groupId = KafkaConstants.TEST_GROUP_ID,
            topicPartitions = @TopicPartition(topic = KafkaConstants.TEST_TOPIC,
                    partitionOffsets = {
                            @PartitionOffset(partition = "0", initialOffset = "0")
                    }))
    // 如果不需要指定initialOffset, 可以简化为：
    *//*@KafkaListener(groupId = KafkaConstants.TEST_GROUP_ID,
            topicPartitions = @TopicPartition(topic = KafkaConstants.TEST_TOPIC, partitions = { "0", "1" }))*//*
    public void listen(@Payload String message,
                       @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        logger.info("接收消息: {}, partition：{}", message, partition);
    }*/

    @KafkaListener(topics = KafkaConstants.TEST_TOPIC, groupId = KafkaConstants.TEST_GROUP_ID)
    public void listen(Map<String, Object> message) {
        logger.info("接收复杂消息: {}", message);
    }
}
