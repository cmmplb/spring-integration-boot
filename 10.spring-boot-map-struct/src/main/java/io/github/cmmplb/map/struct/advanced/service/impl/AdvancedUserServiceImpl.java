package io.github.cmmplb.map.struct.advanced.service.impl;

import io.github.cmmplb.map.struct.advanced.convert.UserConvert;
import io.github.cmmplb.map.struct.advanced.core.utils.ConverterUtil;
import io.github.cmmplb.map.struct.advanced.dao.AdvancedUserDao;
import io.github.cmmplb.map.struct.advanced.entity.User;
import io.github.cmmplb.map.struct.advanced.service.AdvancedUserService;
import io.github.cmmplb.map.struct.advanced.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author penglibo
 * @date 2022-08-04 09:28:25
 * @since jdk 1.8
 */

@Service
public class AdvancedUserServiceImpl implements AdvancedUserService {

    @Autowired
    private AdvancedUserDao userDao;


    @Override
    public UserVO getOne() {
        User user = userDao.selectOne();
        return ConverterUtil.convert(UserConvert.class, user);
    }

    @Override
    public List<UserVO> getList() {
        List<User> users = userDao.selectList();
        return ConverterUtil.convert(UserConvert.class, users);
    }
}
