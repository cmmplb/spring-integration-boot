package io.github.cmmplb.rabbitmq.scheduler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import io.github.cmmplb.rabbitmq.dao.MessageRollbackMapper;
import io.github.cmmplb.rabbitmq.entity.MessageBody;
import io.github.cmmplb.rabbitmq.entity.MessageRollback;
import io.github.cmmplb.rabbitmq.entity.RequireGoodsList;
import io.github.cmmplb.rabbitmq.service.RequireGoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author penglibo
 * @date 2025-03-23 15:22:11
 * @since jdk 1.8
 */

@Slf4j
@Component
public class SchedulerTest {

    @Autowired
    private MessageRollbackMapper messageRollbackMapper;

    @Autowired
    private RequireGoodsService requireGoodsService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // 每分钟执行一次
    @Scheduled(fixedRate = 30000)
    //  @Scheduled(cron = "0 * * * * ?")
    public void schedule() {
        List<MessageRollback> list = messageRollbackMapper.selectList();
        if (list == null || list.isEmpty()) {
            return;
        }
        Set<Object> ids = redisTemplate.opsForSet().members("scheduler");
        Set<Long> idSet = null;
        if (!CollectionUtils.isEmpty(ids)) {
            idSet = ids.stream().map(e -> Long.parseLong(e.toString())).collect(Collectors.toSet());
        }
        log.info("回滚任务...:{}", list);
        for (MessageRollback messageRollback : list) {
            if (!CollectionUtils.isEmpty(idSet) && idSet.contains(messageRollback.getId())) {
                // 当前任务正在处理中
                continue;
            }
            if (messageRollback.getType() == 1) {
                RequireGoodsList requireGoodsList = requireGoodsService.getByIdForUpdate(messageRollback.getBusinessId());
                String content = messageRollback.getContent();
                MessageBody messageBody = JSON.parseObject(content, new TypeReference<MessageBody>() {
                });
                requireGoodsService.rollback(messageRollback.getId(), requireGoodsList, messageBody.getCurrentNum());
            }
        }

    }
}
