package io.github.cmmplb.dynamic.datasource.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.cmmplb.dynamic.datasource.dao.GoodsMapper;
import io.github.cmmplb.dynamic.datasource.entity.Goods;
import io.github.cmmplb.dynamic.datasource.service.GoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author penglibo
 * @date 2021-08-06 10:35:23
 * @since jdk 1.8
 */
@Slf4j
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {

    /**
     * 事务传播特性设置为 REQUIRES_NEW 开启新的事务  重要！！！！一定要使用REQUIRES_NEW
     */
    @DS("slave_1")
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public int reduceStock(Long goodsId, Integer byCount) {
        log.info("=============PRODUCT START=================");
        //log.info("当前 XID: {}", RootContext.getXID());

        // 检查库存
        Goods goodDb = baseMapper.selectById(goodsId);
        Integer count = goodDb.getCount();
        log.info("商品编号为 {} 的库存为{},订单商品数量为{}", goodsId, count, byCount);

        if (count < byCount) {
            log.warn("商品编号为{} 库存不足, 当前库存:{}", goodsId, count);
            throw new RuntimeException("库存不足");
        }
        log.info("开始扣减商品编号为 {} 库存,单价商品价格为{}", goodsId, goodDb.getAmount());
        // 扣减库存
        int currentStock = count - byCount;
        goodDb.setCount(currentStock);
        baseMapper.updateById(goodDb);
        int totalPrice = goodDb.getAmount() * byCount;
        log.info("扣减商品编号为 {} 库存成功,扣减后库存为{}, {} 件商品总价为 {} ", goodsId, currentStock, byCount, totalPrice);
        log.info("=============PRODUCT END=================");
        return totalPrice;
    }
}



