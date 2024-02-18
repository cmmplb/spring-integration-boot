package com.cmmplb.mybatis.plus.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cmmplb.core.beans.PageResult;
import com.cmmplb.core.beans.QueryPageBean;
import com.cmmplb.mybatis.plus.entity.User;
import com.cmmplb.mybatis.plus.vo.UserInfoVO;

/**
 * @author penglibo
 * @date 2021-04-02 00:01:56
 */
public interface UserService extends IService<User> {

    /**
     * 分页获取用户信息
     * @param pageBean
     * @return
     */
    PageResult<User> getByPaged(QueryPageBean pageBean);

    /**
     * 测试一对多映射-子查询方式
     * @param id
     * @return
     */
    UserInfoVO getTestOneMany2SubQuery(Long id);

    /**
     * 测试一对多映射-字段映射方式
     * @param id
     * @return
     */
    UserInfoVO getTestOneMany2FieldMapping(Long id);
}
