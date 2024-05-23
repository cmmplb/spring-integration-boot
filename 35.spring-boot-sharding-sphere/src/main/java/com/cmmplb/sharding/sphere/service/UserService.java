package com.cmmplb.sharding.sphere.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cmmplb.core.beans.PageResult;
import com.cmmplb.core.beans.QueryPageBean;
import com.cmmplb.sharding.sphere.entity.User;

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
}
