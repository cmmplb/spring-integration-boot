package io.github.cmmplb.sharding.sphere.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.cmmplb.core.beans.PageResult;
import io.github.cmmplb.core.beans.QueryPageBean;
import io.github.cmmplb.sharding.sphere.entity.User;

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
