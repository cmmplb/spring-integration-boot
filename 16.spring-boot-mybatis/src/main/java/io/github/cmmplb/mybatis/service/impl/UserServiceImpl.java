package io.github.cmmplb.mybatis.service.impl;

import io.github.cmmplb.core.beans.PageResult;
import io.github.cmmplb.core.beans.QueryPageBean;
import io.github.cmmplb.mybatis.dao.UserMapper;
import io.github.cmmplb.mybatis.entity.User;
import io.github.cmmplb.mybatis.service.UserService;
import io.github.cmmplb.mybatis.vo.UserInfoVO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author penglibo
 * @date 2021-04-02 00:02:06
 */

@Slf4j
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
        boolean b = userMapper.updateById(user) > 0;
        log.info("修改用户信息:{}", b);
        System.out.println(1 / 0);
        return b;
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

    @Override
    public UserInfoVO getUserInfoById(Long id) {
        return userMapper.selectUserInfoById(id);
    }

    @Override
    public UserInfoVO getTestOneMany2SubQuery(Long id) {
        return userMapper.selectTestOneMany2SubQuery(id);
    }

    @Override
    public UserInfoVO getTestOneMany2FieldMapping(Long id) {
        return userMapper.selectTestOneMany2FieldMapping(id);
    }
}
