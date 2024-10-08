package com.cmmplb.mongodb.service;

import io.github.cmmplb.core.beans.PageResult;
import io.github.cmmplb.core.beans.QueryPageBean;
import com.cmmplb.mongodb.dto.UserPageQueryDTO;
import com.cmmplb.mongodb.entity.User;

import java.util.List;

/**
 * @author penglibo
 * @date 2021-09-14 10:06:25
 * @since jdk 1.8
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
    boolean removeById(String id);

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
    User getById(String id);

    /**
     * 条件获取用户列表
     * @return
     */
    List<User> condition(UserPageQueryDTO dto);

    /**
     * 分页获取用户列表
     * @param dto 条件
     * @return 分页列表
     */
    PageResult<User> page(UserPageQueryDTO dto);
}
