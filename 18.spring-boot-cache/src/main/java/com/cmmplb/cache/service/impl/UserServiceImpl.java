package com.cmmplb.cache.service.impl;

import com.cmmplb.cache.dao.UserMapper;
import com.cmmplb.cache.entity.User;
import com.cmmplb.cache.service.UserService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author penglibo
 * @date 2021-04-02 00:02:06
 */

@Service
@CacheConfig(cacheNames = "user")
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    @CachePut(key = "#p0.id")
    public User save(User user) {
        userMapper.insert(user);
        return user;
    }

    @Override
    @CacheEvict(key = "#p0", allEntries = true)
    public boolean removeById(Long id) {
        return userMapper.deleteById(id) > 0;
    }

    @Override
    @CachePut(key = "#p0.id")
    public User updateById(User user) {
        userMapper.updateById(user);
        return user;
    }

    @Override
    public List<User> list() {
        return userMapper.selectList();
    }

    @Override
    @Cacheable(key = "#p0")
    public User getById(Long id) {
        return userMapper.selectById(id);
    }

}
