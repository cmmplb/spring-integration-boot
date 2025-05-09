package io.github.cmmplb.cache.service.impl;

import io.github.cmmplb.cache.domain.entity.User;
import io.github.cmmplb.cache.mapper.UserMapper;
import io.github.cmmplb.cache.service.UserService;
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
    public boolean save(User user) {
        return userMapper.insert(user) > 0;
    }

    @Override
    @CacheEvict(key = "#p0", allEntries = true)
    public boolean removeById(Long id) {
        return userMapper.deleteById(id) > 0;
    }

    @Override
    @CachePut(keyGenerator = "customKeyGenerator")
    public boolean updateById(User user) {
        return userMapper.updateById(user) > 0;
    }

    @Override
    @Cacheable(keyGenerator = "customKeyGenerator")
    public List<User> list() {
        return userMapper.selectList();
    }

    @Override
    // cacheNames对应缓存管理器名称
    @Cacheable(cacheNames = "users", key = "'UserInfo'+#id", sync = true)
    public User getById(Long id) {
        return userMapper.selectById(id);
    }

}
