package io.github.cmmplb.mybatis.mapper.dao;

import io.github.cmmplb.core.beans.QueryPageBean;
import io.github.cmmplb.mybatis.mapper.entity.User;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author penglibo
 * @date 2021-04-02 00:03:34
 */

public interface UserMapper extends Mapper<User> {

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

    /**
     * 分页查询用户列表
     * @return
     */
    Page<User> selectByPaged();

    /**
     * 获取分页总条数
     * @return
     */
    Long selectByPagedTotal();

    /**
     * 获取分页数据列表
     * @param pageBean
     * @return
     */
    List<User> selectByPagedList(@Param("q") QueryPageBean pageBean);

    /**
     * 测试当前对象id是否注入
     */
    List<User> testCurrentUserId();
}
