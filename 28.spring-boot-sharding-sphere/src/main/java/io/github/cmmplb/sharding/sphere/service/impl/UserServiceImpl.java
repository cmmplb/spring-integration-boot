package io.github.cmmplb.sharding.sphere.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.cmmplb.core.beans.PageResult;
import io.github.cmmplb.core.beans.QueryPageBean;
import io.github.cmmplb.sharding.sphere.dao.UserMapper;
import io.github.cmmplb.sharding.sphere.entity.User;
import io.github.cmmplb.sharding.sphere.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author penglibo
 * @date 2021-04-02 00:02:06
 */

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public PageResult<User> getByPaged(QueryPageBean pageBean) {
        Page<User> page = baseMapper.selectByPaged(new Page<User>(pageBean.getCurrent(), pageBean.getSize()));
        return new PageResult<User>(page.getTotal(), page.getRecords());
    }
}
