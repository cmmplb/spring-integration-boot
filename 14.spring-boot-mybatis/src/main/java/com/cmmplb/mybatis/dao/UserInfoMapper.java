package com.cmmplb.mybatis.dao;

import com.cmmplb.mybatis.entity.UserInfo;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

/**
 * @author penglibo
 * @date 2021-04-13 16:00:50
 */

@Mapper
public interface UserInfoMapper {

    /**
     * 新增用户详情
     * @param userInfo 用户详情对象
     * @return 受影响行数
     */
    @Insert("insert into `user_info` (`id`, `icon`, `create_time`, `update_time`, `deleted`) values (#{id}, #{icon}, current_date, current_date, 0)")
    int insert(UserInfo userInfo);

    /**
     * 根据id删除用户详情
     * @param id 用户id
     * @return 受影响行数
     */
    @Delete("delete from `user_info` where `id` = #{id}")
    int deleteById(Long id);

    /**
     * 根据id修改用户详情信息
     * @param userInfo 用户对象
     * @return 受影响行数
     */
    @Update("update `user_info` set `icon` = #{icon},`update_time` = current_date where `id` = #{id}")
    int updateById(UserInfo userInfo);

    /**
     * 根据id获取用户详情信息
     * @param id id
     * @return 用户详情信息
     */
    UserInfo selectById(@Param("id") Long id);

    /**
     * 获取用户详情列表
     * @return 用户详情列表
     */
    @Select("select * from `user_info`")
    @Results(id = "user_info_map", value = {
            @Result(property = "id", column = "id", javaType = Long.class),
            @Result(property = "icon", column = "icon", javaType = String.class),
            @Result(property = "createTime", column = "create_time", javaType = Date.class)
    })
    List<UserInfo> selectList();

    /**
     * 分页获取用户详情列表
     * @return 用户详情分页列表
     */
    @Select("select * from `user_info`")
    Page<UserInfo> selectByPaged();
}