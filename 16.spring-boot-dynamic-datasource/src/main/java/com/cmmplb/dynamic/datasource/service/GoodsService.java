package com.cmmplb.dynamic.datasource.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cmmplb.dynamic.datasource.entity.Goods;

/**
 * @author penglibo
 * @date 2021-08-06 10:35:23
 * @since jdk 1.8
 */

public interface GoodsService extends IService<Goods> {

    /**
     * 扣减库存并计算总价
     * @param goodsId
     * @param count
     * @return
     */
    int reduceStock(Long goodsId, Integer count);
}



