package io.github.cmmplb.sharding.sphere.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.cmmplb.sharding.sphere.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author penglibo
 * @date 2021-04-02 00:03:34
 * MP 支持不需要 Mapper.xml
 */

@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 分页获取用户信息
     * @return Page
     */
    Page<User> selectByPaged(@Param("page") Page<User> page);
}
