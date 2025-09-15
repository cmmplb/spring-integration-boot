package io.github.cmmplb.dynamic.datasource.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import io.github.cmmplb.dynamic.datasource.entity.Order;
import io.github.cmmplb.dynamic.datasource.service.DemoService;
import io.github.cmmplb.dynamic.datasource.service.GoodsService;
import io.github.cmmplb.dynamic.datasource.service.OrderService;
import io.github.cmmplb.dynamic.datasource.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author penglibo
 * @date 2021-08-06 10:32:17
 * @since jdk 1.8
 */

@Slf4j
@Service
public class DemoServiceImpl implements DemoService {

    @Autowired
    private UserService userService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Override
    @DS("master") // 每一层都需要使用多数据源注解切换所选择的数据库
    @Transactional
    // @GlobalTransactional //重点 第一个开启事务的需要添加seata全局事务注解
    public boolean demo() {

        log.info("=============ORDER START=================");
        Long userId = 1L;
        Long goodsId = 1L;
        Integer count = 10;

        log.info("收到下单请求,用户ID:{},商品ID:{},商品数量:{}", userId, goodsId, count);

        //log.info("当前 XID: {}", RootContext.getXID());

        Order order = new Order();
        order.setUserId(userId);
        order.setGoodsId(goodsId);
        order.setAmount(count);
        order.setCreateTime(new Date());
        order.setUpdateTime(new Date());
        orderService.save(order);

        log.info("订单一阶段生成, 等待扣库存付款中");

        int totalPrice = goodsService.reduceStock(goodsId, count);

        log.info("扣减用户余额");
        userService.reduceBalance(userId, totalPrice);

        log.info("更新订单状态");
        order.setStatus((byte) 2);
        orderService.updateById(order);

        log.info("订单已成功下单");

        log.info("=============ORDER END=================");
        return true;
    }
}
