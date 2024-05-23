package com.cmmplb.rocketmq.service;

import org.apache.rocketmq.client.producer.DefaultMQProducer;

import java.net.DatagramSocket;

/**
 * @author penglibo
 * @date 2021-09-18 10:31:08
 * @since jdk 1.8
 */
public interface ProducerService {

    void send2CommonQueue(String msg);

    void send2TransactionQueue(String msg);
}
