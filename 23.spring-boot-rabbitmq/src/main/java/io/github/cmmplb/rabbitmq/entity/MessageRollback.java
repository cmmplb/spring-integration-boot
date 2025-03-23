package io.github.cmmplb.rabbitmq.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author plb
 * @date 2020/6/15 11:16
 * 本地消息表
 */

@Data
@Accessors(chain = true) // 使用这个可以让set返回实体, 便于链式set
public class MessageRollback implements Serializable {

    /**
     * 主键
     */
    private Long id;

    /**
     * 业务 id
     */
    private Long businessId;

    /**
     * 业务属性
     */
    private String content;

    /**
     * 业务类型:1-要货回滚;2-库存回滚
     */
    private Integer type;

    /**
     * 状态:0-处理中;1-成功
     */
    private Integer status;

    /**
     * 重试次数
     */
    private Integer recount;

    /**
     * 乐观锁
     */
    private Integer version;
}