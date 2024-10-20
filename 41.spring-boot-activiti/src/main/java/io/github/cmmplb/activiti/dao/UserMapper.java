package io.github.cmmplb.activiti.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.cmmplb.activiti.entity.User;
import io.github.cmmplb.activiti.vo.UserInfoVO;
import io.github.cmmplb.core.beans.QueryPageBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author penglibo
 * @date 2021-04-02 00:03:34
 * MP 支持不需要 Mapper.xml
 */

public interface UserMapper extends BaseMapper<User> {

    /**
     * 分页获取用户信息
     *
     * @return Page
     */
    Page<User> selectByPaged(@Param("page") Page<User> page);
}
