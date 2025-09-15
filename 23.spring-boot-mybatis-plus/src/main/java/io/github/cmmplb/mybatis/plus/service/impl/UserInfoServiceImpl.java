package io.github.cmmplb.mybatis.plus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.cmmplb.mybatis.plus.dao.UserInfoMapper;
import io.github.cmmplb.mybatis.plus.entity.UserInfo;
import io.github.cmmplb.mybatis.plus.service.UserInfoService;
import org.springframework.stereotype.Service;

/**
* @author penglibo
* @date 2021-08-22 11:47:03
* @since jdk 1.8
*/
    
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

}
