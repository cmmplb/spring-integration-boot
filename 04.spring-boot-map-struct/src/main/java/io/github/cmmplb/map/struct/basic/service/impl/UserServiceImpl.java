package io.github.cmmplb.map.struct.basic.service.impl;

import io.github.cmmplb.map.struct.basic.convert.UserConvert;
import io.github.cmmplb.map.struct.basic.dao.UserDao;
import io.github.cmmplb.map.struct.basic.dto.UserDTO;
import io.github.cmmplb.map.struct.basic.entity.User;
import io.github.cmmplb.map.struct.basic.service.UserService;
import io.github.cmmplb.map.struct.basic.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author penglibo
 * @date 2022-08-04 09:28:25
 * @since jdk 1.8
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;


    @Override
    public UserVO getOne() {
        User user = userDao.selectOne();
        return UserConvert.INSTANCE.convert(user);
    }

    @Override
    public List<UserVO> getList() {
        List<User> users = userDao.selectList();
        return UserConvert.INSTANCE.convertList(users);
    }

    @Override
    public UserVO getOneMapping() {
        UserDTO dto = userDao.selectDTO();

        return UserConvert.INSTANCE.convert(dto);
    }

    @Override
    public UserVO getManyMapping() {
        UserDTO dto = userDao.selectDTO();
        User user = userDao.selectOne();
        return UserConvert.INSTANCE.convert(dto, user);
    }
}