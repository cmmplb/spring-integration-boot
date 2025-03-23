package io.github.cmmplb.rabbitmq.dao;

import io.github.cmmplb.rabbitmq.entity.InventoryWarehousing;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author penglibo
 * @date 2021-08-22 09:29:20
 * @since jdk 1.8
 */

@Mapper
public interface InventoryWarehousingMapper {


    InventoryWarehousing selectByRequireGoodsListId(@Param("requireGoodsListId") Long requireGoodsListId);

    void updateById(InventoryWarehousing inventoryWarehousing);

    void insert(InventoryWarehousing inventoryWarehousing);
}