package io.github.cmmplb.rabbitmq.dao;

import io.github.cmmplb.rabbitmq.entity.RequireGoodsList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author penglibo
 * @date 2021-04-02 00:03:34
 */
@Mapper
public interface RequireGoodsListMapper {


    RequireGoodsList selectById(@Param("id") Long id);

    RequireGoodsList selectByIdForUpdate(@Param("id") Long id);

    void updateById(RequireGoodsList requireGoodsList);
}
