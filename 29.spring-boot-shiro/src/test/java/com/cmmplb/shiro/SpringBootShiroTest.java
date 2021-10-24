package com.cmmplb.shiro;

import com.cmmplb.shiro.config.properties.ShiroProperties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author penglibo
 * @date 2021-03-29 14:10:17
 */

@SpringBootTest
public class SpringBootShiroTest {

    @Autowired
    private ShiroProperties shiroProperties;

    @Test
    public void contextLoads() {
        System.out.println(shiroProperties);

    }
}
