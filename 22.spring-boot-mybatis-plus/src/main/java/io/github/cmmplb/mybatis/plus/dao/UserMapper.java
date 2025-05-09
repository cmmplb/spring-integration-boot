package io.github.cmmplb.mybatis.plus.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.cmmplb.core.beans.QueryPageBean;
import io.github.cmmplb.mybatis.plus.entity.User;
import io.github.cmmplb.mybatis.plus.vo.UserInfoVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author penglibo
 * @date 2021-04-02 00:03:34
 * MP 支持不需要 Mapper.xml
 */

//@CacheNamespace(implementation=MybatisRedisCache.class,eviction=MybatisRedisCache.class)
public interface UserMapper extends BaseMapper<User> {

    /**
     * 分页获取用户信息
     * @return Page
     */
    Page<User> selectByPaged();

    /**
     * 分页获取用户信息
     * @return Page
     */
    Page<User> selectByPaged(@Param("page") Page<User> page);

    /**
     * 获取分页总条数
     * @return 总条数
     */
    Long getByPagedTotal();

    /**
     * 获取分页数据列表
     * @param pageBean pageBean
     * @return List
     */
    List<User> getByList(QueryPageBean pageBean);

    /**
     * 根据id获取用户详情
     * @param id id
     * @return UserInfoVO
     */
    UserInfoVO selectUserInfoById(@Param("id") Long id);

    /**
     * 测试一对多映射-子查询方式
     * @param id id
     * @return UserInfoVO
     */
    UserInfoVO selectTestOneMany2SubQuery(@Param("id") Long id);

    /**
     * 测试一对多映射-字段映射方式
     * @param id id
     * @return UserInfoVO
     */
    UserInfoVO selectTestOneMany2FieldMapping(@Param("id") Long id);
}
