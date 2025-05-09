package io.github.cmmplb.map.struct;

import io.github.cmmplb.map.struct.advanced.service.AdvancedUserService;
import io.github.cmmplb.map.struct.basic.convert.UserConvert;
import io.github.cmmplb.map.struct.basic.dao.UserDao;
import io.github.cmmplb.map.struct.basic.dto.UserDTO;
import io.github.cmmplb.map.struct.basic.entity.User;
import io.github.cmmplb.map.struct.basic.vo.UserVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author penglibo
 * @date 2021-08-27 17:44:49
 * @since jdk 1.8
 */

@SpringBootTest
public class MapStructTests {

    @Autowired
    private UserDao userDao;

    @Autowired
    private AdvancedUserService userService;

    /**
     * 基础使用
     */
    @Test
    public void basicUse() {
        User user = userDao.selectOne();
        System.out.println("UserConvert-user:");
        UserVO userVO = UserConvert.INSTANCE.convert(user);
        System.out.println(userVO);

        System.out.println("UserConvert-userList:");
        List<User> users = userDao.selectList();
        List<UserVO> list = UserConvert.INSTANCE.convertList(users);
        System.out.println(list);
    }

    /**
     * 映射关系使用
     */
    @Test
    public void mappingUse() {
        UserDTO dto = userDao.selectDTO();
        System.out.println("属性不完全映射:");
        UserVO userVO = UserConvert.INSTANCE.convert(dto);
        System.out.println(userVO);

        System.out.println("多属性映射转换user:");
        User user = userDao.selectOne();
        UserVO vo = UserConvert.INSTANCE.convert(dto, user);
        System.out.println(vo);
    }

    /**
     * 封装工具类使用
     */
    @Test
    public void advancedUse() {
        System.out.println("UserConvert-user:");
        System.out.println(userService.getOne());

        System.out.println("UserConvert-userList:");
        System.out.println(userService.getList());
    }

}
