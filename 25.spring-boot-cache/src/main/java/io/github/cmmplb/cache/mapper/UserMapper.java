package io.github.cmmplb.cache.mapper;

import io.github.cmmplb.cache.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author penglibo
 * @date 2021-04-02 00:03:34
 */
@Mapper
public interface UserMapper {

    /**
     * 新增用户
     * @param user 用户对象
     * @return 受影响行数
     */
    int insert(User user);

    /**
     * 根据id删除用户
     * @param id 用户id
     * @return 受影响行数
     */
    int deleteById(@Param("id") Long id);

    /**
     * 根据id修改用户信息
     * @param user 用户对象
     * @return 受影响行数
     */
    int updateById(User user);

    /**
     * 获取用户列表
     * @return 用户列表
     */
    List<User> selectList();

    /**
     * 根据id获取用户信息
     * @param id 用户id
     * @return 用户信息
     */
    User selectById(@Param("id") Long id);

}
