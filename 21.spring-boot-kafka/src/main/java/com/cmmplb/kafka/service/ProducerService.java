package com.cmmplb.kafka.service;

/**
 * @author penglibo
 * @date 2021-09-18 14:32:20
 * @since jdk 1.8
 */
public interface ProducerService {

    void send2SimpleMessage(String message);

    void send2ComplexMessage(String message);
}
