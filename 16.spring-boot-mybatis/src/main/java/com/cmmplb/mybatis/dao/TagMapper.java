package com.cmmplb.mybatis.dao;

import com.cmmplb.mybatis.entity.Tag;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author penglibo
 * @date 2021-08-22 09:29:20
 * @since jdk 1.8
 */

public interface TagMapper {

    /**
     * 根据id删除标签
     * @param id 主键
     * @return 受影响行数
     */
    int deleteById(Long id);

    /**
     * 新增标签
     * @param tag 标签对象
     * @return 受影响行数
     */
    int insert(Tag tag);

    /**
     * 根据id查询标签
     * @param id 主键
     * @return 标签对象
     */
    Tag selectById(Long id);

    /**
     * 根据id更新标签
     * @param tag 标签对象
     * @return 受影响行数
     */
    int updateById(Tag tag);

    /**
     * 根据用户id查询用户标签
     * @param userId 用户id
     * @return 用户标签对象
     */
    List<Tag> selectByUserId(Long userId);
}