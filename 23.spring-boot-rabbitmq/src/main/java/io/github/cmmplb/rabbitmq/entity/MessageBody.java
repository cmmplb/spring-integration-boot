package io.github.cmmplb.rabbitmq.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author plb
 * @date 2020/6/15 11:16
 * 要货单总表
 */

@Data
@Accessors(chain = true) // 使用这个可以让set返回实体, 便于链式set
public class MessageBody implements Serializable {

    /**
     * 主键
     */
    private Long id;

    /**
     * 业务 id
     */
    private Long requireGoodsListId;

    /**
     * 物流状态: 0-未发货;1-已发货(在途中);2-已完成;
     */
    private Integer logisticsStatus;

    /**
     * 收货状态：0-未收货;1-部分收货;2-已收货;
     */
    private Integer isWarehousing;

    /**
     * 已收货数量
     */
    private Integer receiveNum;

    /**
     * 待收货数量
     */
    private Integer standbyNum;

    /**
     * 本次收货数量
     */
    private Integer currentNum;

    /**
     * 状态：0-成功;1-失败;
     */
    private Integer status;
}