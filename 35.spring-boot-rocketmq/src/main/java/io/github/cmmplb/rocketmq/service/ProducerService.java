package io.github.cmmplb.rocketmq.service;

/**
 * @author penglibo
 * @date 2021-09-18 10:31:08
 * @since jdk 1.8
 */
public interface ProducerService {

    void send2CommonQueue(String msg);

    void send2TransactionQueue(String msg);
}
