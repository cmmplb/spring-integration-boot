package com.cmmplb.dynamic.datasource.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cmmplb.dynamic.datasource.entity.User;

import java.util.List;

/**
 * @author penglibo
 * @date 2021-08-06 09:39:20
 * @since jdk 1.8
 */

public interface UserService extends IService<User> {

    /**
     * 添加用户-使用DS注解进行切换数据源。
     * @param user
     */
    void saveUser(User user);

    /**
     * 获取列表-手动切换数据源
     * @return
     */
    List<User> getAllList();

    /**
     * 查询列表
     * @return
     */
    List<User> getList();

    /**
     * 根据id获取信息-自定义一个master库的注解
     * @param id
     * @return
     */
    User getById(Long id);

    @DS("user")//如果a是默认数据源则不需要DS注解。
    @DSTransactional
    boolean updateInfoById(User user);

    /**
     * 扣减用户余额
     * @param userId
     * @param totalPrice
     */
    void reduceBalance(Long userId, Integer totalPrice);

}





