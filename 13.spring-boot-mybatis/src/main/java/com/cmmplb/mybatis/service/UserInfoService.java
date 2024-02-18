package com.cmmplb.mybatis.service;

import com.cmmplb.core.beans.PageResult;
import com.cmmplb.core.beans.QueryPageBean;
import com.cmmplb.mybatis.entity.UserInfo;

import java.util.List;

/**
 * @author penglibo
 * @date 2021-04-13 16:00:50
 */

public interface UserInfoService {

    /**
     * 新增用户详情
     * @param user 用户对象
     * @return 成功true/失败false
     */
    boolean save(UserInfo user);

    /**
     * 根据id删除用户详情
     * @param id 用户id
     * @return 成功true/失败false
     */
    boolean removeById(Long id);

    /**
     * 根据id修改用户详情
     * @param user 用户详情对象
     * @return 成功true/失败false
     */
    boolean updateById(UserInfo user);

    /**
     * 获取用户详情列表
     * @return 用户详情列表
     */
    List<UserInfo> list();

    /**
     * 根据id获取用户详情信息
     * @param id 用户详情id
     * @return 用户详情信息
     */
    UserInfo getById(Long id);

    /**
     * 分页获取用户详情信息
     * @param pageBean pageBean
     * @return 分页用户详情信息
     */
    PageResult<UserInfo> getByPaged(QueryPageBean pageBean);
}
