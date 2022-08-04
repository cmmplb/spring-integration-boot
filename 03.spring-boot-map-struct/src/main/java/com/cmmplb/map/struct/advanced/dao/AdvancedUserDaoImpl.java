package com.cmmplb.map.struct.advanced.dao;

import com.cmmplb.map.struct.advanced.dto.UserDTO;
import com.cmmplb.map.struct.advanced.entity.User;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @author penglibo
 * @date 2022-08-04 09:31:05
 * @since jdk 1.8
 */

@Component
public class AdvancedUserDaoImpl implements AdvancedUserDao {

    @Override
    public User selectOne() {
        return new User(1, "张三", "123456");
    }

    @Override
    public UserDTO selectDTO() {
        return new UserDTO(2, "伞兵一号", "654321");
    }

    @Override
    public List<User> selectList() {
        return Arrays.asList(
                new User(1, "张三", "123456"),
                new User(2, "李四", "123456"),
                new User(3, "王二", "123456"),
                new User(4, "麻子", "123456")
        );
    }
}
