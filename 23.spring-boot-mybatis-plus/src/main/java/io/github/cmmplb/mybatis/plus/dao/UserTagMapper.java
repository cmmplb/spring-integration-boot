package io.github.cmmplb.mybatis.plus.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.cmmplb.mybatis.plus.entity.UserTag;

import java.util.List;

/**
* @author penglibo
* @date 2021-08-22 11:47:03
* @since jdk 1.8
*/
    
public interface UserTagMapper extends BaseMapper<UserTag> {

    /**
     * 根据用户id查询用户标签
     * @param userId 用户id
     * @return 用户标签对象
     */
    List<UserTag> selectByUserId(Long userId);
}