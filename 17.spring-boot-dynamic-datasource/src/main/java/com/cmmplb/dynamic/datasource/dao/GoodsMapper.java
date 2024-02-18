package com.cmmplb.dynamic.datasource.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cmmplb.dynamic.datasource.entity.Goods;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author penglibo
 * @date 2021-08-06 10:47:06
 * @since jdk 1.8
 */

@Mapper
public interface GoodsMapper extends BaseMapper<Goods> {
}