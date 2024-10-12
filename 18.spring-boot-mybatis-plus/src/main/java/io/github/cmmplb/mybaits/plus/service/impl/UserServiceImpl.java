package com.cmmplb.mybatis.plus.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.cmmplb.core.beans.PageResult;
import io.github.cmmplb.core.beans.QueryPageBean;
import com.cmmplb.mybatis.plus.dao.UserMapper;
import com.cmmplb.mybatis.plus.entity.User;
import com.cmmplb.mybatis.plus.service.UserService;
import com.cmmplb.mybatis.plus.vo.UserInfoVO;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author penglibo
 * @date 2021-04-02 00:02:06
 */

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public PageResult<User> getByPaged(QueryPageBean pageBean) {
        // 使用pageHelper实现分页
        // PageHelper.startPage(pageBean.getCurrent(), pageBean.getSize());
        // Page<User> page = baseMapper.selectByPaged();
        // return new PageResult<User>(page.getTotal(), page.getRecords());

        // 手动分页
        // PageResult<User> page = new PageResult<User>();
        // page.setTotal(baseMapper.getByPagedTotal());
        // page.setRows(baseMapper.getByList(pageBean));
        // return page;

        // mybatis-plus自带的分页
        Page<User> page = baseMapper.selectByPaged(new Page<User>(pageBean.getCurrent(), pageBean.getSize()));
        return new PageResult<User>(page.getTotal(), page.getRecords());
    }

    /**
     * @value: 在redis中 保存缓存在以user命名的集合中
     * @key :   user集合中的关键字, 注意字符串要以单引号括住  '',变量前缀加#号, 如#userId
     */
    @Override
    @Cacheable(value = "user", key = "'getTestOneMany2SubQuery'")
    public UserInfoVO getTestOneMany2SubQuery(Long id) {
        return baseMapper.selectTestOneMany2SubQuery(id);
    }

    @Override
    public UserInfoVO getTestOneMany2FieldMapping(Long id) {
        return baseMapper.selectTestOneMany2FieldMapping(id);
    }
}
