package io.github.cmmplb.rabbitmq.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author penglibo
 * @date 2021-08-22 09:29:20
 * @since jdk 1.8
 * 入库信息表
 */

@Data
@Accessors(chain = true) // 使用这个可以让set返回实体, 便于链式set
public class InventoryWarehousing implements Serializable {

    /**
     * 主键
     */
    private Long id;

    /**
     * 库存数量
     */
    private Integer num;

    /**
     * 要货单 id
     */
    private Long requireGoodsListId;

    /**
     * 乐观锁版本号
     */
    private Integer version;
}