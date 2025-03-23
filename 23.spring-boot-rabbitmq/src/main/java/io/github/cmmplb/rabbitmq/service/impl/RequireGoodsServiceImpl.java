package io.github.cmmplb.rabbitmq.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.rabbitmq.client.Channel;
import io.github.cmmplb.rabbitmq.dao.MessageRollbackMapper;
import io.github.cmmplb.rabbitmq.dao.RequireGoodsListMapper;
import io.github.cmmplb.rabbitmq.entity.MessageBody;
import io.github.cmmplb.rabbitmq.entity.MessageRollback;
import io.github.cmmplb.rabbitmq.entity.RequireGoodsList;
import io.github.cmmplb.rabbitmq.service.RequireGoodsService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

/**
 * @author penglibo
 * @date 2025-03-23 13:17:14
 * @since jdk 1.8
 */

@Slf4j
@Service
public class RequireGoodsServiceImpl implements RequireGoodsService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RequireGoodsListMapper requireGoodsListMapper;

    @Autowired
    private MessageRollbackMapper messageRollbackMapper;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    @Transactional
    public void test(Long id, int i) throws InterruptedException {
        RLock lock = redissonClient.getLock("test");
        try {
            // 待收货数量、已收货数量
            // 供应商在要货单界面如果待收货数量为 0，则更新收货状态为已收货
            // 假设此时收货 100，待收货也是 100
            // 更新要货单待收货和已收货数量
            RequireGoodsList requireGoodsList = requireGoodsListMapper.selectByIdForUpdate(id);
            Integer standbyNum = requireGoodsList.getStandbyNum();
            int count = standbyNum - i;
            if (count < 0) {
                log.info("待收货数量不足");
                return;
            } else if (count == 0) {
                // 待收货数量减收货数量为 0 表示已全收货

                // 物流状态: 0-未发货;1-已发货(在途中);2-已完成;
                requireGoodsList.setLogisticsStatus(2);
                // 收货状态：0-未收货;1-部分收货;2-已收货;
                requireGoodsList.setIsWarehousing(2);
            } else {
                // 收货状态：0-未收货;1-部分收货;2-已收货;
                requireGoodsList.setIsWarehousing(1);
            }
            // 已收货数量
            requireGoodsList.setReceiveNum(requireGoodsList.getReceiveNum() + i);
            // 待收货数量
            requireGoodsList.setStandbyNum(count);
            requireGoodsList.setStatus(1);
            requireGoodsList.setCurrentNum(i);
            // 更新要货单信息
            Thread.sleep(3000);
            requireGoodsListMapper.updateById(requireGoodsList);

            MessageBody messageBody = new MessageBody();
            messageBody.setRequireGoodsListId(requireGoodsList.getId());
            messageBody.setCurrentNum(requireGoodsList.getCurrentNum());
            messageBody.setStandbyNum(requireGoodsList.getStandbyNum());
            messageBody.setIsWarehousing(requireGoodsList.getIsWarehousing());
            messageBody.setLogisticsStatus(requireGoodsList.getLogisticsStatus());
            log.info("发送消息:{}", JSON.toJSONString(messageBody));

            // 记录本地消息表
            MessageRollback messageRollback = new MessageRollback();
            messageRollback.setBusinessId(requireGoodsList.getId());
            messageRollback.setContent(JSON.toJSONString(messageBody));
            messageRollback.setType(1);
            messageRollback.setStatus(0);
            messageRollback.setRecount(0);
            messageRollback.setVersion(0);
            messageRollback.setStatus(0);
            messageRollbackMapper.insert(messageRollback);

            redisTemplate.opsForValue().set("scheduler", messageRollback.getId().toString(), 30, TimeUnit.SECONDS);

            messageBody.setId(messageRollback.getId());
            // 发送消息通知修改库存
            rabbitTemplate.convertAndSend("testQueue", JSON.toJSONString(messageBody));

        } finally {
            // 确保释放锁
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    // 监听是否成功，更新要货单状态
    @RabbitListener(queues = "reQueue")
    @Transactional
    public void listener(String body, Message message, Channel channel) throws Exception {
        long msgTag = message.getMessageProperties().getDeliveryTag();
        // 处理修改库存
        MessageBody messageBody = JSON.parseObject(body, new TypeReference<MessageBody>() {
        });
        log.info("监听到成功消息 MessageBody:{}", messageBody);
        RequireGoodsList requireGoodsList = requireGoodsListMapper.selectByIdForUpdate(messageBody.getRequireGoodsListId());
        // 状态：0-成功;1-失败;
        if (messageBody.getStatus().equals(1)) {
            // 回滚
            rollback(messageBody.getId(), requireGoodsList, messageBody.getCurrentNum());
        } else {
            // 状态：0-正常;1-异常;
            requireGoodsList.setStatus(0);
            requireGoodsListMapper.updateById(requireGoodsList);
            // 删除本地消息表
            messageRollbackMapper.deleteById(messageBody.getId());
        }
        log.info("告诉broker, 消息已经被确认");
        channel.basicAck(msgTag, false);
    }

    @Override
    public void rollback(Long id, RequireGoodsList requireGoodsList, Integer currentNum) {
        // 将要货单待收货和已收货数量回滚
        Integer standbyNum = requireGoodsList.getStandbyNum();
        // 已收货数量
        requireGoodsList.setReceiveNum(requireGoodsList.getReceiveNum() - currentNum);
        // 待收货数量
        requireGoodsList.setStandbyNum(standbyNum + currentNum);
        // 收货状态：0-未收货;1-部分收货;2-已收货;
        requireGoodsList.setIsWarehousing(standbyNum + currentNum == 0 ? 2 : 1);
        // 状态：0-正常;1-异常;
        requireGoodsList.setStatus(0);
        requireGoodsListMapper.updateById(requireGoodsList);
        // 删除本地消息表
        messageRollbackMapper.deleteById(id);
        log.info("回滚成功");
    }

    @Override
    public RequireGoodsList getByIdForUpdate(Long id) {
        return requireGoodsListMapper.selectByIdForUpdate(id);
    }
}
