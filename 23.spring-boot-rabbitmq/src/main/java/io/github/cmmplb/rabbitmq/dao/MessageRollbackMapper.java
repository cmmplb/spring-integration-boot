package io.github.cmmplb.rabbitmq.dao;

import io.github.cmmplb.rabbitmq.entity.MessageRollback;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author penglibo
 * @date 2021-08-22 09:29:20
 * @since jdk 1.8
 */

@Mapper
public interface MessageRollbackMapper {

    void insert(MessageRollback messageRollback);

    List<MessageRollback> selectList();

    void deleteById(Long id);
}