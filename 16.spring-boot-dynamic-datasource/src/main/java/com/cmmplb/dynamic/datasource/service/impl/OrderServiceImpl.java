package com.cmmplb.dynamic.datasource.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cmmplb.dynamic.datasource.dao.OrderMapper;
import com.cmmplb.dynamic.datasource.entity.Order;
import com.cmmplb.dynamic.datasource.service.OrderService;
import org.springframework.stereotype.Service;

/**
 * @author penglibo
 * @date 2021-08-06 10:41:21
 * @since jdk 1.8
 */

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

}


