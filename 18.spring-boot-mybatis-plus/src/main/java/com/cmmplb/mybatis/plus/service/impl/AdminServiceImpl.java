package com.cmmplb.mybatis.plus.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.cmmplb.core.beans.PageResult;
import io.github.cmmplb.core.beans.QueryPageBean;
import com.cmmplb.mybatis.plus.dao.AdminMapper;
import com.cmmplb.mybatis.plus.dao.UserMapper;
import com.cmmplb.mybatis.plus.entity.Admin;
import com.cmmplb.mybatis.plus.entity.User;
import com.cmmplb.mybatis.plus.service.AdminService;
import com.cmmplb.mybatis.plus.service.UserService;
import com.cmmplb.mybatis.plus.vo.UserInfoVO;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author penglibo
 * @date 2021-04-02 00:02:06
 */

@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

}
