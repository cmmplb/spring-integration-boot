package io.github.cmmplb.mybatis.mapper.service.impl;

import io.github.cmmplb.core.beans.PageResult;
import io.github.cmmplb.core.beans.QueryPageBean;
import io.github.cmmplb.mybatis.mapper.dao.UserMapper;
import io.github.cmmplb.mybatis.mapper.entity.User;
import io.github.cmmplb.mybatis.mapper.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author penglibo
 * @date 2021-04-02 00:02:06
 */

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public boolean save(User user) {
        return userMapper.insert(user) > 0;
    }

    @Override
    public boolean removeById(Long id) {
        return userMapper.deleteById(id) > 0;
    }

    @Override
    public boolean updateById(User user) {
        return userMapper.updateById(user) > 0;
    }

    @Override
    public List<User> list() {
        return userMapper.selectList();
    }

    @Override
    public User getById(Long id) {
        return userMapper.selectById(id);
    }

    @Override
    public PageResult<User> getByPaged(QueryPageBean pageBean) {
        // 使用pageHelper实现分页
        // PageHelper.startPage(pageBean.getCurrent(), pageBean.getSize());
        // Page<User> pageList = userMapper.selectByPaged();
        // return new PageResult<>(pageList.getTotal(), pageList.getResult());

        // 手动分页
        PageResult<User> page = new PageResult<User>();
        page.setTotal(userMapper.selectByPagedTotal());
        page.setRows(userMapper.selectByPagedList(pageBean));
        return page;
    }

    @Override
    public List<User> testCurrentUserId() {
        return userMapper.testCurrentUserId();
    }
}
