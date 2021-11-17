package com.cmmplb.mybatis.plus;

import com.cmmplb.mybatis.plus.dao.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author penglibo
 * @date 2021-03-29 14:10:17
 */

@SpringBootTest
public class SpringBootMybatisPlusTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void contextLoads() {
        for (int i = 0; i < 3; i++) {
            userMapper.getByPagedTotal();
        }
        // Cache Hit Ratio [com.cmmplb.mybatisplus.plus.dao.UserMapper]: 0.75
        System.out.println(userMapper.getByPagedTotal());
    }
}
