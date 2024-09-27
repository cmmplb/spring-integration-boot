package com.cmmplb.mybatis.dao;

import com.cmmplb.mybatis.entity.UserTag;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author penglibo
* @date 2021-08-22 09:29:20
* @since jdk 1.8
*/

public interface UserTagMapper {

    /**
     * 根据id删除用户标签关系
     * @param id 主键
     * @return 受影响行数
     */
    int deleteById(Long id);

    /**
     * 新增用户标签关系
     * @param userTag 用户标签
     * @return 受影响行数
     */
    int insert(UserTag userTag);

    /**
     * 根据用户id查询用户标签
     * @param userId 用户id
     * @return 用户标签对象
     */
    List<UserTag> selectByUserId(Long userId);
}