package com.cmmplb.mybatis.mapper.service;

import io.github.cmmplb.core.beans.PageResult;
import io.github.cmmplb.core.beans.QueryPageBean;
import com.cmmplb.mybatis.mapper.entity.User;

import java.util.List;

/**
 * @author penglibo
 * @date 2021-04-02 00:01:56
 */
public interface UserService {

    /**
     * 新增用户
     * @param user 用户对象
     * @return 成功true/失败false
     */
    boolean save(User user);

    /**
     * 根据id删除用户
     * @param id 用户id
     * @return 成功true/失败false
     */
    boolean removeById(Long id);

    /**
     * 根据id修改用户信息
     * @param user 用户对象
     * @return 成功true/失败false
     */
    boolean updateById(User user);

    /**
     * 获取用户列表
     * @return 用户列表
     */
    List<User> list();

    /**
     * 根据id获取用户信息
     * @param id 用户id
     * @return 用户信息
     */
    User getById(Long id);

    /**
     * 分页获取用户信息
     * @param pageBean pageBean
     * @return 分页用户信息
     */
    PageResult<User> getByPaged(QueryPageBean pageBean);

    /**
     * 测试当前对象id是否注入
     * @return
     */
    List<User> testCurrentUserId();
}
