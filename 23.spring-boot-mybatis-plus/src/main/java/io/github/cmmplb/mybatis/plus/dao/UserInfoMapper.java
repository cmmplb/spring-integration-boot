package io.github.cmmplb.mybatis.plus.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.cmmplb.mybatis.plus.entity.UserInfo;
import org.apache.ibatis.annotations.Param;

/**
 * @author penglibo
 * @date 2021-08-22 11:47:03
 * @since jdk 1.8
 */

public interface UserInfoMapper extends BaseMapper<UserInfo> {

    /**
     * 根据id获取用户详情信息
     * @param id id
     * @return 用户详情信息
     */
    UserInfo selectById(@Param("id") Long id);
}