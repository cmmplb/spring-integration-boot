package io.github.cmmplb.mybatis.service;

import io.github.cmmplb.mybatis.entity.UserTag;

import java.util.List;

/**
 * @author penglibo
 * @date 2021-08-22 09:29:20
 * @since jdk 1.8
 */

public interface UserTagService {

    /**
     * 根据id删除用户标签关系
     * @param id 主键
     * @return 成功true/失败false
     */
    Boolean removeById(Long id);

    /**
     * 新增用户标签关系
     * @param userTag 用户标签
     * @return 成功true/失败false
     */
    Boolean insert(UserTag userTag);

    /**
     * 根据用户id查询用户标签
     * @param userId 用户id
     * @return 用户标签对象
     */
    List<UserTag> getByUserId(Long userId);
}
