package com.cmmplb.activemq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.annotation.PostConstruct;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * @author penglibo
 * @date 2024-05-24 11:25:08
 * @since jdk 1.8
 */

@Slf4j
@EnableJms
@Configuration
public class ActivemqConfig {

    @Autowired
    private JmsTemplate jmsTemplate;

    @PostConstruct
    public void send(String msg) {

        log.info("发送消息：{}", msg);

        jmsTemplate.send("test", new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                // 也可以创建对象 session.createObjectMessage()
                TextMessage textMessage = session.createTextMessage();
                textMessage.setText(msg);
                return textMessage;
            }
        });
    }

    /**
     * 监听消息
     * @param content
     */
    // concurrency = 消费线程数
    @JmsListener(destination = "test", concurrency = "3")
    public void recive(String content) {
        log.info("收到消息：{}", content);
    }
}
