package io.github.cmmplb.mybatis.plus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.cmmplb.mybatis.plus.dao.AdminMapper;
import io.github.cmmplb.mybatis.plus.entity.Admin;
import io.github.cmmplb.mybatis.plus.service.AdminService;
import org.springframework.stereotype.Service;

/**
 * @author penglibo
 * @date 2021-04-02 00:02:06
 */

@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

}
