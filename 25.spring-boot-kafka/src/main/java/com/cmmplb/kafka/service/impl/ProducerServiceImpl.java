package com.cmmplb.kafka.service.impl;

import com.cmmplb.kafka.constants.KafkaConstants;
import com.cmmplb.kafka.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author penglibo
 * @date 2021-09-18 14:32:30
 * @since jdk 1.8
 */

@Service
public class ProducerServiceImpl implements ProducerService {

    @Autowired
    // private KafkaTemplate<String, String> kafkaTemplate;
    private KafkaTemplate<String, Map<String, Object>> kafkaTemplate;

    @Override
    public void send2SimpleMessage(String message) {
        //this.kafkaTemplate.send(KafkaConstants.TEST_TOPIC, message);
    }

    @Override
    public void send2ComplexMessage(String message) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", message);
        map.put("id", message);
        this.kafkaTemplate.send(KafkaConstants.TEST_TOPIC, map);
    }
}
