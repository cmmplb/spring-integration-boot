package io.github.cmmplb.mybatis.plus.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.cmmplb.mybatis.plus.entity.Tag;

import java.util.List;

/**
* @author penglibo
* @date 2021-08-22 11:47:03
* @since jdk 1.8
*/
    
public interface TagMapper extends BaseMapper<Tag> {

    /**
     * 根据id查询标签
     * @param id 主键
     * @return 标签对象
     */
    Tag selectById(Long id);

    /**
     * 根据用户id查询用户标签
     * @param userId 用户id
     * @return 用户标签对象
     */
    List<Tag> selectByUserId(Long userId);
}