package com.cmmplb.mybatis.plus;

import com.cmmplb.mybatis.plus.dao.UserMapper;
import com.cmmplb.redis.service.RedisService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author penglibo
 * @date 2021-03-29 14:10:17
 */

@SpringBootTest
public class MybatisPlusTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisService redisService;

    @Test
    public void contextLoads() {
        for (int i = 0; i < 3; i++) {
            userMapper.getByPagedTotal();
        }
        // Cache Hit Ratio [com.cmmplb.mybatisplus.plus.dao.UserMapper]: 0.75
        System.out.println(userMapper.getByPagedTotal());
    }

    @Test
    public void test() {
        redisService.set("name", "mybatis-plus");
        Object name = redisService.get("name");
        System.out.println(name);
    }
}
