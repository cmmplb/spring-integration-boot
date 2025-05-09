package io.github.cmmplb.dynamic.datasource.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.cmmplb.dynamic.datasource.dao.OrderMapper;
import io.github.cmmplb.dynamic.datasource.entity.Order;
import io.github.cmmplb.dynamic.datasource.service.OrderService;
import org.springframework.stereotype.Service;

/**
 * @author penglibo
 * @date 2021-08-06 10:41:21
 * @since jdk 1.8
 */

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

}


