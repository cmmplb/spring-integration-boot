package com.cmmplb.mybatis.service.impl;

import com.cmmplb.core.beans.PageResult;
import com.cmmplb.core.beans.QueryPageBean;
import com.cmmplb.mybatis.dao.UserInfoMapper;
import com.cmmplb.mybatis.entity.UserInfo;
import com.cmmplb.mybatis.service.UserInfoService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author penglibo
 * @date 2021-04-13 16:00:50
 */

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Resource
    private UserInfoMapper userInfoMapper;

    @Override
    public boolean save(UserInfo userInfo) {
        return userInfoMapper.insert(userInfo) > 0;
    }

    @Override
    public boolean removeById(Long id) {
        return userInfoMapper.deleteById(id) > 0;
    }

    @Override
    public boolean updateById(UserInfo userInfo) {
        return userInfoMapper.updateById(userInfo) > 0;
    }

    @Override
    public List<UserInfo> list() {
        return userInfoMapper.selectList();
    }

    @Override
    public UserInfo getById(Long id) {
        return userInfoMapper.selectById(id);
    }

    @Override
    public PageResult<UserInfo> getByPaged(QueryPageBean pageBean) {
        // 使用pageHelper实现分页
         PageHelper.startPage(pageBean.getCurrent(), pageBean.getSize());
         Page<UserInfo> pageList = userInfoMapper.selectByPaged();
         return new PageResult<>(pageList.getTotal(), pageList.getResult());
    }
}
