package io.github.cmmplb.rabbitmq.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.rabbitmq.client.Channel;
import io.github.cmmplb.rabbitmq.dao.InventoryWarehousingMapper;
import io.github.cmmplb.rabbitmq.entity.InventoryWarehousing;
import io.github.cmmplb.rabbitmq.entity.MessageBody;
import io.github.cmmplb.rabbitmq.service.InventoryWarehousingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

/**
 * @author penglibo
 * @date 2025-03-23 13:22:44
 * @since jdk 1.8
 */

@Slf4j
@Service
public class InventoryWarehousingServiceImpl implements InventoryWarehousingService {

    @Autowired
    private InventoryWarehousingMapper inventoryWarehousingMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    // 监听是否修改库存
    @RabbitListener(queues = "testQueue")
    @Transactional
    public void listener(String body, Message message, Channel channel) throws IOException, InterruptedException {
        Thread.sleep(3000);
        long msgTag = message.getMessageProperties().getDeliveryTag();

        // 处理修改库存
        MessageBody messageBody = JSON.parseObject(body, new TypeReference<MessageBody>() {
        });
        log.info("监听到 MessageBody:{}", messageBody);
        try {
            // 如果收货是偶数，就手动加个异常
            if (messageBody.getCurrentNum() % 2 == 0) {
                log.info("手动抛出异常");
                throw new RuntimeException("手动抛出异常");
            }
            // 根据要货单 id 查询库存，如果库存信息不存在则新增
            InventoryWarehousing inventoryWarehousing = inventoryWarehousingMapper.selectByRequireGoodsListId(messageBody.getRequireGoodsListId());
            // 更新库存信息
            if (inventoryWarehousing == null) {
                inventoryWarehousing = new InventoryWarehousing();
                inventoryWarehousing.setRequireGoodsListId(messageBody.getRequireGoodsListId());
                inventoryWarehousing.setNum(messageBody.getCurrentNum());
                inventoryWarehousingMapper.insert(inventoryWarehousing);
            } else {
                inventoryWarehousing.setNum(inventoryWarehousing.getNum() + messageBody.getCurrentNum());
                inventoryWarehousingMapper.updateById(inventoryWarehousing);
            }
            messageBody.setStatus(0);
            sendMessage(messageBody);
        } catch (Exception e) {
            log.info("插入库存失败，发送回滚消息,{}", e.getMessage());
            messageBody.setStatus(1);
            // 失败的话重试 3 次
            sendMessage(messageBody);
        }
        log.info("告诉broker, 消息已经被确认");
        channel.basicAck(msgTag, false);
    }

    private void sendMessage(MessageBody messageBody) throws InterruptedException {
        int i = 0;
        while (i <= 3) {
            i++;
            if (sendSuccess(messageBody)) {
                log.info("发送成功消息成功");
                break;
            }
            Thread.sleep(200);
        }
    }

    private boolean sendSuccess(MessageBody messageBody) {
        // 发送成功消息
        try {
            rabbitTemplate.convertAndSend("reQueue", JSON.toJSONString(messageBody));
        } catch (AmqpException e) {
            return false;
        }
        return true;
    }
}
