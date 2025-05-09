package io.github.cmmplb.activiti.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.cmmplb.activiti.convert.UserConvert;
import io.github.cmmplb.activiti.dao.UserMapper;
import io.github.cmmplb.activiti.entity.User;
import io.github.cmmplb.activiti.service.UserService;
import io.github.cmmplb.activiti.vo.UserInfoVO;
import io.github.cmmplb.core.utils.ConverterUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * @author penglibo
 * @date 2023-11-17 09:44:28
 * @since jdk 1.8
 */

@Slf4j
@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public List<User> getListByIds(Set<String> userIds) {
        return baseMapper.selectList(new LambdaQueryWrapper<User>().in(User::getId, userIds));
    }

    @Override
    public List<UserInfoVO> getList() {
        List<User> userList = baseMapper.selectList(new LambdaQueryWrapper<User>());
        return ConverterUtil.convert(UserConvert.class, userList);
    }
}
