package com.cmmplb.dynamic.datasource.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cmmplb.dynamic.datasource.annotation.Master;
import com.cmmplb.dynamic.datasource.dao.UserMapper;
import com.cmmplb.dynamic.datasource.entity.User;
import com.cmmplb.dynamic.datasource.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author penglibo
 * @date 2021-08-06 09:39:20
 * @since jdk 1.8
 */

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    /**
     * 使用DS注解进行切换数据源。
     * @param user
     */
    @DS("oracle")
    @Override
    public void saveUser(User user) {
        //do something
        baseMapper.insert(user);
    }

    /**
     * 手动切换数据源
     * @return
     */
    @Override
    public List<User> getAllList() {
        DynamicDataSourceContextHolder.push("slave");//手动切换
        return baseMapper.selectList(new QueryWrapper<>());
    }

    /**
     * 自定义一个master库的注解
     * @return
     */
    @Master
    @Override
    public User getById(Long id) {
        return baseMapper.selectById(id);
    }

    // @DS("user")//如果a是默认数据源则不需要DS注解。
    @DSTransactional // 本地事务回滚-在最外层的方法添加 @DSTransactional，底下调用的各个类该切数据源就正常使用DS切换数据源即可。
    @Override
    public boolean updateInfoById(User user) {
        return baseMapper.updateById(user) > 0;
        // todo:userInfoService.update();
    }

    /**
     * 事务传播特性设置为 REQUIRES_NEW 开启新的事务    重要！！！！一定要使用REQUIRES_NEW
     */
    @DS("slave_2")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void reduceBalance(Long userId, Integer totalPrice) {
        log.info("=============ACCOUNT START=================");
        //log.info("当前 XID: {}", RootContext.getXID());

        User user = baseMapper.selectById(userId);
        Integer amount = user.getAmount();
        log.info("下单用户{}余额为 {},商品总价为{}", userId, amount, totalPrice);

        if (amount < totalPrice) {
            log.warn("用户 {} 余额不足，当前余额:{}", userId, amount);
            throw new RuntimeException("余额不足");
        }
        log.info("开始扣减用户 {} 余额", userId);
        int currentBalance = user.getAmount() - amount;
        user.setAmount(currentBalance);
        baseMapper.updateById(user);
        log.info("扣减用户 {} 余额成功,扣减后用户账户余额为{}", userId, currentBalance);
        log.info("=============ACCOUNT END=================");
    }

    @Override
    public List<User> getList() {
        return baseMapper.selectList(new QueryWrapper<>());
    }

}





