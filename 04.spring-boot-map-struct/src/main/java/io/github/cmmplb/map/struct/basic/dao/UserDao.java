package com.cmmplb.map.struct.basic.dao;

import com.cmmplb.map.struct.basic.dto.UserDTO;
import com.cmmplb.map.struct.basic.entity.User;

import java.util.List;

/**
 * @author penglibo
 * @date 2022-08-04 09:28:50
 * @since jdk 1.8
 */
public interface UserDao {

    /**
     * 模拟数据库, 查询一个
     * @return user
     */
    User selectOne();

    /**
     * 模拟数据库, 查询DTO
     * @return user
     */
    UserDTO selectDTO();

    /**
     * 模拟数据库, 查询列表
     * @return users
     */
    List<User> selectList();
}
