package io.github.cmmplb.mybatis.service.impl;

import io.github.cmmplb.mybatis.dao.UserTagMapper;
import io.github.cmmplb.mybatis.entity.UserTag;
import io.github.cmmplb.mybatis.service.UserTagService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author penglibo
 * @date 2021-08-22 09:29:20
 * @since jdk 1.8
 */

@Service
public class UserTagServiceImpl implements UserTagService {

    @Resource
    private UserTagMapper userTagMapper;

    @Override
    public Boolean removeById(Long id) {
        return userTagMapper.deleteById(id) > 0;
    }

    @Override
    public Boolean insert(UserTag userTag) {
        return userTagMapper.insert(userTag) > 0;
    }

    @Override
    public List<UserTag> getByUserId(Long userId) {
        return userTagMapper.selectByUserId(userId);
    }
}
